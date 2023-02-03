module se.kth.ikran.databas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    //requires javafx.controls;
    requires javafx.base;
    requires java.desktop;
    requires mongo.java.driver;


    opens se.kth.ikran.databas to javafx.base;
    opens se.kth.ikran.databas.model to javafx.base; // open se.kth.ikran.databas.model package for reflection from PropertyValuesFactory (sigh ...)
    exports se.kth.ikran.databas;
    exports se.kth.ikran.databas.view;
    opens se.kth.ikran.databas.view to javafx.base;

    //opens se.kth.ikran.databas to javafx.fxml;
    //exports se.kth.ikran.databas;
}