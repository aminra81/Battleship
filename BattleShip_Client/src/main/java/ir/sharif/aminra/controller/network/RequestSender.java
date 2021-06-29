package ir.sharif.aminra.controller.network;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.response.Response;

public interface RequestSender {
    Response sendRequest(Request request);
    void close();
}
