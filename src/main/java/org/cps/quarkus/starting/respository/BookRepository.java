package org.cps.quarkus.starting.respository;

import org.cps.quarkus.starting.model.Book;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class BookRepository implements IBookRepository {

    @Override
    public List<Book> getAllBooks() {
        return List.of(
                new Book(1, "Understanding Quarkus", "Antonio", "IT", 2020),
                new Book(1, "Practising Quarkus", "Antonio", "IT", 2020)
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
