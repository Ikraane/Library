package se.kth.ikran.databas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.kth.ikran.databas.model.LibraryDb;
import se.kth.ikran.databas.view.View;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {

        LibraryDb libraryDb = new LibraryDb(); // model
        // Don't forget to connect to the db, somewhere...

        View root = new View(libraryDb);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Library Database Client");
        // add an exit handler to the stage (X) ?
        primaryStage.setOnCloseRequest(event -> {
            try {
                libraryDb.disconnect();
            } catch (Exception e) {}
        });
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
