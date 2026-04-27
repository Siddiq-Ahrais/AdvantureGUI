package pack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for the title screen.
 */
public class TitleController {

    @FXML
    private void onStartClicked() {
        try {
            SoundManager.playClick();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pack/game.fxml"));
            Parent gameRoot = loader.load();

            Stage stage = App.getPrimaryStage();
            Scene scene = stage.getScene();
            scene.setRoot(gameRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
