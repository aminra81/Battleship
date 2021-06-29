package ir.sharif.aminra.request.liveGamesRequests;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class GetLiveGamesRequest extends Request {
    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.getLiveGames();
    }
}
