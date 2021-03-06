import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class GameScene {
    public static final int NUM_COL = 10, NUM_ROW = 5;
    public static final float SCREEN_WIDTH = 800;
    public static final float SCREEN_HEIGHT = 600;
    public static final int PLAYER_INDEX = 51;
    public static final int SCORE_LABEL_INDEX = 53;
    public static final int LIVES_LABEL_INDEX = 54;
    public static final int LEVEL_LABEL_INDEX = 55;
    public static final int GAMEOVER_VBOX_INDEX = 56;
    public static final int PLAYER_WON_VBOX_INDEX = 57;
    enum STATE {STOP, RUN}
    static STATE gameState = STATE.RUN;
    static Group root = null;
    static Group enemyBulletGroup = null;
    public static List<Enemy.ENEMY_TYPE> firedEnemies = new ArrayList<>();
    static Group playerBulletGroup = null;
    static boolean launchNextLevel = false;

    public static Scene createGameScene() {
        root = new Group();
        addEnemiesToGroup(root);
        enemyBulletGroup = new Group();
        root.getChildren().add(enemyBulletGroup);
        addPlayerToGroup(root);
        playerBulletGroup = new Group();
        root.getChildren().add(playerBulletGroup);
        addScoreLabelToGroup(root);
        addLivesLabel(root);
        addLevelLabel(root);
        addGameOverVBox(root);
        addPlayerWonVBox(root);

        startNewGame();
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

    public static void stopGame(boolean gameSuccess) {
        gameState = STATE.STOP;
        if (!gameSuccess) {
            VBox gameOver = (VBox) root.getChildren().get(GAMEOVER_VBOX_INDEX);
            Label scoreLabel = (Label) gameOver.getChildren().get(1);
            scoreLabel.setText("Final Score: " + Player.getScore());
            gameOver.setVisible(true);
        }
        else {
            VBox playerWon = (VBox) root.getChildren().get(PLAYER_WON_VBOX_INDEX);
            Label scoreLabel = (Label) playerWon.getChildren().get(1);
            scoreLabel.setText("Final Score: " + Player.getScore());
            playerWon.setVisible(true);
        }
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

    public static void addScoreLabelToGroup(Group root) {
        Label scoreLabel;
        if (launchNextLevel) {
            scoreLabel = new Label("Score: " + Player.getScore());
        } else {
            scoreLabel = new Label("Score: 0");
        }
        scoreLabel.setFont(new Font("Arial", 25));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutX(50);
        scoreLabel.setLayoutY(15);
        root.getChildren().add(scoreLabel);
    }

    public static void updateScoreLabel() {
        Node scoreNode = root.getChildren().get(SCORE_LABEL_INDEX);
        Label scoreLabel = (Label) scoreNode;
        scoreLabel.setText("Score: " + Player.getScore());
    }

    public static void addLivesLabel(Group root) {
        Label livesLabel = new Label("Lives: 3");
        livesLabel.setFont(new Font("Arial", 25));
        livesLabel.setTextFill(Color.WHITE);
        livesLabel.setLayoutX(350);
        livesLabel.setLayoutY(15);
        root.getChildren().add(livesLabel);
    }

    public static void updateLivesLabel() {
        Node livesNode = root.getChildren().get(LIVES_LABEL_INDEX);
        Label livesLabel = (Label) livesNode;
        livesLabel.setText("Lives: " + Player.getLives());
    }

    public static void addLevelLabel(Group root) {
        Label levelLabel = new Label("Level: 1");
        levelLabel.setFont(new Font("Arial", 25));
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.setLayoutX(650);
        levelLabel.setLayoutY(15);
        root.getChildren().add(levelLabel);
    }

    public static void updateLevelLabel() {
        Node levelNode = root.getChildren().get(LEVEL_LABEL_INDEX);
        Label levelLabel = (Label) levelNode;
        levelLabel.setText("Level: " + GameSettings.getGameLevel());
    }

    public static void addGameOverVBox(Group root) {
        Label label = new Label("GAME OVER!");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        Label scoreLabel = new Label("Final Score: 0");
        Label enterLabel = new Label("Enter - Start New Game");
        Label instructionLabel = new Label("I - Back to Instructions");
        Label quitLabel = new Label("Q - Quit Game");
        Label levelLabel = new Label("1 or 2 or 3 - Start New Game at a specific level");

        VBox gameOver = new VBox(label, scoreLabel, enterLabel, instructionLabel, quitLabel, levelLabel);
        gameOver.setBackground(new Background(new BackgroundFill(Color.rgb(250, 250, 250),
                CornerRadii.EMPTY, Insets.EMPTY)));
        gameOver.setPrefWidth(400);
        gameOver.setPrefHeight(250);
        gameOver.setAlignment(Pos.CENTER);
        gameOver.setLayoutX(200);
        gameOver.setLayoutY(150);
        gameOver.setVisible(false);
        root.getChildren().add(gameOver);
    }

    public static void addPlayerWonVBox(Group root) {
        Label label = new Label("YOU WON!");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        Label scoreLabel = new Label("Final Score: 2700");
        Label enterLabel = new Label("Enter - Start New Game");
        Label instructionLabel = new Label("I - Back to Instructions");
        Label quitLabel = new Label("Q - Quit Game");
        Label levelLabel = new Label("1 or 2 or 3 - Start New Game at a specific level");

        VBox playerWon = new VBox(label, scoreLabel, enterLabel, instructionLabel, quitLabel, levelLabel);
        playerWon.setBackground(new Background(new BackgroundFill(Color.rgb(250, 250, 250),
                CornerRadii.EMPTY, Insets.EMPTY)));
        playerWon.setPrefWidth(400);
        playerWon.setPrefHeight(250);
        playerWon.setAlignment(Pos.CENTER);
        playerWon.setLayoutX(200);
        playerWon.setLayoutY(150);
        playerWon.setVisible(false);
        root.getChildren().add(playerWon);
    }

    public static void startNewGame() {
        if (launchNextLevel) {
            //level will be set by GameSettings.setGameLevel(level) soon
            Player.startNewGame(1, true);
            launchNextLevel = false;
        } else {
            Player.startNewGame(1, false);
        }
        Enemy.startNewGame(1);
    }
}
