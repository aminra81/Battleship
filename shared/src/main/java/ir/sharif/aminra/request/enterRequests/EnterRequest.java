package ir.sharif.aminra.request.enterRequests;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class EnterRequest extends Request {
    private final String username;
    private final String password;
    EnterRequestType enterRequestType;

    public EnterRequest(String username, String password, EnterRequestType enterRequestType) {
        this.username = username;
        this.password = password;
        this.enterRequestType = enterRequestType;
    }
    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.enter(username, password, enterRequestType);
    }
}
