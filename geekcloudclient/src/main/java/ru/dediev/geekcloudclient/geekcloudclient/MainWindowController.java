package ru.dediev.geekcloudclient.geekcloudclient;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    public Button btn;
    private String pathToFile;
    FileTransferClient fileTransferClient = new FileTransferClient();

    public void fileChoose() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        pathToFile = file.getPath();
        fileTransferClient.sendFile(pathToFile);
    }
}