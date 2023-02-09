package org.cps.quarkus.starting.respository;

import org.cps.quarkus.starting.model.Book;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class BookRepository implements IBookRepository {

    @ConfigProperty(name = "books.genre", defaultValue = "Sci-fi")
    String genre;

    @Override
    public List<Book> getAllBooks() {
        return List.of(
                new Book(1, "Understanding Quarkus", "Antonio", genre, 2020),
                new Book(2, "Practising Quarkus", "Antonio", genre, 2020)
        );
    }

    @Override
    public Optional<Book> getBook(int id) {
        return getAllBooks()
                .stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }
}
