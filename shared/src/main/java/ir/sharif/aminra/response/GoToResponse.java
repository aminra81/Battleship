package ir.sharif.aminra.response;

public class GoToResponse extends Response {
    String pageName;
    String message;
    public GoToResponse(String pageName, String message) {
        this.pageName = pageName;
        this.message = message;
    }
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.goTo(pageName, message);
    }
}
