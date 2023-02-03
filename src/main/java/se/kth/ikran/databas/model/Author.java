package se.kth.ikran.databas.model;
import se.kth.ikran.databas.model.Book;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**Denna klass skapar ett författarobjekt
 *
 */
public class Author implements Serializable {
    private String name;
    private String authorID;
    private ArrayList<Book> books;
    private String ISBN;

    public Author(String authorID, String name, String ISBN){
        this.ISBN = ISBN;
        this.name = name;
        this.books = books;
        this.authorID = authorID;

    }

    /** Denna metod hämtar namn på författare
     *
     * @return namnet på författaren
     */

    public String getISBN(){
        return ISBN;
    }
    public String getName(){
        return name;
    }

    /** Denna metod hämtar födelsedatum
     *
     * @return födelsedatum
     */

    public String getAuthorID(){return authorID;}

    public List<Book> getBooks(){
        return List.copyOf(books);
    }

    public void addBook(Book book){
        books.add(book);
    }

    @Override
    public String toString() {
        return name + ", " + authorID;
    }
}