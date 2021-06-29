package ir.sharif.aminra.controller.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.aminra.request.LogoutRequest;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.response.enterResponses.EnterResponse;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.gson.Deserializer;
import ir.sharif.aminra.gson.Serializer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketRequestSender implements RequestSender{
    private final Serializer serializer;
    private final Deserializer deserializer;
    String token;
    private final Socket socket;
    private final PrintStream printStream;
    private final Scanner scanner;
    private final Gson gson;

    public SocketRequestSender(Socket socket) throws IOException {
        serializer = new Serializer<>();
        deserializer = new Deserializer<>();
        this.socket = socket;
        this.scanner = new Scanner(socket.getInputStream());
        this.printStream = new PrintStream(socket.getOutputStream());
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Response.class, deserializer)
                .registerTypeAdapter(Request.class, serializer)
                .create();
    }

    @Override
    public Response sendRequest(Request request) {
        String requestString = gson.toJson(request, Request.class);
        printStream.println(requestString);
        String responseString = scanner.nextLine();
        Response response = gson.fromJson(responseString, Response.class);
        if (response instanceof EnterResponse && ((EnterResponse)response).getSuccess()) {
            token = deserializer.getToken();
            serializer.setToken(token);
        }
        if (request instanceof LogoutRequest) {
            token = null;
            serializer.setToken(null);
        }
        return response;
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
