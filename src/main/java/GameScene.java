import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameScene {
    public static final int NUM_COL = 10, NUM_ROW = 5;
    public static final float SCREEN_WIDTH = 800;
    public static final float SCREEN_HEIGHT = 600;
    public static final int PLAYER_INDEX = 51;
    enum STATE {STOP, RUN}
    static STATE gameState = STATE.RUN;
    static Group root = null;
    static Group enemyBulletGroup = null;
    public static List<Enemy.ENEMY_TYPE> firedEnemies = new ArrayList<>();
    static Group playerBulletGroup = null;

    public static Scene createGameScene() {
        root = new Group();
        addEnemiesToGroup(root);
        enemyBulletGroup = new Group();
        root.getChildren().add(enemyBulletGroup);
        Enemy.init();
        addPlayerToGroup(root);
        playerBulletGroup = new Group();
        root.getChildren().add(playerBulletGroup);

        Scene scene = new Scene(root, Color.BLACK);
        setupEventHandler(scene);
        return scene;
    }

    public static void setupEventHandler(Scene scene) {
        EventHandler<KeyEvent> sceneHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.A) {
                    Player.handlePlayerMoveLeft();
                }
                else if (e.getCode() == KeyCode.D) {
                    Player.handlePlayerMoveRight();
                }
                else if (e.getCode() == KeyCode.SPACE) {
                    addPlayerBulletToGroup();
                }
            }
        };
        scene.addEventHandler(KeyEvent.KEY_PRESSED, sceneHandler);

        EventHandler<KeyEvent> sceneHandler2 = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() != KeyCode.SPACE) {
                    Player.handleStopMoving();
                }
            }
        };
        scene.addEventHandler(KeyEvent.KEY_RELEASED, sceneHandler2);

    }

    public static void stopGame() {
        gameState = STATE.STOP;
        SpaceInvaders.stopGame = true;
    }

    private static void addEnemiesToGroup(Group root) {
        Image image = null;
        ImageView imageView = null;
        for (int j = 0; j < NUM_COL; j++) {
            image = new Image("enemy3.png", 40, 25, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(40);
            imageView.setFitHeight(25);
            imageView.setLayoutX(150 + j * 50);
            imageView.setLayoutY(80);
            root.getChildren().add(imageView);
        }
        for (int j = 0; j < NUM_COL * 2; j++) {
            image = new Image("enemy2.png", 40, 25, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(40);
            imageView.setFitHeight(25);
            imageView.setLayoutX(150 + (j % NUM_COL) * 50);
            if (j < NUM_COL) {
                imageView.setLayoutY(115);
            } else {
                imageView.setLayoutY(150);
            }
            root.getChildren().add(imageView);
        }
        for (int j = 0; j < NUM_COL * 2; j++) {
            image = new Image("enemy1.png", 40, 25, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(40);
            imageView.setFitHeight(25);
            imageView.setLayoutX(150 + (j % NUM_COL) * 50);
            if (j < NUM_COL) {
                imageView.setLayoutY(185);
            } else {
                imageView.setLayoutY(220);
            }
            root.getChildren().add(imageView);
        }
    }

    public static void addEnemyBulletToGroup(int randEnemyIndex) {
        Image bulletImage = null;
        ImageView bulletImageView = null;
        int row = randEnemyIndex / NUM_COL;
        int col = randEnemyIndex % NUM_COL;
        if (row == 0) {
            bulletImage = new Image("bullet3.png", 13, 25, true, true);
            firedEnemies.add(Enemy.ENEMY_TYPE.ENEMY3);
        }
        else if (row == 1 || row == 2) {
            bulletImage = new Image("bullet2.png", 13, 25, true, true);
            firedEnemies.add(Enemy.ENEMY_TYPE.ENEMY2);
        }
        else {
            bulletImage = new Image("bullet1.png", 13, 25, true, true);
            firedEnemies.add(Enemy.ENEMY_TYPE.ENEMY1);
        }
        bulletImageView = new ImageView(bulletImage);
        bulletImageView.setFitWidth(13);
        bulletImageView.setFitHeight(25);
        double layoutX = root.getChildren().get(randEnemyIndex).getLayoutX() + 40 / 2;
        double layoutY = root.getChildren().get(randEnemyIndex).getLayoutY();
        bulletImageView.setLayoutX(layoutX);
        bulletImageView.setLayoutY(layoutY);
        enemyBulletGroup.getChildren().add(bulletImageView);
    }

    //need to store which enemy fires the bullet cuz the speed of bullets fired by different enemies is different
    public static List<Enemy.ENEMY_TYPE> getFiredEnemies() {
        return firedEnemies;
    }

    public static void addPlayerToGroup(Group root) {
        Image playerImage = new Image("player.png", 40, 25, true, true);
        ImageView playerImageView = new ImageView(playerImage);
        playerImageView.setFitWidth(40);
        playerImageView.setFitHeight(25);
        playerImageView.setLayoutX(SCREEN_WIDTH / 2);
        playerImageView.setLayoutY(SCREEN_HEIGHT - 50);
        root.getChildren().add(playerImageView);
    }

    public static void addPlayerBulletToGroup() {
        Image bulletImage = new Image("player_bullet.png", 6, 16, true, true);
        ImageView bulletImageView = new ImageView(bulletImage);
        bulletImageView.setFitWidth(6);
        bulletImageView.setFitHeight(16);
        double layoutX = root.getChildren().get(PLAYER_INDEX).getLayoutX() + 40 / 2;
        double layoutY = root.getChildren().get(PLAYER_INDEX).getLayoutY();
        bulletImageView.setLayoutX(layoutX);
        bulletImageView.setLayoutY(layoutY);
        playerBulletGroup.getChildren().add(bulletImageView);
    }
}
