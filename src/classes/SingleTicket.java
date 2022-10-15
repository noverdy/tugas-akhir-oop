package classes;

import util.Hash;

import java.io.Serializable;

public class SingleTicket extends Ticket implements Serializable {
    private final String seat;

    public SingleTicket(String nama, String nik, String email, int harga, Schedule schedule, String seat) {
        super(nama, nik, email, harga, schedule);
        this.seat = seat;
    }

    public String getTicketID() {
        return "S/" + Hash.of(getNama()) + "/" + Hash.of(getSchedule()) + "/" + Hash.of(getSeat());
    }

    public String getSeat() {
        return seat;
    }
}
