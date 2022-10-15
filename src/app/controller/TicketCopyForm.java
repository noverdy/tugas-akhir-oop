package app.controller;

import classes.GroupTicket;
import classes.Schedule;
import classes.SingleTicket;
import classes.Ticket;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketCopyForm implements Initializable {
    private static Ticket ticket;

    public static void setTicket(Ticket ticket) {
        TicketCopyForm.ticket = ticket;
    }

    public Label judulLabel, tanggalLabel, waktuLabel, seatLabel, idLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Ticket ticket = TicketCopyForm.ticket;
        Schedule show = ticket.getSchedule();

        judulLabel.setText(show.getMovie().getJudul());
        tanggalLabel.setText(String.valueOf(show.getTanggal()));
        waktuLabel.setText(String.valueOf(show.getWaktu()));
        idLabel.setText(ticket.getTicketID());

        if (ticket instanceof SingleTicket) {
            seatLabel.setText(((SingleTicket)ticket).getSeat());
        }
        else {
            seatLabel.setText(String.join(", ", ((GroupTicket)ticket).getSeats()));
        }
    }

    public void copyIdButtonOnAction() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(idLabel.getText());
        clipboard.setContent(content);
    }
}
