package ir.sharif.aminra.response.scoreboardResponses;

import ir.sharif.aminra.gameModels.PlayerState;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateScoreboardResponse extends Response {

    List<PlayerState> playerStateList;

    public UpdateScoreboardResponse(List<PlayerState> playerStateList) {
        this.playerStateList = playerStateList;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyScoreboard(playerStateList);
    }
}
