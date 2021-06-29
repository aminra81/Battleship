package ir.sharif.aminra.view.mainPage;

import ir.sharif.aminra.request.infoRequests.GetInfoRequest;
import ir.sharif.aminra.request.LogoutRequest;
import ir.sharif.aminra.request.gameRequests.StartGameRequest;
import ir.sharif.aminra.request.liveGamesRequests.GetLiveGamesRequest;
import ir.sharif.aminra.request.scoreboardRequests.GetScoreboardRequest;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;

public class MainFXController extends FXController {
    @FXML
    public void startGame() {
        requestListener.listen(new StartGameRequest());
    }

    @FXML
    public void getInfo() { requestListener.listen(new GetInfoRequest()); }

    @FXML
    public void logout() { requestListener.listen(new LogoutRequest()); }

    @FXML
    public void getScoreboard() { requestListener.listen(new GetScoreboardRequest()); }

    @FXML
    public void getLiveGames() { requestListener.listen(new GetLiveGamesRequest()); }
}
