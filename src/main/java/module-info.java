module com.fifteenpuzzle.fiteenpuzzlegame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires com.h2database;
    requires java.naming;
    requires java.desktop;
    requires javafx.base;

    requires javafx.media;
    requires javafx.swing;
    requires slf4j.api;

    opens com.fifteenpuzzle.fifteenpuzzlegame to javafx.fxml;
    exports com.fifteenpuzzle.fifteenpuzzlegame;
}