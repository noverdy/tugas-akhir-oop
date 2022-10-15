package app;

import database.Data;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage startupStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("form/StartupForm.fxml"));
        startupStage.setTitle("Welcome To XXD");
        startupStage.setScene(new Scene(root));
        startupStage.initStyle(StageStyle.UNDECORATED);
        startupStage.getIcons().add(new Image("/res/icon.png"));
        startupStage.show();
        Data.init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
