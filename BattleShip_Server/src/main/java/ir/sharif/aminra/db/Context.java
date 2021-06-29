package ir.sharif.aminra.db;

public class Context {
    static private Context context;

    PlayerDB playerDB;

    public static Context getInstance() {
        if(context == null)
            context = new Context();
        return context;
    }

    private Context() {
        playerDB = new PlayerDB();
    }

    public PlayerDB getPlayerDB() {
        return playerDB;
    }
}
