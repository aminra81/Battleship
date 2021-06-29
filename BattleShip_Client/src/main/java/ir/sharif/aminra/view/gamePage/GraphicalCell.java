package ir.sharif.aminra.view.gamePage;

import ir.sharif.aminra.util.Config;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GraphicalCell extends Rectangle {

    private final int x, y;
    private final boolean mine;
    private Circle damagedCircle;

    public GraphicalCell(int x, int y, boolean mine) {
        Config gameConfig = Config.getConfig("gamePage");
        if(mine)
            this.setX(gameConfig.getProperty(Double.class, "myBoardX") +
                    y * gameConfig.getProperty(Double.class, "sizeOfCell"));
        else
            this.setX(gameConfig.getProperty(Double.class, "opponentBoardX") +
                    y * gameConfig.getProperty(Double.class, "sizeOfCell"));
        this.setY(gameConfig.getProperty(Double.class, "boardsY") +
                x * gameConfig.getProperty(Double.class, "sizeOfCell"));

        this.setHeight(gameConfig.getProperty(Double.class, "sizeOfCell"));
        this.setWidth(gameConfig.getProperty(Double.class, "sizeOfCell"));

        this.x = x;
        this.y = y;
        this.mine = mine;
        this.setFill(Color.web(gameConfig.getProperty("defaultColor")));
        this.setStroke(Color.web(gameConfig.getProperty("defaultStroke")));
    }

    public void setDamagedCircle(Circle circle) {
        this.damagedCircle = circle;
    }

    public void getDamaged() {
        this.damagedCircle.setVisible(true);
    }

    public boolean isMine() { return mine; }
    public int getCellX() { return x; }
    public int getCellY() { return y; }

    @Override
    public String toString() {
        return "GraphicalCell{" +
                "x=" + x +
                ", y=" + y +
                ", mine=" + mine +
                '}';
    }
}
