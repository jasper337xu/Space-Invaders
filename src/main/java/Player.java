import javafx.scene.Group;
import javafx.scene.Node;

public class Player {
    public static final int PLAYER_INDEX = 51;
    public static final double PLAYER_SPEED = 3.0;
    public static final float PLAYER_WIDTH = 40;
    public static final float SCREEN_WIDTH = 800;
    static boolean moveLeft = false;
    static boolean moveRight = false;

    public static void handlePlayerMoveLeft() {
        moveLeft = true;
        moveRight = false;
    }

    public static void handlePlayerMoveRight() {
        moveRight = true;
        moveLeft = false;
    }

    public static void handlePlayerFire() {

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
}
