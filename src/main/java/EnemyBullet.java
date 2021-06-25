import javafx.scene.Group;
import javafx.scene.Node;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class EnemyBullet {
    public static final int EBGROUP_INDEX = 50;
    public static final double ENEMY1_BULLET_SPEED = 4.0;
    public static final double ENEMY2_BULLET_SPEED = 5.0;
    public static final double ENEMY3_BULLET_SPEED = 6.0;
    public static final float SCREEN_HEIGHT = 600;

    public static void fireBullet() {
        Set<Integer> remainingEnemies = Enemy.getRemainingEnemies();
        if (remainingEnemies.isEmpty()) {
            return;
        }
        // convert HashSet to an array
        Integer[] array = remainingEnemies.toArray(new Integer[remainingEnemies.size()]);
        Random rn = new Random();
        int rand = rn.nextInt(remainingEnemies.size());
        GameScene.addEnemyBulletToGroup(array[rand]);
    }

    public static void handleEnemyBulletAnimation(Group root) {
        Group enemyBulletGroup = (Group) root.getChildren().get(EBGROUP_INDEX);
        List<Node> firedEnemyBullets = enemyBulletGroup.getChildren();
        List<Enemy.ENEMY_TYPE> firedEnemies = GameScene.getFiredEnemies();

        for (int i = 0; i < firedEnemyBullets.size(); i++) {
            Node n = firedEnemyBullets.get(i);
            if (firedEnemies.get(i) == Enemy.ENEMY_TYPE.ENEMY1) {
                n.setLayoutY(n.getLayoutY() + ENEMY1_BULLET_SPEED);
            }
            else if (firedEnemies.get(i) == Enemy.ENEMY_TYPE.ENEMY2) {
                n.setLayoutY(n.getLayoutY() + ENEMY2_BULLET_SPEED);
            }
            else if (firedEnemies.get(i) == Enemy.ENEMY_TYPE.ENEMY3) {
                n.setLayoutY(n.getLayoutY() + ENEMY3_BULLET_SPEED);
            }

            if (n.getLayoutY() > SCREEN_HEIGHT) {
                firedEnemyBullets.remove(i);
                firedEnemies.remove(i);
            }
        }
    }
}
