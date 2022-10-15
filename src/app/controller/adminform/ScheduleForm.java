package app.controller.adminform;

import app.controller.AdminForm;
import classes.Movie;
import classes.Schedule;
import classes.Ticket;
import database.Data;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ScheduleForm implements Initializable {
    private AdminForm parentController;

    public void initParentController(AdminForm parentController) {
        this.parentController = parentController;
    }

    public ListView<Schedule> scheduleListView;
    public GridPane seatGridPane;
    public ChoiceBox<Movie> movieChoiceBox;
    public DatePicker jadwalDatePicker;
    public Spinner<Integer> hourSpinner, minuteSpinner;
    public TextField hargaTextField;
    private Button[][] seatArray;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieChoiceBox.setItems(Data.getMovieList());
        scheduleListView.setItems(Data.getScheduleList());
        scheduleListView.getItems().sort(Comparator.comparing(Schedule::toString));

        jadwalDatePicker.setConverter(new StringConverter<>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });

        seatArray = new Button[seatGridPane.getRowCount()][seatGridPane.getColumnCount()];
        for (int i = 0; i < seatGridPane.getRowCount(); i++) {
            for (int j = 0; j < seatGridPane.getColumnCount(); j++) {
                Button button = new Button(Character.toString(65 + i) + (j + 1));
                button.getStyleClass().add("seat-button");
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setOnAction(this::seatButtonOnAction);
                button.setDisable(true);
                seatArray[i][j] = button;
                seatGridPane.add(button, j, i);
            }
        }

        jadwalDatePicker.setValue(LocalDate.now());
        hourSpinner.getValueFactory().setValue(LocalTime.now().getHour());
        minuteSpinner.getValueFactory().setValue(LocalTime.now().getMinute());
    }

    public void tambahScheduleButtonOnAction() {
        Movie movie = movieChoiceBox.getValue();
        LocalDate date = jadwalDatePicker.getValue();
        LocalTime time = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
        int harga = Integer.parseInt(hargaTextField.getText());

        if (isTextFieldValid()) {
            Schedule schedule = new Schedule(movie, date, time, harga);
            Data.addSchedule(schedule);
            resetScheduleButtonOnAction();
        }
    }

    public void updateScheduleButtonOnAction() {
        Schedule schedule = scheduleListView.getSelectionModel().getSelectedItem();
        if (schedule != null && isTextFieldValid()) {
            schedule.setMovie(movieChoiceBox.getValue());
            schedule.setTanggal(jadwalDatePicker.getValue());
            schedule.setWaktu(LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue()));
            schedule.setHarga(Integer.parseInt(hargaTextField.getText()));
        }
        resetScheduleButtonOnAction();
    }

    public void hapusScheduleButtonOnAction() {
        Schedule schedule = scheduleListView.getSelectionModel().getSelectedItem();
        if (schedule != null) {
            Data.removeSchedule(schedule);
        }
        resetScheduleButtonOnAction();
    }

    public void resetScheduleButtonOnAction() {
        movieChoiceBox.setValue(null);
        jadwalDatePicker.setValue(LocalDate.now());
        hourSpinner.getValueFactory().setValue(LocalTime.now().getHour());
        minuteSpinner.getValueFactory().setValue(LocalTime.now().getMinute());
        scheduleListView.getSelectionModel().clearSelection();
        hargaTextField.setText("");

        for (Button[] i : seatArray) {
            for (Button j : i) {
                j.setDisable(true);
            }
        }
    }

    public void seatButtonOnAction(ActionEvent event) {
        Button selectedButton = (Button)event.getSource();
        Schedule selectedSchedule = scheduleListView.getSelectionModel().getSelectedItem();

        int row = GridPane.getRowIndex(selectedButton), column = GridPane.getColumnIndex(selectedButton);
        Ticket ticket = selectedSchedule.getReservation(row, column);

        parentController.ticketFormController.idTextField.setText(ticket.getTicketID());
        parentController.ticketFormController.searchButtonOnAction();
        parentController.mainPane.getSelectionModel().select(1);
    }

    public void scheduleListViewOnAction() {
        Schedule schedule = scheduleListView.getSelectionModel().getSelectedItem();
        if (schedule != null) {
            movieChoiceBox.setValue(schedule.getMovie());
            jadwalDatePicker.setValue(schedule.getTanggal());
            hourSpinner.getValueFactory().setValue(schedule.getWaktu().getHour());
            minuteSpinner.getValueFactory().setValue(schedule.getWaktu().getMinute());
            hargaTextField.setText(String.valueOf(schedule.getHarga()));

            for (int i = 0; i < seatArray.length; i++) {
                for (int j = 0; j < seatArray[i].length; j++) {
                    if (schedule.getReservation(i, j) != null) {
                        seatArray[i][j].setDisable(false);
                    }
                    else {
                        seatArray[i][j].setDisable(true);
                    }
                }
            }
        }
    }

    private boolean isTextFieldValid() {
        Movie movie = movieChoiceBox.getValue();
        LocalDate date = jadwalDatePicker.getValue();
        LocalTime time = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
        String harga = hargaTextField.getText();

        return (movie != null && date != null && time != null && !harga.equals(""));
    }
}
