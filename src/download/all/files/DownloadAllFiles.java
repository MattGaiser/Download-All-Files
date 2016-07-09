/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package download.all.files;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Matthew
 */
public class DownloadAllFiles extends Application {

    File selectedDirectory;
    TextField urlToDownload = new TextField();

    @Override
    public void start(Stage primaryStage) {

        Label urlLabel = new Label("Paste the URL here");

        Text labelDirectory = new Text();
        //TextField downloadLocation = new TextField ();
        Button directory = new Button();
        directory.setText("Select Folder for Files");

        directory.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                selectedDirectory
                        = directoryChooser.showDialog(primaryStage);

                if (selectedDirectory == null) {
                    labelDirectory.setText("No Directory selected");
                } else {
                    labelDirectory.setText(selectedDirectory.getAbsolutePath());
                }
            }
        });

        Button btn = new Button();
        btn.setText("Download");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String url = urlToDownload.getText();
                go(url);
            }
        });

        VBox root = new VBox();
        root.getChildren().addAll(urlLabel, urlToDownload, directory, labelDirectory, btn);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPrefSize(400, 200);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Download All PDF Files");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void go(String url) {
        WebScanner scanner = new WebScanner();
        try {
            scanner.initiateSearch(url, ".pdf", selectedDirectory);
        } catch (Exception e) {

        }
    }

}