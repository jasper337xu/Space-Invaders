public class GameSettings {
    static int gameLevel = 1;
    static boolean isNextLevel = false;

    public static void setGameLevel(int level) {
        Player.startNewGame(level, isNextLevel);
        Enemy.startNewGame(level);
        gameLevel = level;
        isNextLevel = false;
        GameScene.updateLevelLabel();
    }

    public static void launchNextLevel() {
        gameLevel += 1;
        isNextLevel = true;
        SpaceInvaders.launchNextLevel = true;
    }

    public static int getGameLevel() {
        return gameLevel;
    }
}
