package ir.sharif.aminra.response.liveGamesResponses;

import ir.sharif.aminra.gameModels.LiveGameState;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateLiveGamesResponse extends Response {
    List<LiveGameState> liveGameStateList;

    public UpdateLiveGamesResponse(List<LiveGameState> liveGameStateList) {
        this.liveGameStateList = liveGameStateList;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyLiveGames(liveGameStateList);
    }
}
