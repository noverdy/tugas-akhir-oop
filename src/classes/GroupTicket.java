package classes;

import util.Hash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupTicket extends Ticket implements Serializable {
    private final ArrayList<String> seats;

    public GroupTicket(String nama, String nik, String email, int harga, Schedule schedule, ArrayList<String> seats) {
        super(nama, nik, email, harga, schedule);
        this.seats = seats;
    }

    public String getTicketID() {
        return "G/" + Hash.of(getNama()) + "/" + Hash.of(getSchedule()) + "/" + Hash.of(getSeats());
    }

    public List<String> getSeats() {
        return Collections.unmodifiableList(seats);
    }
}
