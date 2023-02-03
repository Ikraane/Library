package se.kth.ikran.databas.view;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import se.kth.ikran.databas.model.*;

import java.util.*;

import se.kth.ikran.databas.model.Book;
import se.kth.ikran.databas.model.LibraryDbException;
import se.kth.ikran.databas.model.LibraryDbInterface;
import se.kth.ikran.databas.model.SearchMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.*;

public class Controller{
    private final View libraryView; // view
    private final LibraryDbInterface libraryDb; // model

    public Controller(LibraryDbInterface libraryDb, View libraryView) {
        this.libraryDb = libraryDb;
        this.libraryView = libraryView;
    }

    protected void onSearchSelected(String searchFor, SearchMode mode) {
        try {
            if (searchFor != null && searchFor.length() > 0) {

                switch (mode) {
                    case Title:
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    List<Book> result = libraryDb.searchBooksByTitle(searchFor);
                                    javafx.application.Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            libraryView.displayBooks(result);
                                        }
                                    });

                                } catch (LibraryDbException e) {
                                    libraryView.showAlertAndWait("Database error.", ERROR);
                                }
                            }
                        }.start();
                        break;
                    case ISBN:
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    List<Book> result = libraryDb.searchBooksByISBN(searchFor);
                                    ;
                                    javafx.application.Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            libraryView.displayBooks(result);
                                        }
                                    });

                                } catch (LibraryDbException e) {
                                    libraryView.showAlertAndWait("Database error.", ERROR);
                                }
                            }
                        }.start();
                        break;
                    case Author:
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    List<Book> result = libraryDb.searchBooksByAuthor(searchFor);
                                    javafx.application.Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            libraryView.displayBooks(result);
                                        }
                                    });

                                } catch (LibraryDbException e) {
                                    libraryView.showAlertAndWait("Database error.", ERROR);
                                }
                            }
                        }.start();
                        break;
                    case Rating:
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    List<Book> result = libraryDb.searchBooksByRating(Integer.parseInt(searchFor));
                                    javafx.application.Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            libraryView.displayBooks(result);
                                        }
                                    });

                                } catch (LibraryDbException e) {
                                    libraryView.showAlertAndWait("Database error.", ERROR);
                                }
                            }
                        }.start();
                        break;
                    case Genre:
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    List<Book> result = libraryDb.searchBooksByGenre(searchFor);
                                    javafx.application.Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            libraryView.displayBooks(result);
                                        }
                                    });

                                } catch (LibraryDbException e) {
                                    libraryView.showAlertAndWait("Database error.", ERROR);
                                }
                            }
                        }.start();
                        break;

                    default:
                        List<Book> result = new ArrayList<>();
                }

            }
        } catch (Exception e) {
            libraryView.showAlertAndWait("Database error.", ERROR);
        }
    }

    public void handleAdd(Book book) throws LibraryDbException{
        new Thread() {
            @Override
            public void run() {
                try {
                    libraryDb.addBook(book);
                } catch (SQLException throwables) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            libraryView.showAlertAndWait("Error", ERROR);
                        }
                    });
                } catch (LibraryDbException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            libraryView.showAlertAndWait("Error", ERROR);
                        }
                    });
                }
            }
        }.start();
    }



    public void handleConnect()throws LibraryDbException{
        libraryDb.connect();
    }

    public void handleDisconnect() throws LibraryDbException{
        try {
            libraryDb.disconnect();
        } catch (LibraryDbException e){
            libraryView.showAlertAndWait("ERROR! Cannot disconnect", ERROR);
        }

    }

    public void handleAddAuthor(Author author, String ISBN) {
        new Thread() {
            @Override
            public void run() {
                try {
                    libraryDb.addAuthor(author, ISBN);
                } catch (SQLException throwables) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            libraryView.showAlertAndWait("Error", ERROR);
                        }
                    });
                } catch (LibraryDbException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            libraryView.showAlertAndWait("Error", ERROR);
                        }
                    });
                }
            }
        }.start();
    }

}