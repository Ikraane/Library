package se.kth.ikran.databas.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;


public class LibraryDb implements LibraryDbInterface{

    private final List<Book> books;
    private Connection con;
    private MongoDatabase mongoDatabase;
    private MongoClient mongoClient;

    public LibraryDb() {
        books = new ArrayList<>();
        con = null;
        mongoDatabase = null;
        mongoClient = null;

    }

    /**
     * Denna metod ansluter till databasen
     * @return sant om anslutningen lyckades och falskt om det inte lyckades anlsuta
     * @throws LibraryDbException
     */
    @Override
    public boolean connect() throws LibraryDbException {
        String database = "librarydb";
        String server = "jdbc:mysql://localhost:3306/" + database;
        String user = "anonymous";
        String password = "ikraane133";

        try{
            String port="@librarydb-shard-00-00.uof2a.mongodb.net:27017,librarydb-shard-00-01.uof2a.mongodb.net:27017,librarydb-shard-00-02.uof2a.mongodb.net:27017";
            MongoClientURI mongoClientURI = new MongoClientURI("mongodb://anonymous:ikraane133"+port+"/lop?ssl=true&replicaSet=atlas-ecgqoe-shard-0&authSource=admin&retryWrites=true&w=majority");
            mongoClient = new MongoClient(mongoClientURI);
            mongoDatabase = mongoClient.getDatabase(database);
            System.out.println("SUCCESS");
            return  true;
        } catch (MongoException e)
        {
            e.getMessage();
        }
        return false;
    }

    /**
     * Kopplar från databasen
     * @throws LibraryDbException
     */
    @Override
    public void disconnect() throws LibraryDbException{
        try {
            mongoClient.close();
            System.out.println("Disconnected");
        } catch (MongoException e)
        {
            e.getMessage();
        }


    }

    /**
     * Söker efter bok genom titel
     * @param title den titel man ska söka på
     * @return lista av böcker
     * @throws LibraryDbException
     */
    @Override
    public List<Book> searchBooksByTitle(String title) throws LibraryDbException {
        List<Book> result = new ArrayList<>();
        title = title.toLowerCase();

        try{
            MongoCollection<Document> document = mongoDatabase.getCollection("book");

            FindIterable bookDoc = document.find(eq("title", title));
            for (MongoCursor<Document> indicator = bookDoc.iterator(); indicator.hasNext(); ){
                Document document1 = indicator.next();
                String tit = document1.getString("title");
                String is = document1.getString("isbn");
                Genre gen = Genre.valueOf(document1.getString("genre"));
                int rat = document1.getInteger("rating");
                Document author = (Document) document1.get("author");
                String authorID = author.getString("authorID");
                String name = author.getString("name");
                Book book = new Book(tit,is, gen, rat, authorID, name);
                result.add(book);

            }
            return result;

        } catch (MongoException e)
        {
            e.getMessage();
        }
        return result;
    }

    /**
     * Söker efter bok genom titel
     * @param author den författare man ska söka på
     * @return lista av böcker
     * @throws LibraryDbException
     */
    @Override
    public List<Book> searchBooksByAuthor(String author) throws LibraryDbException {
        List<Book> result = new ArrayList<>();

        try{
            MongoCollection<Document> document = mongoDatabase.getCollection("book");

            FindIterable bookDoc = document.find(eq("author.name", author));
            for (MongoCursor<Document> indicator = bookDoc.iterator(); indicator.hasNext(); ){
                Document document1 = indicator.next();
                String tit = document1.getString("title");
                String is = document1.getString("isbn");
                Genre gen = Genre.valueOf(document1.getString("genre"));
                int rat = document1.getInteger("rating");
                Document author1 = (Document) document1.get("author");
                String authorID = author1.getString("authorID");
                String name = author1.getString("name");
                Book book = new Book(tit,is, gen, rat, authorID, name);
                result.add(book);

            }
            return result;

        } catch (MongoException e)
        {
            e.getMessage();
        }
        return result;
    }

    /**
     * Söker efter bok genom titel
     * @param isbn den isbn man ska söka på
     * @return lista av böcker
     * @throws LibraryDbException
     */

