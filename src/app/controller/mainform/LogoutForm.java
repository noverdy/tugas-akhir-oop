package app.controller.mainform;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogoutForm {
    public Button logoutButton;

    public void logoutButtonOnAction() throws Exception {
        Stage currentStage = (Stage)logoutButton.getScene().getWindow();
        currentStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/app/form/StartupForm.fxml"));

        Stage startupStage = new Stage();

        startupStage.initStyle(StageStyle.UNDECORATED);
        startupStage.setScene(new Scene(root));
        startupStage.getIcons().add(new Image("/res/icon.png"));
        startupStage.show();
    }
}
