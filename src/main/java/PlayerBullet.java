import javafx.scene.Group;
import javafx.scene.Node;

import java.util.List;

public class PlayerBullet {
    public static boolean fireBullet = false;
    public static final int PBGROUP_INDEX = 52;
    public static final double PLAYER_BULLET_SPEED = 6.0;

    public static void handlePlayerFire() {
        fireBullet = true;
    }

    public static void handlePlayerBulletAnimation(Group root) {
        Group playerBulletGroup = (Group) root.getChildren().get(PBGROUP_INDEX);
        List<Node> firedPlayerBullets = playerBulletGroup.getChildren();
        for (int i = 0; i < firedPlayerBullets.size(); i++) {
            Node n = firedPlayerBullets.get(i);
            n.setLayoutY(n.getLayoutY() - PLAYER_BULLET_SPEED);
            if (n.getLayoutY() < 40) {
                firedPlayerBullets.remove(i);
            }
            //TODO: when hit the enemy, also need to remove
        }
    }
}