    @Override
    public List<Book> searchBooksByISBN(String isbn) throws LibraryDbException {
        List<Book> result = new ArrayList<>();

        try{
            MongoCollection<Document> document = mongoDatabase.getCollection("book");

            FindIterable bookDoc = document.find(eq("isbn", isbn));
            for (MongoCursor<Document> indicator = bookDoc.iterator(); indicator.hasNext(); ){
                Document document1 = indicator.next();
                String tit = document1.getString("title");
                String is = document1.getString("isbn");
                Genre gen = Genre.valueOf(document1.getString("genre"));
                int rat = document1.getInteger("rating");
                Document author1 = (Document) document1.get("author");
                String authorID = author1.getString("authorID");
                String name = author1.getString("name");
                Book book = new Book(tit,is, gen, rat, authorID, name);
                result.add(book);

            }
            return result;

        } catch (MongoException e)
        {
            e.getMessage();
        }
        return result;
    }

    /**
     * Söker efter bok genom titel
     * @param rating det betyg man ska söka på
     * @return lista av böcker
     * @throws LibraryDbException
     */

    @Override
    public List<Book> searchBooksByRating(int rating) throws LibraryDbException {
        List<Book> result = new ArrayList<>();

        try{
            MongoCollection<Document> document = mongoDatabase.getCollection("book");

            FindIterable bookDoc = document.find(eq("rating", rating));
            for (MongoCursor<Document> indicator = bookDoc.iterator(); indicator.hasNext(); ){
                Document document1 = indicator.next();
                String tit = document1.getString("title");
                String is = document1.getString("isbn");
                Genre gen = Genre.valueOf(document1.getString("genre"));
                int rat = document1.getInteger("rating");
                Document author1 = (Document) document1.get("author");
                String authorID = author1.getString("authorID");
                String name = author1.getString("name");
                Book book = new Book(tit,is, gen, rat, authorID, name);
                result.add(book);

            }
            return result;

        } catch (MongoException e)
        {
            e.getMessage();
        }
        return result;
    }

    /**
     * Söker efter bok genom titel
     * @param genre den genre man ska söka på
     * @return lista av böcker
     * @throws LibraryDbException
     */
    @Override
    public List<Book> searchBooksByGenre(String genre) throws LibraryDbException {
        List<Book> result = new ArrayList<>();
        genre = genre.toUpperCase();
        try{
            MongoCollection<Document> document = mongoDatabase.getCollection("book");

            FindIterable bookDoc = document.find(eq("genre", genre));
            for (MongoCursor<Document> indicator = bookDoc.iterator(); indicator.hasNext(); ){
                Document document1 = indicator.next();
                String tit = document1.getString("title");
                String is = document1.getString("isbn");
                Genre gen = Genre.valueOf(document1.getString("genre"));
                int rat = document1.getInteger("rating");
                Document author1 = (Document) document1.get("author");
                String authorID = author1.getString("authorID");
                String name = author1.getString("name");
                Book book = new Book(tit,is, gen, rat, authorID, name);
                result.add(book);

            }
            return result;

        } catch (MongoException e)
        {
            e.getMessage();
        }
        return result;
    }

    /**
     * Lägger till en bok i databasen
     * @param book den bok som ska läggas till
     * @throws LibraryDbException
     * @throws SQLException
     */
    @Override
    public void addBook(Book book) throws LibraryDbException, SQLException {
        if(book == null) throw new LibraryDbException("Cannot add\n");
        {
            try {
                Document document = new Document();
                Document document2 = new Document();
                document.append("title", book.getTitle().toLowerCase());
                document.append("isbn", book.getISBN());
                document.append("genre", book.getGenre().toString());
                document.append("rating", book.getRating());
                document.append("author", document2.append("authorID", book.getAuthorID()).append("name", book.getAuthorName().toLowerCase()));
                mongoDatabase.getCollection("book").insertOne(document);
                mongoDatabase.getCollection("author").insertOne(document2);
                System.out.println("Det gick!!");
                } catch (MongoException e)
            {
                System.out.println("Fail to add!!!!!!!!");
                e.getMessage();
            }
            }
        }

    /**
     * Lägger till en författare till en bok
     * @param author den författare som ska läggas till
     * @param ISBN isbn till den bok som man ska lägg till författaren
     * @throws LibraryDbException
     * @throws SQLException
     */
    @Override
    public void addAuthor(Author author, String ISBN) throws LibraryDbException, SQLException {
        if(author == null) throw new LibraryDbException("Cannot add\n");{

           try {
               Document document = new Document();
               document.append("authorID", author.getAuthorID());
               document.append("name", author.getName().toLowerCase());
               document.append("book", new Document("isbn", ISBN));
               mongoDatabase.getCollection("author").insertOne(document);
               System.out.println("Det gick att lägga till author!!!");
           } catch (MongoException e)
           {
               e.getMessage();
           }
        }

    }

}