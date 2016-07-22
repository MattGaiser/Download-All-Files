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
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Matthew
 */
public class DownloadAllFiles extends Application {

    File selectedDirectory;
    TextField fileExtension = new TextField();
    TextField urlToDownload = new TextField();

    @Override
    public void start(Stage primaryStage) {

        Label urlLabel = new Label("Paste the URL here:");
        Label fileExten = new Label("Enter File Extension:");
        Text labelDirectory = new Text("No Directory Selected Yet");
        Text messageOutput = new Text();
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
                if (urlToDownload.getText().equals("")) {
                    messageOutput.setFill(Color.web("#ff0000"));
                    messageOutput.setText("No url to download from");
                } else if (fileExtension.getText().equals("")) {
                    messageOutput.setFill(Color.web("#ff0000"));
                    messageOutput.setText("Filetype not selected");
                } else {

                    String extension = fileExtension.getText();
                    String url = urlToDownload.getText();
                    go(url, extension);
                    labelDirectory.setFill(Color.web("#ff0000"));
                    labelDirectory.setText("Complete");
                }
            }
        });

        HBox url = new HBox(urlLabel, urlToDownload);
        url.setSpacing(5);
        url.setAlignment(Pos.CENTER);
        HBox file = new HBox(fileExten, fileExtension);
        file.setSpacing(5);
        file.setAlignment(Pos.CENTER);
        HBox direct = new HBox(directory, labelDirectory);
        direct.setAlignment(Pos.CENTER);
        direct.setSpacing(20);
        VBox root = new VBox();
        root.getChildren().addAll(url, file, direct, btn, messageOutput);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPrefSize(400, 250);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Download All Files");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void go(String url, String extension) {
        WebScanner scanner = new WebScanner();
        try {
            scanner.initiateSearch(url, extension, selectedDirectory);
        } catch (Exception e) {

        }
    }

}
