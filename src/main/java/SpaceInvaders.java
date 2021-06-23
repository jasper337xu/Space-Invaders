import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SpaceInvaders extends Application {
    float SCREEN_WIDTH = 800;
    float SCREEN_HEIGHT = 600;

    @Override
    public void start(Stage stage) {
        setScene(stage, InstructionScene.createInstructionScene());
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
