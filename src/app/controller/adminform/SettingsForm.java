package app.controller.adminform;

import database.Data;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import util.Hash;
import util.NodeAnimation;

public class SettingsForm {
    public AnchorPane aboutPane, credentialsPane;
    public Button logoutButton, passwordButton;
    public TextField oldUsernameTextField, newUsernameTextField, oldPasswordTextField, newPasswordTextField;
    public Label wrongCredentialsLabel, successLabel;

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

    public void passwordButtonOnAction() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1));
        Timeline timeline;
        transition.setNode(aboutPane);
        if (passwordButton.getText().equals("GANTI PASSWORD")) {
            credentialsPane.setDisable(false);
            transition.setToX(-440);
            passwordButton.setText("BATAL GANTI");
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), new KeyValue(credentialsPane.opacityProperty(), 1))
            );
            timeline.setOnFinished(null);
        }
        else {
            transition.setToX(0);
            passwordButton.setText("GANTI PASSWORD");
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), new KeyValue(credentialsPane.opacityProperty(), 0))
            );
            timeline.setOnFinished(e -> credentialsPane.setDisable(!credentialsPane.isDisable()));
        }
        transition.playFromStart();
        timeline.playFromStart();
    }

    public void changeButtonOnAction() {
        String username = oldUsernameTextField.getText();
        String password = oldPasswordTextField.getText();

        String data = Data.getCredentials();

        String hash = Hash.md5(username + "//" + password);

        if (hash != null && hash.equals(data)) {
            String newUsername = newUsernameTextField.getText();
            String newPassword = newPasswordTextField.getText();

            if (newUsername.equals("") || newPassword.length() < 8) {
                successLabel.setText("Invalid Credentials!");
            }
            else {
                successLabel.setText("Credentials changed successfully.");
                String newHash = Hash.md5(newUsername + "//" + newPassword);
                Data.setCredentials(newHash);

                oldUsernameTextField.setText("");
                oldPasswordTextField.setText("");
                newUsernameTextField.setText("");
                newPasswordTextField.setText("");
            }
            Timeline show = NodeAnimation.popLabel(successLabel);
            show.playFromStart();
        }
        else {
            Timeline show = NodeAnimation.popLabel(wrongCredentialsLabel);
            show.playFromStart();
        }
    }
}
