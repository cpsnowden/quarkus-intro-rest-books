package org.cps.quarkus.starting.respository;

import org.cps.quarkus.starting.model.Book;

import java.util.List;
import java.util.Optional;

public interface IBookRepository {

    List<Book> getAllBooks();

    Optional<Book> getBook(int id);
}
