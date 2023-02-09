package org.cps.quarkus.starting.model;

public class Book {

    private final int id;
    private final String title;
    private final String author;
    private final String genre;
    private final int yearOfPublication;

    public Book(int id, String title, String author, String genre, int yearOfPublication) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.yearOfPublication = yearOfPublication;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }
}
