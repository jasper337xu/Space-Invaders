import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public class Enemy {
    public static double enemySpeed = 0.5;
    public static final double ENEMY_VERTICAL_SPEED = 10.0;
    public static final float ENEMY_WIDTH = 40;
    public static final float SCREEN_WIDTH = 800;
    public static final float SCREEN_HEIGHT = 600;
    public static final int INIT_NUM_ENEMIES = 50;
    public static Set<Integer> remainingEnemies = new HashSet<>();

    enum ENEMY_TYPE {ENEMY1, ENEMY2, ENEMY3};

    public static void handleEnemyAnimation(Group root) {
        boolean changeDirection = false;
        boolean stopGame = false;
        for (int i = 0; i < INIT_NUM_ENEMIES; i++) {
            Node n = root.getChildren().get(i);
            if (remainingEnemies.contains(i)) {
                n.setLayoutX(n.getLayoutX() + enemySpeed);
                if (n.getLayoutX() < 0 || n.getLayoutX() > SCREEN_WIDTH - ENEMY_WIDTH) {
                    changeDirection = true;
                }
            }
        }

        if (changeDirection) {
            // descend one row and reverse direction
            for (int i = 0; i < INIT_NUM_ENEMIES; i++) {
                Node n = root.getChildren().get(i);
                if (remainingEnemies.contains(i)) {
                    n.setLayoutY(n.getLayoutY() + ENEMY_VERTICAL_SPEED);
                    if (n.getLayoutY() > SCREEN_HEIGHT - 39) {
                        //alien contacts the player
                        stopGame = true;
                    }
                }
            }
            if (stopGame) {
                GameScene.stopGame(false);
            }
            enemySpeed *= -1.0f;
        }
    }

    public static Set<Integer> getRemainingEnemies() {
        return remainingEnemies;
    }

    public static void enemyDestroyed(Node enemy, int enemyIndex) {
        ImageView enemyImageView = (ImageView) enemy;
        enemyImageView.setImage(null);
        remainingEnemies.remove(enemyIndex);
        if (remainingEnemies.size() == 0) {
            // launch next level or game success
            int currentLevel = GameSettings.getGameLevel();
            if (currentLevel <= 2) {
                GameSettings.launchNextLevel();
            }
            else {
                GameScene.stopGame(true);
            }
            return;
        }
        enemiesSpeedUp();
    }

    public static void enemiesSpeedUp() {
        if (enemySpeed > 0) {
            enemySpeed += 0.1;
        }
        else {
            enemySpeed -= 0.1;
        }
    }

    public static void startNewGame(int level) {
        if (level == 1) {
            enemySpeed = 0.5;
        } else if (level == 2) {
            enemySpeed = 1.0;
        } else {
            enemySpeed = 1.5;
        }
        remainingEnemies = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            remainingEnemies.add(i);
        }
    }
}
