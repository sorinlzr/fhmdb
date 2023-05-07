module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires annotations;
    requires ormlite.jdbc;
    requires java.sql;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.ui to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb.service;
    exports at.ac.fhcampuswien.fhmdb.filter;
    exports at.ac.fhcampuswien.fhmdb.ui;
    exports at.ac.fhcampuswien.fhmdb.exceptions;
    exports at.ac.fhcampuswien.fhmdb.dao;
    opens at.ac.fhcampuswien.fhmdb.dao to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.models to ormlite.jdbc;
}