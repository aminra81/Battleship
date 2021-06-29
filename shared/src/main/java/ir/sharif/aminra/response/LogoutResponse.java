package ir.sharif.aminra.response;

public class LogoutResponse extends Response{
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.logout();
    }
}
