package se.kth.ikran.databas.model;

//https://beginnersbook.com/2017/08/comparable-interface-in-java-with-example/
//https://www.javatpoint.com/java-int-to-string

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**Denna klass skapar en bok
 *
 */
public class Book {

    private static String s;
    private String title;
    private int rating;
    private Genre genre;
    private String isbn;
    private List<Author> authors;
    private String authorName;
    private String authorID;



    public Book(String title, String isbn, Genre genre, int rating, String authorID, String authorName){
        if(!isValidISBN(isbn)) throw new IllegalArgumentException("not a valid isbn");{
            this.title = title;
            this.rating = rating;
            this.authors = new ArrayList<Author>();
            addAuthor(authorID, authorName, isbn);
            this.genre = genre;
            this.isbn = isbn;
            this.authorName = authorName;
            this.authorID = authorID;
        }

    }

    public String getAuthorName(){
        return authorName;
    }

    public String getAuthorID()
    {
        return authorID;
    }

    private static final Pattern ISBN_PATTERN = Pattern.compile("^[0-9]{13}$");  //ÄNDRA TILLBAKA TILL 13
    public static boolean isValidISBN(String isbn){
        return ISBN_PATTERN.matcher(isbn).matches();
    }

    /**
     * Denna metod lägger till författare till listan av författare
     * @param authorID författarens id
     * @param authorName författarens namn
     * @param ISBN bokens isbn
     */
    public void addAuthor(String authorID, String authorName, String ISBN){
        Author author = new Author(authorID, authorName, ISBN);
        authors.add(author);
    }


    /**Denna metod hämtar en kopia av listan av författare
     *
     * @return en kopia av listan av författare
     */
    public List<Author> getAuthors(){
        return List.copyOf(authors);
    }

    /** Denna metod hämtar ISBN
     *
     * @return ISBN
     */
    public String getISBN(){
        return isbn;
    }

    public Genre getGenre(){
        return genre;
    }

    /** Denna metod hämtar titel på boken
     *
     * @return titel
     */
    public String getTitle(){
        return title;
    }

    /**
     * Denna metod hämtar betyget för en bok
     * @return ett betyg
     */
    public int getRating(){
        return rating;
    }

    public void addRating(int ra){
        rating = ra;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", genre=" + genre +
                ", isbn='" + isbn + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorID='" + authorID + '\'' +
                '}';
    }
}