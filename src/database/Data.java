package database;

import classes.Movie;
import classes.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Data {
    //Linked List Start
    private static final LinkedList<Movie> movieLinkedList = new LinkedList<>();

    public static void addMovieLinkedList(Movie movie) {
        movieLinkedList.add(movie);
    }

    public static void removeMovieLinkedList(Movie movie) {
        movieLinkedList.remove(movie);
    }

    public static LinkedList<Movie> getMovieLinkedList() {
        return movieLinkedList;
    }

    public static void printLinkedList() {
        System.out.println("Daftar Film dalam Linked List :");
        if (movieLinkedList.isEmpty()) {
            System.out.println("-");
        }
        else for (Movie movie : movieLinkedList) {
            System.out.println(movie);
        }
        System.out.println();
    }
    //Linked List End

    private static ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private static ObservableList<Schedule> scheduleList = FXCollections.observableArrayList();
    private static String credentials;

    public static void init() {
        try {
            Scanner read = new Scanner(new File("src/database/admin-credentials"));
            credentials = read.nextLine();
            read.close();

            FileInputStream fileIn = new FileInputStream("src/database/movie-list.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<Movie> listMv = (List<Movie>) in.readObject();
            movieList = FXCollections.observableList(listMv);
            in.close();
            fileIn.close();

            fileIn = new FileInputStream("src/database/schedule-list.ser");
            in = new ObjectInputStream(fileIn);
            List<Schedule> listSch = (List<Schedule>) in.readObject();
            scheduleList = FXCollections.observableList(listSch);
            in.close();
            fileIn.close();
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Movie> getMovieList() {
        return movieList;
    }

    public static void addMovie(Movie movie) {
        movieList.add(movie);
        saveMovieList();
    }

    public static void removeMovie(Movie movie) {
        movieList.remove(movie);
        saveMovieList();
    }

    public static void saveMovieList() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/database/movie-list.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            ArrayList<Movie> list = new ArrayList<>(movieList);
            out.writeObject(list);
            fileOut.close();
        } catch (IOException ignored) {
        }
    }

    public static ObservableList<Schedule> getScheduleList() {
        return scheduleList;
    }

    public static void addSchedule(Schedule schedule) {
        scheduleList.add(schedule);
        saveScheduleList();
    }

    public static void removeSchedule(Schedule schedule) {
        scheduleList.remove(schedule);
        saveScheduleList();
    }

    public static void saveScheduleList() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/database/schedule-list.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            ArrayList<Schedule> list = new ArrayList<>(scheduleList);
            out.writeObject(list);
            fileOut.close();
        } catch (IOException ignored) {
        }
    }


    public static String getCredentials() {
        return credentials;
    }

    public static void setCredentials(String credentials) {
        try {
            FileWriter write = new FileWriter("src/database/admin-credentials");
            write.write(credentials);
            write.close();
            Data.credentials = credentials;
        } catch (IOException ignored) {
        }
    }
}
