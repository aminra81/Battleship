package ir.sharif.aminra.request.scoreboardRequests;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;

public class GetScoreboardRequest extends Request {
    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.getScoreboard();
    }
}
