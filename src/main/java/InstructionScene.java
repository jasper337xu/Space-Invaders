import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class InstructionScene {
    public static Scene createInstructionScene() {
        Image logo = new Image("logo.png", 400, 200, true, true);
        ImageView logoView = new ImageView(logo);
        Label label = new Label("Implemented by Zhanhong Xu(20755568)");

        VBox vbox = new VBox(logoView, label);
        vbox.setAlignment(Pos.BASELINE_CENTER);
        VBox.setMargin(logoView, new Insets(10, 0, 0, 0));
        VBox.setMargin(label, new Insets(340, 0, 20, 0));

        Scene instructionScene = new Scene(vbox);

        return instructionScene;
    }
}
