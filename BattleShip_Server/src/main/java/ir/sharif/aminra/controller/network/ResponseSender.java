package ir.sharif.aminra.controller.network;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.response.Response;

public interface ResponseSender {
    Request getRequest();

    void sendResponse(Response response);

    void close();
}
