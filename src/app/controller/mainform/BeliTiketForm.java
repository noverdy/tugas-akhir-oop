package app.controller.mainform;

import app.controller.TicketCopyForm;
import classes.*;

import database.Data;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.NodeAnimation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BeliTiketForm {
    public ToggleGroup seatToggleGroup;
    public RadioButton singleRadioButton, groupRadioButton;
    public GridPane seatGridPane;
    public ListView<Schedule> scheduleListView;
    public TextField namaTextField, nikTextField, emailTextField;
    public Label errorLabel, detailHargaLabel;
    public TextArea detailsTextArea;

    public Button[][] seatArray;
    public boolean[][] selectedSeat;

    public void initialize() {
        scheduleListView.setItems(Data.getScheduleList());
        scheduleListView.getItems().sort(Comparator.comparing(Schedule::toString));

        seatArray = new Button[seatGridPane.getRowCount()][seatGridPane.getColumnCount()];
        selectedSeat = new boolean[seatGridPane.getRowCount()][seatGridPane.getColumnCount()];

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

        seatToggleGroup.selectedToggleProperty().addListener(e -> resetSeat());
    }

    public void scheduleListViewOnAction() {
        resetSeat();
        Schedule selected = scheduleListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Movie movie = selected.getMovie();
            detailsTextArea.setText("Judul : " + movie.getJudul() +
                    "\nGenre\t: " + movie.getGenre().getType() +
                    "\nDirector\t: " + movie.getDirector() +
                    "\nTahun\t:  " + movie.getTahun() +
                    "\n\nSinopsis\t:\n" + movie.getSinopsis());
            initializeButton(selected);
        }
    }

    public void initializeButton(Schedule schedule) {
        for (int i = 0; i < seatArray.length; i++) {
            for (int j = 0; j < seatArray[i].length; j++) {
                if (schedule.getReservation(i, j) == null) {
                    seatArray[i][j].setDisable(false);
                }
                else {
                    seatArray[i][j].setDisable(true);
                }
            }
        }
    }

    private int posI = -1, posJ = -1;
    private int count = 0;
    public void seatButtonOnAction(ActionEvent event) {
        RadioButton selected = (RadioButton) seatToggleGroup.getSelectedToggle();
        Button selectedButton = (Button)event.getSource();
        int row = GridPane.getRowIndex(selectedButton), column = GridPane.getColumnIndex(selectedButton);
        if (selected == singleRadioButton) {
            if (posI != -1 && posJ != -1) {
                seatArray[posI][posJ].getStyleClass().removeAll("selected-seat-button");
                seatArray[posI][posJ].getStyleClass().add("seat-button");
            }
            posI = row;
            posJ = column;
            seatArray[row][column].getStyleClass().removeAll("seat-button");
            seatArray[row][column].getStyleClass().add("selected-seat-button");
        }
        else {
            if (selectedSeat[row][column]) {
                seatArray[row][column].getStyleClass().removeAll("selected-seat-button");
                seatArray[row][column].getStyleClass().add("seat-button");
                count--;
            }
            else {
                seatArray[row][column].getStyleClass().removeAll("seat-button");
                seatArray[row][column].getStyleClass().add("selected-seat-button");
                count++;
            }
            selectedSeat[row][column] = !selectedSeat[row][column];
        }

        updateLabelHarga();
    }

    public void resetButtonOnAction() {
        namaTextField.setText("");
        nikTextField.setText("");
        emailTextField.setText("");
        detailHargaLabel.setText("-");
        scheduleListView.getSelectionModel().clearSelection();
        detailsTextArea.setText("");
        resetSeat();
        for (Button[] buttons: seatArray) {
            for (Button button: buttons)
                button.setDisable(true);
        }
    }

    public void checkoutButtonOnAction() {
        Schedule selectedShow = scheduleListView.getSelectionModel().getSelectedItem();
        RadioButton selected = (RadioButton) seatToggleGroup.getSelectedToggle();

        if (selectedShow == null || namaTextField.getText().equals("") || nikTextField.getText().equals("") || emailTextField.getText().equals("") || (selected == singleRadioButton && (posI == -1 && posJ == -1)) || (selected == groupRadioButton && count == 0)) {
            Timeline timeline = NodeAnimation.popLabel(errorLabel);
            timeline.playFromStart();
            return;
        }

        if (selected == singleRadioButton) {
            SingleTicket ticket = new SingleTicket(namaTextField.getText(), nikTextField.getText(), emailTextField.getText(), selectedShow.getHarga(), selectedShow, seatArray[posI][posJ].getText());
            TicketCopyForm.setTicket(ticket);
            selectedShow.addReservation(ticket, posI, posJ);
        }
        else {
            ArrayList<String> seats = new ArrayList<>();
            GroupTicket ticket = new GroupTicket(namaTextField.getText(), nikTextField.getText(), emailTextField.getText(), (int)(selectedShow.getHarga() * count * 90/100.0), selectedShow, seats);
            TicketCopyForm.setTicket(ticket);

            for (int i = 0; i < selectedSeat.length; i++) {
                for (int j = 0; j < selectedSeat[i].length; j++) {
                    if (selectedSeat[i][j]) {
                        seats.add(seatArray[i][j].getText());
                        selectedShow.addReservation(ticket, i, j);
                    }
                }
            }
        }

        namaTextField.setText("");
        nikTextField.setText("");
        emailTextField.setText("");

        try {
            Stage popup = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/app/form/CheckoutForm.fxml"));
            popup.setTitle("Ticket Details");
            popup.setScene(new Scene(root));
            popup.showAndWait();
        } catch (Exception e){
            e.printStackTrace();
        }

        resetSeat();
        initializeButton(selectedShow);
    }

    private void resetSeat() {
        posI = -1;
        posJ = -1;
        count = 0;
        for (boolean[] booleans : selectedSeat) {
            Arrays.fill(booleans, false);
        }

        for (Button[] buttons : seatArray) {
            for (Button button : buttons) {
                button.getStyleClass().removeAll("selected-seat-button");
                button.getStyleClass().add("seat-button");
            }
        }

        updateLabelHarga();
    }

    private void updateLabelHarga() {
        Schedule selectedShow = scheduleListView.getSelectionModel().getSelectedItem();
        RadioButton selected = (RadioButton) seatToggleGroup.getSelectedToggle();

        if (selected == singleRadioButton) {
            if (posI == -1 && posJ == -1) detailHargaLabel.setText("-");
            else detailHargaLabel.setText("Harga Tiket : Rp" + selectedShow.getHarga());
        }
        else {
            if (count == 0) detailHargaLabel.setText("-");
            else {
                int totalHarga = selectedShow.getHarga() * count;
                int diskon = (int)(totalHarga * 10/100.0);
                detailHargaLabel.setText("Harga Tiket : Rp" + selectedShow.getHarga() +
                        "\nJumlah Orang : " + count +
                        "\n\nTotal Harga : Rp" + totalHarga +
                        "\nDiskon 10% : Rp" + diskon +
                        "\n\nHarga Akhir : Rp" + (totalHarga - diskon));
            }
        }
    }
}
