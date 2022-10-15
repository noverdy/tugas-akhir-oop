package classes;

import database.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule implements Serializable {
    private Movie movie;
    private LocalDate tanggal;
    private LocalTime waktu;
    private int harga;
    private final Ticket[][] seat;

    public Schedule(Movie movie, LocalDate tanggal, LocalTime waktu, int harga) {
        this.movie = movie;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.harga = harga;
        seat = new Ticket[12][8];
    }

    public <T extends Ticket> void addReservation(T ticket, int i, int j) {
        seat[i][j] = ticket;
        Data.saveScheduleList();
    }

    public Ticket getReservation(int i, int j) {
        return seat[i][j];
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public LocalTime getWaktu() {
        return waktu;
    }

    public void setWaktu(LocalTime waktu) {
        this.waktu = waktu;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return movie.getJudul() + ", " + getTanggal() + " " + getWaktu();
    }
}
