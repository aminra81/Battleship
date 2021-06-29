package ir.sharif.aminra.request.gameRequests;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class GetGameStateRequest extends Request {
    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.getGameState();
    }
}
