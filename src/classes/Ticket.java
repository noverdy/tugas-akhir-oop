package classes;

import java.io.Serializable;

public abstract class Ticket implements Serializable {
    private final String nama;
    private final String nik;
    private final String email;
    private final int harga;
    private final Schedule schedule;

    public Ticket(String nama, String nik, String email, int harga, Schedule schedule) {
        this.nama = nama;
        this.nik = nik;
        this.email = email;
        this.harga = harga;
        this.schedule = schedule;
    }

    public abstract String getTicketID();

    public String getNama() {
        return nama;
    }

    public String getNik() {
        return nik;
    }

    public String getEmail() {
        return email;
    }

    public int getHarga() {
        return harga;
    }

    public Schedule getSchedule() {
        return schedule;
    }
}