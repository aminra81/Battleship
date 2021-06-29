package ir.sharif.aminra.response;

public abstract class Response {
    public abstract void visit(ResponseVisitor responseVisitor);
}
