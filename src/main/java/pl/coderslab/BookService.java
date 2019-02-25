package pl.coderslab;

import javax.ws.rs.core.Response;
import java.util.List;

public interface BookService {
    public List<Book> getList();

    public Book getBookById(long id) throws ClassNotFoundException;

    public Response addNewBook(Book book);

    public Response updateBook(Book book, long id);

    public Response deleteBookById(long id);

    public void setList(List<Book> list);
}
