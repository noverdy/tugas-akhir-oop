package app.controller.mainform;

import app.controller.TicketCopyForm;
import classes.Movie;
import classes.Schedule;
import classes.Ticket;
import database.Data;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import util.Hash;
import util.NodeAnimation;

public class CekTiketForm {
    public AnchorPane detailsPane;
    public TextField idTextField;
    public TextArea movieTextArea, showTextArea;
    public Label ticketNotFoundLabel, userLabel;
    public Pane ticketPane;

    public void searchButtonOnAction() {
        String id = idTextField.getText();
        String[] arr = id.split("/");

        Timeline popLabel = NodeAnimation.popLabel(ticketNotFoundLabel);

        if (arr.length != 4 || !(arr[0].equals("S") || arr[0].equals("G"))){
            popLabel.playFromStart();
            detailsPane.setVisible(false);
        }
        else {
            try {
                int showHashCode = Integer.parseInt(arr[2]);
                for (Schedule schedule : Data.getScheduleList()) {
                    if (Hash.of(schedule) == showHashCode) {
                        for (int i = 0; i < 12; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (schedule.getReservation(i, j) != null) {
                                    if (id.equals(schedule.getReservation(i, j).getTicketID())) {
                                        showTicketDetails(schedule.getReservation(i, j));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                popLabel.playFromStart();
                detailsPane.setVisible(false);
            } catch (NumberFormatException n) {
                popLabel.playFromStart();
                detailsPane.setVisible(false);
            }
        }
    }

    private void showTicketDetails(Ticket ticket) {
        detailsPane.setVisible(true);
        TicketCopyForm.setTicket(ticket);
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("/app/form/CheckoutForm.fxml"));
            TicketCopyForm.setTicket(ticket);
            ticketPane.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Schedule show = ticket.getSchedule();
        Movie movie = show.getMovie();

        userLabel.setText("Nama\t\t: " + ticket.getNama() +
                "\nNIK\t\t\t: " + ticket.getNik() +
                "\nE-Mail\t\t: " + ticket.getEmail() +
                "\nHarga Beli\t: Rp" + ticket.getHarga());
        showTextArea.setText("Judul Film\t: " + show.getMovie().getJudul() +
                "\nTanggal\t\t: " + show.getTanggal() +
                "\nWaktu\t\t: " + show.getWaktu() +
                "\nHarga Tiket\t: Rp" + show.getHarga());
        movieTextArea.setText("Judul\t\t: " + movie.getJudul() +
                "\nGenre\t\t: " + movie.getGenre().getType() +
                "\nDirector\t\t: " + movie.getDirector() +
                "\nTahun\t\t: " + movie.getTahun() +
                "\n\nSinopsis\t:\n" + movie.getSinopsis());
    }
}
