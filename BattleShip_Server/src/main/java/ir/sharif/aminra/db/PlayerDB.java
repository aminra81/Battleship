package ir.sharif.aminra.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.aminra.gameModels.PlayerState;
import ir.sharif.aminra.models.PlayerStatus;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.models.ID;
import ir.sharif.aminra.models.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerDB implements DBSet<Player> {
    private final File dbDirectory = Config.getConfig("db").getProperty(File.class, "playersDB");

    @Override
    public Player getByID(ID id) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File Data = new File(dbDirectory, id + ".json");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Data));
            Player player = gson.fromJson(bufferedReader, Player.class);
            bufferedReader.close();
            return player;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveIntoDB(Player player) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File Data = new File(dbDirectory, player.getID() + ".json");
            if (!Data.exists())
                Data.createNewFile();
            FileWriter writer = new FileWriter(Data);
            writer.write(gson.toJson(player));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer(String username) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (File userFile : Objects.requireNonNull(dbDirectory.listFiles())) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(userFile));
                Player currentPlayer = gson.fromJson(bufferedReader, Player.class);
                bufferedReader.close();
                if (currentPlayer.getUsername().equals(username))
                    return currentPlayer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PlayerState> getPlayerStates() {
        List<PlayerState> playerStateList = new ArrayList<>();
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (File userFile : Objects.requireNonNull(dbDirectory.listFiles())) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(userFile));
                Player currentPlayer = gson.fromJson(bufferedReader, Player.class);
                bufferedReader.close();
                if(currentPlayer.getPlayerStatus().equals(PlayerStatus.ONLINE))
                    playerStateList.add(new PlayerState(currentPlayer.getUsername(), currentPlayer.getScore(),
                            "Online"));
                else
                    playerStateList.add(new PlayerState(currentPlayer.getUsername(), currentPlayer.getScore(),
                            "Offline"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerStateList;
    }
}
