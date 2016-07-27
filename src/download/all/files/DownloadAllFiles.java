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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.UrlValidator;

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

                UrlValidator validator = new UrlValidator();

                if (urlToDownload.getText().equals("")) {
                    messageOutput.setFill(Color.web("#ff0000"));
                    messageOutput.setText("No URL given.");
                } else if (!validator.isValid(urlToDownload.getText())) {
                    messageOutput.setFill(Color.web("#ff0000"));
                    messageOutput.setText("URL is not valid");
                } else if (fileExtension.getText().equals("")) {
                    messageOutput.setFill(Color.web("#ff0000"));
                    messageOutput.setText("Filetype not selected");
                } else if (!validateFileType(fileExtension.getText())) {
                    messageOutput.setFill(Color.web("#ff0000"));
                    messageOutput.setText("A period must be the first char in a file type");
                } else if (labelDirectory.getText().contains("No Directory")) {
                    messageOutput.setFill(Color.web("#ff0000"));
                    messageOutput.setText("Directory not selected");
                } else {

                    int numberOfFiles = 0;
                    String extension = fileExtension.getText();
                    String url = urlToDownload.getText();
                    numberOfFiles = go(url, extension, messageOutput);
                    if (numberOfFiles == 0) {
                        labelDirectory.setFill(Color.web("#ff0000"));
                        labelDirectory.setText("No files found.");
                    } else {
                        labelDirectory.setFill(Color.web("#00ff00"));
                        labelDirectory.setText("Complete. " + numberOfFiles + " files downloaded.");

                    }
                }
            }

            private int go(String url, String extension, Text messageOutput) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                WebScanner scanner = new WebScanner();
                int numberOfFiles = 0;
                try {
                    numberOfFiles = scanner.initiateSearch(url, extension, selectedDirectory, messageOutput);
                } catch (Exception e) {
                }
                return numberOfFiles;
            }

            private boolean validateFileType(String fileType) {
                if (fileType.charAt(0) != '.') {
                    return false;
                }
                return true;
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
        primaryStage.getIcons().add(new Image("http://images.gofreedownload.net/montreal-metro-clip-art-13225.jpg"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    /*
    public int go(String url, String extension){
        WebScanner scanner = new WebScanner();
        int numberOfFiles = 0;
        try {
            numberOfFiles = scanner.initiateSearch(url, extension, selectedDirectory, message);
        } catch (Exception e) {
        }
        return numberOfFiles;
    }
     */

}
