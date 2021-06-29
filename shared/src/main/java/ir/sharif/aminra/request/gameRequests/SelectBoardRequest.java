package ir.sharif.aminra.request.gameRequests;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class SelectBoardRequest extends Request {
    boolean isSelected;
    public SelectBoardRequest(boolean isSelected) {
        this.isSelected = isSelected;
    }
    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.selectBoard(isSelected);
    }
}
