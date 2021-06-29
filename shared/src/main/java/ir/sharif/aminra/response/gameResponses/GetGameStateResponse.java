package ir.sharif.aminra.response.gameResponses;

import ir.sharif.aminra.gameModels.GameState;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class GetGameStateResponse extends Response {
    GameState gameState;
    public GetGameStateResponse(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyGameState(gameState);
    }
}
