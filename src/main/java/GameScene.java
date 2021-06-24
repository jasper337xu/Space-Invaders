import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class GameScene {
    public static final int NUM_COL = 10, NUM_ROW = 5;
    public static double enemySpeed = 0.5;
    public static final float SCREEN_WIDTH = 800;
    public static final float SCREEN_HEIGHT = 600;

    public static Scene createGameScene() {
        Group root = new Group();
        addImageViewsToGroup(root);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handle_animation(root);
            }
        };
        timer.start();

        Scene scene = new Scene(root, Color.BLACK);
        return scene;
    }

    private static void addImageViewsToGroup(Group root) {
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

    static void handle_animation(Group root) {
        boolean changeDirection = false;
        for (Node n : root.getChildren()) {
            if (n instanceof ImageView) {
                n.setLayoutX(n.getLayoutX() + enemySpeed);
                if (n.getLayoutX() < 0 || n.getLayoutX() > SCREEN_WIDTH - 40) {
                    changeDirection = true;
                    break;
                }
            }
        }
        if (changeDirection) {
            enemySpeed *= -1.0f;
        }
    }
}
