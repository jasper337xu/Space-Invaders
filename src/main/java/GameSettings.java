public class GameSettings {
    static int gameLevel = 1;

    public static void setGameLevel(int level) {
        Player.startNewGame(level);
        Enemy.startNewGame(level);
        gameLevel = level;
        GameScene.updateLevelLabel();
    }

    public static int getGameLevel() {
        return gameLevel;
    }
}
