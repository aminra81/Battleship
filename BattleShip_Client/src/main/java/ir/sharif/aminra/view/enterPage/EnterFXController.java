package ir.sharif.aminra.view.enterPage;

import ir.sharif.aminra.request.enterRequests.EnterRequest;
import ir.sharif.aminra.request.enterRequests.EnterRequestType;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EnterFXController extends FXController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void signIn() {
        requestListener.listen(new EnterRequest(usernameField.getText(), passwordField.getText(), EnterRequestType.SIGN_IN));
    }

    @FXML
    public void signUp() {
        requestListener.listen(new EnterRequest(usernameField.getText(), passwordField.getText(), EnterRequestType.SIGN_UP));
    }

    public void setErrorLabel(String error) {
        errorLabel.setText(error);
    }
}
