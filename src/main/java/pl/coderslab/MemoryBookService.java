package pl.coderslab;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@CrossOrigin
@RestController
@RequestMapping("/books")
public class MemoryBookService implements BookService {
    private List<Book> list;

    public MemoryBookService() {
        list = new ArrayList<>();
        list.add(new Book(1L, "9788324631766", "Thinking in Java", "Bruce Eckel",
                "Helion", "programming"));
        list.add(new Book(2L, "9788324627738", "Rusz glowa, Java.",
                "Sierra Kathy, Bates Bert", "Helion", "programming"));
        list.add(new Book(3L, "9780130819338", "Java 2. Podstawy",
                "Cay Horstmann, Gary Cornell", "Helion", "programming"));
    }

    //get list of all books
    @Override
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Book> getList() {
        return this.list;
    }

    //get book by id
    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Book getBookById(@PathVariable long id) throws ClassNotFoundException {
        Optional<Book> book = list.stream().filter(s -> s.getId() == id).findFirst();
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new ClassNotFoundException("book " + id + " not found");
        }
    }

    //add book to list
    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Response addNewBook(@RequestBody Book book) {
        list.add(book);
        return Response.status(Response.Status.CREATED).build();
    }

    //aktualizacja book
    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Response updateBook(@RequestBody Book book, @PathVariable long id) {
        Optional<Book> bookToUpdate = this.list
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst();

        if(bookToUpdate.isPresent()) {
            int bookIndex = this.list.indexOf(bookToUpdate);
            this.list.set(bookIndex, book);
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //delete book
    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Response deleteBookById(@PathVariable long id) {
        Predicate<Book> book = s -> s.getId() == id;

        if (this.list.removeIf(book)) {
            return Response.status(204).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public void setList(List<Book> list) {
        this.list = list;
    }
}
