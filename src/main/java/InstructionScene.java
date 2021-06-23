import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

public class InstructionScene {
    public static Scene createInstructionScene() {
        Image logo = new Image("logo.png", 400, 200, true, true);
        ImageView logoView = new ImageView(logo);

        Label label = new Label("Instructions");
        label.setFont(new Font("Arial", 30));
        Label enterLabel = new Label("Enter - Start Game");
        enterLabel.setTextAlignment(TextAlignment.CENTER);
        Label moveLabel = new Label("A or ◀, D or  ︎▶ - Move ship left or right");
        Label spaceLabel = new Label("SPACE - Fire!");
        Label quitLabel = new Label("Q - Quit Game");
        Label levelLabel = new Label("1 or 2 or 3 - Start Game at a specific level");
        Label creatorLabel = new Label("Implemented by Zhanhong Xu(20755568)");

        VBox vbox = new VBox(logoView, label, enterLabel, moveLabel, spaceLabel, quitLabel, levelLabel, creatorLabel);
        vbox.setAlignment(Pos.BASELINE_CENTER);
        VBox.setMargin(logoView, new Insets(10, 0, 0, 0));
        VBox.setMargin(label, new Insets(60, 0, 0, 0));
        VBox.setMargin(enterLabel, new Insets(20, 0, 0, 0));
        VBox.setMargin(moveLabel, new Insets(6, 0, 0, 0));
        VBox.setMargin(spaceLabel, new Insets(6, 0, 0, 0));
        VBox.setMargin(quitLabel, new Insets(6, 0, 0, 0));
        VBox.setMargin(levelLabel, new Insets(6, 0, 0, 0));
        VBox.setMargin(creatorLabel, new Insets(120, 0, 20, 0));

        Scene instructionScene = new Scene(vbox, 800, 600);

        return instructionScene;
    }
}
