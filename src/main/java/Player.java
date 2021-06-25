import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    public static final int PLAYER_INDEX = 51;
    public static final double PLAYER_SPEED = 3.0;
    public static final float PLAYER_WIDTH = 40;
    public static final float SCREEN_WIDTH = 800;
    public static final float SCREEN_HEIGHT = 600;
    public static final int NUM_COL = 10;
    static boolean moveLeft = false;
    static boolean moveRight = false;
    public static int score = 0;
    public static int lives = 3;

    public static void handlePlayerMoveLeft() {
        moveLeft = true;
        moveRight = false;
    }

    public static void handlePlayerMoveRight() {
        moveRight = true;
        moveLeft = false;
    }

    public static void handleStopMoving() {
        moveLeft = false;
        moveRight = false;
    }

    public static void handlePlayerAnimation(Group root) {
        Node player = root.getChildren().get(PLAYER_INDEX);
        if (moveLeft) {
            player.setLayoutX(player.getLayoutX() - PLAYER_SPEED);
            if (player.getLayoutX() < 0) {
                player.setLayoutX(0);
            }
        }
        else if (moveRight) {
            player.setLayoutX(player.getLayoutX() + PLAYER_SPEED);
            if (player.getLayoutX() + PLAYER_WIDTH > SCREEN_WIDTH) {
                player.setLayoutX(SCREEN_WIDTH - PLAYER_WIDTH);
            }
        }
    }

    public static void playerDestroyed(Group root) {
        Node player = root.getChildren().get(PLAYER_INDEX);
        ImageView playerImageView = (ImageView) player;
        playerImageView.setImage(null);
        //create a new player, and put into group to replace the previous one
        Image newPlayerImage = new Image("player.png", 40, 25, true, true);
        ImageView newPlayerImageView = new ImageView(newPlayerImage);
        newPlayerImageView.setFitWidth(40);
        newPlayerImageView.setFitHeight(25);
        newPlayerImageView.setLayoutX(SCREEN_WIDTH / 2);
        newPlayerImageView.setLayoutY(SCREEN_HEIGHT - 50);
        root.getChildren().set(PLAYER_INDEX, newPlayerImageView);
    }

    public static void updateScore(int enemyIndex) {
        int row = enemyIndex / NUM_COL;
        if (row == 0) {
            score += 30;
        } else if (row == 1 || row == 2) {
            score += 20;
        } else {
            score += 10;
        }
    }

    public static int getScore() {
        return score;
    }

    public static void updateLives() {
        lives--;
        if (lives == 0) {
            GameScene.stopGame(false);
        }
    }

    public static int getLives() {
        return lives;
    }
}
