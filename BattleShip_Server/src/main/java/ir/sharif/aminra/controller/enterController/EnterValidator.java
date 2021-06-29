package ir.sharif.aminra.controller.enterController;

import ir.sharif.aminra.db.Context;
import ir.sharif.aminra.request.enterRequests.EnterRequestType;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.models.Player;

public class EnterValidator {
    public String getError(String username, String password, EnterRequestType enterRequestType) {
        Config errorsConfig = Config.getConfig("enterPage");
        switch (enterRequestType) {
            case SIGN_IN:
                Player player = Context.getInstance().getPlayerDB().getPlayer(username);
                if(player == null)
                    return errorsConfig.getProperty("noUserError");
                if(!player.getPassword().equals(password))
                    return errorsConfig.getProperty("passNotMatch");
                return "";
            case SIGN_UP:
                if(username.equals(""))
                    return errorsConfig.getProperty("emptyUsernameError");
                if(Context.getInstance().getPlayerDB().getPlayer(username) != null)
                    return errorsConfig.getProperty("duplicateUsernameError");
                if(password.equals(""))
                    return errorsConfig.getProperty("emptyPasswordError");
                return "";
        }
        return "";
    }
}
