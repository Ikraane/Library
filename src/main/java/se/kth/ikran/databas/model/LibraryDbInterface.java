package se.kth.ikran.databas.model;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

public interface LibraryDbInterface {
    public boolean connect() throws LibraryDbException;

    public void disconnect() throws LibraryDbException;

    public List<Book> searchBooksByTitle(String title) throws LibraryDbException;

    abstract List<Book> searchBooksByAuthor(String author) throws LibraryDbException;

    abstract List<Book> searchBooksByISBN(String isbn) throws LibraryDbException;

    abstract List<Book> searchBooksByRating(int rating) throws LibraryDbException;

    abstract List<Book> searchBooksByGenre(String genre) throws LibraryDbException;

    abstract void addBook(Book book) throws LibraryDbException, SQLException;

    abstract void addAuthor(Author author, String ISBN) throws LibraryDbException, SQLException;
}

