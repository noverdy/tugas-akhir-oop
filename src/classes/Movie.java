package classes;

import java.io.Serializable;

public class Movie implements Serializable {
    private String judul;
    private MovieGenre genre;
    private String director;
    private String tahun;
    private String sinopsis;

    public Movie(String judul, MovieGenre genre, String director, String tahun, String sinopsis) {
        this.judul = judul;
        this.genre = genre;
        this.director = director;
        this.tahun = tahun;
        this.sinopsis = sinopsis;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return getJudul() + ", " + getDirector();
    }
}
