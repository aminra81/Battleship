package ir.sharif.aminra.response.liveGamesResponses;

import ir.sharif.aminra.gameModels.LiveState;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class GetLiveStateResponse extends Response {
    LiveState liveState;

    public GetLiveStateResponse(LiveState liveState) {
        this.liveState = liveState;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyLiveState(liveState);
    }
}
