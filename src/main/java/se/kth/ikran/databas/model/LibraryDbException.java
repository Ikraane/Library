package se.kth.ikran.databas.model;

public class LibraryDbException extends Exception{
    public LibraryDbException(String msg, Exception cause) {
        super(msg, cause);
    }

    public LibraryDbException(String msg) {
        super(msg);
    }

    public LibraryDbException() {
        super();
    }
}
