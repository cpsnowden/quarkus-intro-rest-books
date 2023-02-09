package org.cps.quarkus.starting.respository;

import org.cps.quarkus.starting.model.Book;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Optional;

public interface IBookRepository {
    @GET
    List<Book> getAllBooks();

    Optional<Book> getBook(@PathParam("id") int id);
}
