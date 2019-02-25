package pl.coderslab;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BookDao implements BookDaoI<Book>{
    private List<Book> listOfAllBooks = new ArrayList<>();

    @Override
    public List getAll() {
        return listOfAllBooks;
    }

    @Override
    public Optional get(long id) {
        return Optional.ofNullable(listOfAllBooks.get((int) id));
    }

    @Override
    public void save(Book book) {
        listOfAllBooks.add(book);
    }

    @Override
    public void update(Book book, String[] params) {
        book.setIsbn(Objects.requireNonNull(params[0],"Isbn cannot be null"));
        book.setTitle(Objects.requireNonNull(params[1],"Title cannot be null"));
        book.setAuthor(Objects.requireNonNull(params[2],"Author cannot be null"));
        book.setPublisher(Objects.requireNonNull(params[3],"Publisher cannot be null"));
        book.setType(Objects.requireNonNull(params[4],"Type cannot be null"));

        listOfAllBooks.add(book);
    }

    @Override
    public void delete(Book book) {
        listOfAllBooks.remove(book);
    }


}
