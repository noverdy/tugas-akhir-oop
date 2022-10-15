package app.controller.adminform;

import classes.Movie;
import classes.MovieGenre;
import database.Data;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MovieForm implements Initializable {
    public ListView<Movie> movieListView;
    public TextField judulTextField, directorTextField, tahunTextField;
    public ChoiceBox<MovieGenre> genreChoiceBox;
    public TextArea sinopsisTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieListView.setItems(Data.getMovieList());
//        movieListView.getItems().addAll(Data.getMovieLinkedList()); //Get all data from Linked List
//        movieListView.getItems().sort(Comparator.comparing(Movie::toString));
        genreChoiceBox.getItems().setAll(MovieGenre.values());
    }

    public void tambahMovieButtonOnAction() {
        String judul = judulTextField.getText();
        MovieGenre genre = genreChoiceBox.getValue();
        String director = directorTextField.getText();
        String tahun = tahunTextField.getText();
        String sinopsis = sinopsisTextArea.getText();

        if (isTextFieldValid()) {
            Movie movie = new Movie(judul, genre, director, tahun, sinopsis);
            Data.addMovie(movie);
//            Data.addMovieLinkedList(movie); //Add Movie to Linked List
//            movieListView.getItems().add(movie); //Add Movie to ListView
            resetMovieButtonOnAction();
        }
    }

    public void printLinkedList() {
        Data.printLinkedList();
    }

    public void updateMovieButtonOnAction() {
        Movie movie = movieListView.getSelectionModel().getSelectedItem();
        if (movie != null && isTextFieldValid()) {
            movie.setJudul(judulTextField.getText());
            movie.setGenre(genreChoiceBox.getValue());
            movie.setDirector(directorTextField.getText());
            movie.setTahun(tahunTextField.getText());
            movie.setSinopsis(sinopsisTextArea.getText());
            Data.saveMovieList();
            resetMovieButtonOnAction();
        }
    }

    public void hapusMovieButtonOnAction() {
        Movie movie = movieListView.getSelectionModel().getSelectedItem();
        if (movie != null) {
            Data.removeMovie(movie);
//            Data.removeMovieLinkedList(movie); //Remove Movie from Linked List
//            movieListView.getItems().remove(movie); //Remove Movie from ListView
            resetMovieButtonOnAction();
        }
    }

    public void resetMovieButtonOnAction() {
        judulTextField.setText("");
        genreChoiceBox.setValue(MovieGenre.ACTION);
        directorTextField.setText("");
        tahunTextField.setText("");
        sinopsisTextArea.setText("");
        movieListView.getSelectionModel().clearSelection();
    }

    public void movieListViewOnAction() {
        Movie movie = movieListView.getSelectionModel().getSelectedItem();
        if (movie != null) {
            judulTextField.setText(movie.getJudul());
            genreChoiceBox.setValue(movie.getGenre());
            directorTextField.setText(movie.getDirector());
            tahunTextField.setText(movie.getTahun());
            sinopsisTextArea.setText(movie.getSinopsis());
        }
    }

    private boolean isTextFieldValid() {
        String judul = judulTextField.getText();
        MovieGenre genre = genreChoiceBox.getValue();
        String director = directorTextField.getText();
        String tahun = tahunTextField.getText();
        String sinopsis = sinopsisTextArea.getText();

        return (!judul.equals("") && genre != null && !director.equals("") && !tahun.equals("") && !sinopsis.equals(""));
    }
}
