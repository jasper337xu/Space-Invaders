import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SpaceInvaders extends Application {
    float SCREEN_WIDTH = 800;
    float SCREEN_HEIGHT = 600;
    AnimationTimer timer;
    static boolean stopGame = false;
    static boolean launchNextLevel = false;
    int count = 0;
    Scene instructionScene, gameScene;
    enum SCENES {INSTRUCTION_SCENE, GAME_SCENE}
    SCENES currentScene = SCENES.INSTRUCTION_SCENE;

    @Override
    public void start(Stage stage) {
        instructionScene = InstructionScene.createInstructionScene();
        instructionScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setScene(stage, SCENES.GAME_SCENE);
            }
            else if (event.getCode() == KeyCode.Q) {
                exit(stage);
            }
            else if (event.getCode() == KeyCode.DIGIT1) {
                setGameSceneByLevel(stage,1);
            }
            else if (event.getCode() == KeyCode.DIGIT2) {
                setGameSceneByLevel(stage,2);
            }
            else if (event.getCode() == KeyCode.DIGIT3) {
                setGameSceneByLevel(stage,3);
            }
        });

        timer = new AnimationTimer() {
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
                else if (launchNextLevel) {
                    System.out.println("Launching next level");
                    this.stop();
                    int nextLevel = GameSettings.getGameLevel();
                    setGameSceneByLevel(stage, nextLevel);
                    return;
                }
                count++;
                if (count == 60) {
                    EnemyBullet.fireBullet();
                    count = 0;
                }
            }
        };

        stage.setResizable(false);
        stage.setTitle("Space Invaders");
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
        setScene(stage, SCENES.INSTRUCTION_SCENE);
        stage.show();
    }

    void setScene(Stage stage, SCENES scene) {
        currentScene = scene;
        switch (scene) {
            case INSTRUCTION_SCENE:
                stage.setScene(instructionScene);
                break;
            case GAME_SCENE:
                setupGameScene(stage);
                stage.setScene(gameScene);
                timer.start();
                break;
        }
    }

    void setupGameScene(Stage stage) {
        //start a new game
        stopGame = false;
        if (launchNextLevel) {
            GameScene.launchNextLevel = true;
            launchNextLevel = false;
        } else {
            GameSettings.gameLevel = 1;
        }
        gameScene = GameScene.createGameScene();
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.I) {
                setScene(stage, SCENES.INSTRUCTION_SCENE);
            }
            else if (event.getCode() == KeyCode.ENTER) {
                setScene(stage, SCENES.GAME_SCENE);
            }
            else if (event.getCode() == KeyCode.Q) {
                exit(stage);
            }
            else if (event.getCode() == KeyCode.DIGIT1) {
                setGameSceneByLevel(stage,1);
            }
            else if (event.getCode() == KeyCode.DIGIT2) {
                setGameSceneByLevel(stage,2);
            }
            else if (event.getCode() == KeyCode.DIGIT3) {
                setGameSceneByLevel(stage,3);
            }
        });
    }

    void exit(Stage stage) {
        stage.close();
        System.out.println("Quitting");
        System.exit(0);
    }

    void setGameSceneByLevel(Stage stage, int level) {
        setScene(stage, SCENES.GAME_SCENE);
        GameSettings.setGameLevel(level);
    }

}
