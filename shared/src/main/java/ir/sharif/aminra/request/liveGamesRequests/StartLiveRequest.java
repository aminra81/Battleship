package ir.sharif.aminra.request.liveGamesRequests;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class StartLiveRequest extends Request {
    String gameName;

    public StartLiveRequest(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.startLive(gameName);
    }
}
