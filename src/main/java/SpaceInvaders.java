import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SpaceInvaders extends Application {
    float SCREEN_WIDTH = 800;
    float SCREEN_HEIGHT = 600;
    static boolean stopGame = false;
    int count = 0;

    @Override
    public void start(Stage stage) {
        setScene(stage, GameScene.createGameScene());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Player.handlePlayerAnimation(GameScene.root);
                PlayerBullet.handlePlayerBulletAnimation(GameScene.root);
                Enemy.handleEnemyAnimation(GameScene.root);
                EnemyBullet.handleEnemyBulletAnimation(GameScene.root);
                if (stopGame) {
                    System.out.println("STOP now");
                    this.stop();
                    return;
                }
                count++;
                if (count == 60) {
                    EnemyBullet.fireBullet();
                    count = 0;
                }
            }
        };
        timer.start();

        stage.setResizable(false);
        stage.setTitle("Space Invaders");
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
        stage.show();
    }

    void setScene(Stage stage, Scene scene) {
        stage.setScene(scene);
    }

}
