package ir.sharif.aminra.controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.aminra.gson.Deserializer;
import ir.sharif.aminra.gson.Serializer;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.response.enterResponses.EnterResponse;
import ir.sharif.aminra.response.LogoutResponse;
import ir.sharif.aminra.response.Response;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketResponseSender implements ResponseSender {
    private final Serializer serializer;
    private final Deserializer deserializer;
    private final Socket socket;
    private final PrintStream printStream;
    private final Scanner scanner;
    private final Gson gson;
    private String token;

    public SocketResponseSender(Socket socket) throws IOException {
        this.serializer = new Serializer<>();
        this.deserializer = new Deserializer<>();
        this.socket = socket;
        this.scanner = new Scanner(socket.getInputStream());
        this.printStream = new PrintStream(socket.getOutputStream());
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Request.class, deserializer)
                .registerTypeAdapter(Response.class, serializer)
                .create();
    }

    @Override
    public Request getRequest() {
        String requestString = scanner.nextLine();
        Request request = gson.fromJson(requestString, Request.class);
        String curToken = deserializer.getToken();
        if(token != null && !token.equals(curToken))
            try {
                throw new Exception("wrong token");
            } catch (Exception e) {
                e.printStackTrace();
            }
        return request;
    }

    @Override
    public void sendResponse(Response response) {
        if(response instanceof EnterResponse && ((EnterResponse)response).getSuccess()) {
            token = TokenGenerator.getInstance().generateNewToken();
            serializer.setToken(token);
        }
        if(response instanceof LogoutResponse) {
            token = null;
            serializer.setToken(null);
        }
        printStream.println(gson.toJson(response, Response.class));
    }

    @Override
    public void close() {
        try {
            printStream.close();
            scanner.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
