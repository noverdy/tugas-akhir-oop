package app.controller;

import app.controller.adminform.*;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminForm implements Initializable {
    public TabPane mainPane;

    public TicketForm ticketFormController;
    public ScheduleForm scheduleFormController;
    public MovieForm movieFormController;
    public SettingsForm settingsFormController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scheduleFormController.initParentController(this);
        ticketFormController.initParentController(this);
    }
}