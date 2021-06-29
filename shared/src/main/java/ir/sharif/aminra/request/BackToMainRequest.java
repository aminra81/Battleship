package ir.sharif.aminra.request;

import ir.sharif.aminra.response.Response;

public class BackToMainRequest extends Request{
    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.backToMain();
    }
}
