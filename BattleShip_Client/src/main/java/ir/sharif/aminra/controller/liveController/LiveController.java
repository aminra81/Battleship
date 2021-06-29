package ir.sharif.aminra.controller.liveController;

import ir.sharif.aminra.gameModels.LiveState;
import ir.sharif.aminra.view.gamePage.LiveFXController;
import javafx.application.Platform;

public class LiveController {
    private final LiveFXController liveFXController;
    private final LiveState liveState;

    public LiveController(LiveFXController liveFXController, LiveState liveState) {
        this.liveFXController = liveFXController;
        this.liveState = liveState;
    }

    public void applyOnPanel() {
        if(liveState == null)
            return;
        Platform.runLater(() -> {
            liveFXController.setTurnLabel(liveState.getTurn());
            liveFXController.setFirstPlayerNameLabel(liveState.getPlayers()[0]);
            liveFXController.setSecondPlayerNameLabel(liveState.getPlayers()[1]);
            liveFXController.applyBoards(liveState.getBoards());
        });
    }

}
