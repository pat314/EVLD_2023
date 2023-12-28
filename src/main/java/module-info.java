module com.mydictionaryfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires voicerss.tts;
    requires freetts;
    requires javafx.web;
    requires com.zaxxer.hikari;
    requires jlayer;
    requires org.jsoup;
    requires java.ocr.api;


    opens com.example.final_dictionary to javafx.fxml;
    exports com.example.final_dictionary;
}