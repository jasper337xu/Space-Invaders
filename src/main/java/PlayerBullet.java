import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Set;

public class PlayerBullet {
    public static final int PBGROUP_INDEX = 52;
    public static final double PLAYER_BULLET_SPEED = 6.0;
    public static final float ENEMY_WIDTH = 40;
    public static final float ENEMY_HEIGHT = 25;
    public static final int INIT_NUM_ENEMIES = 50;

    public static void handlePlayerBulletAnimation(Group root) {
        Group playerBulletGroup = (Group) root.getChildren().get(PBGROUP_INDEX);
        List<Node> firedPlayerBullets = playerBulletGroup.getChildren();
        for (int i = 0; i < firedPlayerBullets.size(); i++) {
            Node n = firedPlayerBullets.get(i);
            n.setLayoutY(n.getLayoutY() - PLAYER_BULLET_SPEED);

            boolean isStrike = checkStrikeAlien(n, root);
            if (isStrike) {
                firedPlayerBullets.remove(i);
            }
            else if (n.getLayoutY() < 49) {
                firedPlayerBullets.remove(i);
            }
        }
    }

    public static boolean checkStrikeAlien(Node playerBullet, Group root) {
        Set<Integer> remainingEnemies = Enemy.getRemainingEnemies();
        for (int i = 0; i < INIT_NUM_ENEMIES; i++) {
            if (!remainingEnemies.contains(i)) {
                continue;
            }
            Node enemy = root.getChildren().get(i);
            if ((playerBullet.getLayoutX() >= enemy.getLayoutX() && playerBullet.getLayoutX() < enemy.getLayoutX() + ENEMY_WIDTH)
                    && (playerBullet.getLayoutY() <= enemy.getLayoutY() + ENEMY_HEIGHT)) {
                //player bullet strikes an alien
                Enemy.enemyDestroyed(enemy, i);
                return true;
            }
        }
        return false;
    }
}
