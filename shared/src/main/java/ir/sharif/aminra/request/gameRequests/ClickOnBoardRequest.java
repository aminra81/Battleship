package ir.sharif.aminra.request.gameRequests;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class ClickOnBoardRequest extends Request {
    private final int x, y;

    public ClickOnBoardRequest(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.clickOnBoard(x, y);
    }
}
