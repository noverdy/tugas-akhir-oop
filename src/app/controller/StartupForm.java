package app.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import database.Data;
import util.Hash;
import util.NodeAnimation;

import java.net.URL;
import java.util.ResourceBundle;

public class StartupForm implements Initializable {
    public Label minimizeButton, closeButton, wrongCredentialsLabel;
    public HBox titleBar;
    public Button adminButton, beliButton, signInButton;
    public SVGPath backButton;
    public TextField usernameTextField, passwordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeWindowDraggable();
    }

    private double xOffset = 0, yOffset = 0;
    private void makeWindowDraggable() {
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titleBar.setOnMouseDragged(event -> {
            Stage stage = (Stage)titleBar.getScene().getWindow();

            Timeline translucent = new Timeline(
                    new KeyFrame(Duration.millis(25), new KeyValue(stage.opacityProperty(), 0.9))
            );

            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
            translucent.playFromStart();
        });
        titleBar.setOnDragDone(event -> {
            Stage stage = (Stage)titleBar.getScene().getWindow();

            Timeline translucent = new Timeline(
                    new KeyFrame(Duration.millis(100), new KeyValue(stage.opacityProperty(), 1))
            );

            translucent.playFromStart();
        });
        titleBar.setOnMouseReleased(event -> {
            Stage stage = (Stage)titleBar.getScene().getWindow();

            Timeline translucent = new Timeline(
                    new KeyFrame(Duration.millis(100), new KeyValue(stage.opacityProperty(), 1))
            );

            translucent.playFromStart();
        });
    }

    public void minimizeButtonOnClick() {
        Stage currentStage = (Stage)closeButton.getScene().getWindow();
        currentStage.setIconified(true);
    }

    public void closeButtonOnClick() {
        Stage currentStage = (Stage)closeButton.getScene().getWindow();
        currentStage.close();
    }

    public void beliButtonOnAction() throws Exception {
        Stage currentStage = (Stage)beliButton.getScene().getWindow();
        currentStage.close();

        Parent login = FXMLLoader.load(getClass().getResource("/app/form/MainForm.fxml"));
        Stage mainStage = new Stage();

        mainStage.setTitle("Pemesanan Tiket XXD");
        mainStage.initStyle(StageStyle.DECORATED);
        mainStage.setScene(new Scene(login));
        mainStage.setResizable(false);
        mainStage.getIcons().add(new Image("/res/icon.png"));
        mainStage.show();
    }

    public void adminButtonOnAction() throws Exception {
        Stage currentStage = (Stage)adminButton.getScene().getWindow();
        Parent login = FXMLLoader.load(getClass().getResource("/app/form/AdminLoginForm.fxml"));
        currentStage.setScene(new Scene(login));
    }

    public void backButtonOnClick() throws Exception {
        Stage currentStage = (Stage)backButton.getScene().getWindow();
        Parent login = FXMLLoader.load(getClass().getResource("/app/form/StartupForm.fxml"));
        currentStage.setScene(new Scene(login));
    }

    public void signInButtonOnAction() throws Exception {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        String data = Data.getCredentials();
        String hash = Hash.md5(username + "//" + password);

        if (hash != null && hash.equals(data)) {
            Stage currentStage = (Stage)signInButton.getScene().getWindow();
            currentStage.close();

            Parent login = FXMLLoader.load(getClass().getResource("/app/form/AdminForm.fxml"));
            Stage adminStage = new Stage();

            adminStage.setTitle("XXD Admin");
            adminStage.initStyle(StageStyle.DECORATED);
            adminStage.setScene(new Scene(login));
            adminStage.getIcons().add(new Image("/res/icon.png"));
            adminStage.setResizable(false);
            adminStage.show();
        }
        else {
            Timeline show = NodeAnimation.popLabel(wrongCredentialsLabel);
            show.playFromStart();
        }
    }
}
