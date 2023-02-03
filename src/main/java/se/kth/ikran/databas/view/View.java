package se.kth.ikran.databas.view;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import se.kth.ikran.databas.model.*;


public class View extends VBox {
    private TableView<Book> booksTable;
    private ObservableList<Book> booksInTable; // the data backing the table view
    private Controller controller;
    private ComboBox<SearchMode> searchModeBox;
    private TextField searchField;
    private Button searchButton;
    private Book book;
    private MenuBar menuBar;
    private AddBookDialogs dialog = new AddBookDialogs();
    private Button addAuthorButton = new Button();
    private AddAuthorDialog authorDialog = new AddAuthorDialog();

    public View(LibraryDb libraryDb) {
        controller = new Controller(libraryDb, this);
        this.init(controller);
    }

    /**
     * Display a new set of books, e.g. from a database select, in the
     * booksTable table view.
     *
     * @param books the books to display
     */
    public void displayBooks(List<Book> books) {
        booksInTable.clear();
        booksInTable.addAll(books);
        /*booksInTable.addListener(new ListChangeListener<Book>() {
            @Override
            public void onChanged(Change<? extends Book> change) {
                System.out.println(change.getFrom());
            }
        });*/
    }

    /**
     * Notify user on input error or exceptions.
     *
     * @param msg the message
     * @param type types: INFORMATION, WARNING et c.
     */
    protected void showAlertAndWait(String msg, Alert.AlertType type) {
        // types: INFORMATION, WARNING et c.
        Alert alert = new Alert(type, msg);
        alert.showAndWait();
    }


    private void init(Controller controller) {

        booksInTable = FXCollections.observableArrayList();

        // init views and event handlers
        initBooksTable();
        initSearchView(controller);
        initMenus();

        FlowPane bottomPane = new FlowPane();
        bottomPane.setHgap(10);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.getChildren().addAll(searchModeBox, searchField, searchButton);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(booksTable);
        mainPane.setBottom(bottomPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));

        this.getChildren().addAll(menuBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);
    }

    private void initBooksTable() {
        booksTable = new TableView<>();
        booksTable.setEditable(false); // don't allow user updates (yet)
        booksTable.setPlaceholder(new Label("No rows to display"));

        // define columns
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        TableColumn<Book, Integer> ratingCol = new TableColumn<>("Rating");
        TableColumn<Book, String> genreCol = new TableColumn<>("Genre");
        booksTable.getColumns().addAll(titleCol, isbnCol, authorCol, ratingCol, genreCol);
        // give title column some extra space
        titleCol.prefWidthProperty().bind(booksTable.widthProperty().multiply(0.5));

        // define how to fill data for each cell,
        // get values from Book properties
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authors"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        // associate the table view with the data
        booksTable.setItems(booksInTable);
    }

    private void initSearchView(Controller controller) {
        searchField = new TextField();
        searchField.setPromptText("Search for...");
        searchModeBox = new ComboBox<>();
        searchModeBox.getItems().addAll(SearchMode.values());
        searchModeBox.setValue(SearchMode.Title);
        searchModeBox.setValue(SearchMode.ISBN);
        searchModeBox.setValue(SearchMode.Author);
        searchModeBox.setValue(SearchMode.Rating);
        searchButton = new Button("Search");

        // event handling (dispatch to controller)
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String searchFor = searchField.getText();
                SearchMode mode = searchModeBox.getValue();
                controller.onSearchSelected(searchFor, mode);
            }
        });

        /*addAuthorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Optional<Author> result = authorDialog.showAndWait();
                if (result.isPresent()) {
                    Author author = result.get();
                    controller.handleAddAuthor(author,"123" );
                    System.out.println("Result: " + author.toString());
                } else {
                    System.out.println("Canceled");
                }
            }
        });*/

    }


    private void initMenus() {
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        EventHandler<ActionEvent> exitHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    controller.handleDisconnect();
                } catch (LibraryDbException e) {
                    e.printStackTrace();
                }
            }
        };
        exitItem.addEventHandler(ActionEvent.ACTION, exitHandler);

        MenuItem connectItem = new MenuItem("Connect to Db");
        EventHandler<ActionEvent> connectHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    controller.handleConnect();
                    //controller.diplayAll();
                } catch (LibraryDbException e) {
                    e.printStackTrace();
                }
            }
        };
        connectItem.addEventHandler(ActionEvent.ACTION, connectHandler);

        MenuItem disconnectItem = new MenuItem("Disconnect");
        EventHandler<ActionEvent> disconnectHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    controller.handleDisconnect();
                } catch (LibraryDbException e) {
                    e.printStackTrace();
                }
            }
        };
        disconnectItem.addEventHandler(ActionEvent.ACTION, disconnectHandler);
        fileMenu.getItems().addAll(exitItem, connectItem, disconnectItem);


        Menu searchMenu = new Menu("Search");
        MenuItem titleItem = new MenuItem("Title");
        EventHandler<ActionEvent> titleSearchHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
        MenuItem isbnItem = new MenuItem("ISBN");
        EventHandler<ActionEvent> ISBNSearchHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
        MenuItem authorItem = new MenuItem("Author");
        EventHandler<ActionEvent> authorSearchHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
        MenuItem ratingItem = new MenuItem("Rating");
        EventHandler<ActionEvent> ratingSearchHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
        MenuItem genreItem = new MenuItem("Genre");
        EventHandler<ActionEvent> genreSearchHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
        searchMenu.getItems().addAll(titleItem, isbnItem, authorItem, ratingItem, genreItem);

        Menu manageMenu = new Menu("Manage");
        MenuItem addItem = new MenuItem("Add");
        EventHandler<ActionEvent> addHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<Book> result = dialog.showAndWait();
                if (result.isPresent()) {
                    Book book = result.get();
                    try {
                        controller.handleAdd(book);
                    } catch (LibraryDbException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Result: " + book.toString());
                } else {
                    System.out.println("Canceled");
                }
            }
        };
        addItem.addEventHandler(ActionEvent.ACTION, addHandler);

        MenuItem addAuthorItem = new MenuItem("Add Author");
        EventHandler<ActionEvent> addAuthorHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<Author> result = authorDialog.showAndWait();
                if (result.isPresent()) {
                    Author author = result.get();
                    controller.handleAddAuthor(author, author.getISBN());
                    System.out.println("Result: " + author.toString());
                } else {
                    System.out.println("Canceled");
                }
            }
        };
        addAuthorItem.addEventHandler(ActionEvent.ACTION, addAuthorHandler);

        //MenuItem removeItem = new MenuItem("Remove");
        //MenuItem updateItem = new MenuItem("Update");
        manageMenu.getItems().addAll(addItem, addAuthorItem);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, searchMenu, manageMenu);
    }

}