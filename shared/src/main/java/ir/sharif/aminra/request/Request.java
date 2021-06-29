package ir.sharif.aminra.request;

import ir.sharif.aminra.response.Response;

public abstract class Request {
    public abstract Response visit(RequestVisitor requestVisitor);
}
