package cz.vianel.drawing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenController  implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(ScreenController.class.getSimpleName());

    private Drawing drawing;

    @FXML
    public Button openImage;

    @FXML
    public Button cropImage;

    @FXML
    public ComboBox cropImageRatio;

    @FXML
    private ImageView imageView;


    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawing = new Drawing();
        imageView.fitWidthProperty().bind(borderPane.widthProperty());
        imageView.fitHeightProperty().bind(borderPane.heightProperty());
        imageView.imageProperty().bind(drawing.imageProperty());
        cropImage.disableProperty().bind(drawing.imageReadyPropertyProperty());
        cropImageRatio.disableProperty().bind(drawing.imageReadyPropertyProperty());
    }

    public void openImage(ActionEvent actionEvent) {
        //Show open file dialog
        File file = Drawing.IMAGE_FILE_CHOOSER.showOpenDialog(null);
        if (file != null && file.exists()) {
            drawing.openImage(file);
            LOGGER.log(Level.INFO, String.format("Image %s open", file.getAbsoluteFile()));
        } else {
            LOGGER.log(Level.INFO, "Open new imageProperty canceled.");
        }
    }

    public void cropImage(ActionEvent actionEvent) {
        LOGGER.log(Level.INFO, "cropImage");
    }

    public void cropImageRation(ActionEvent actionEvent) {
        LOGGER.log(Level.INFO, "cropImageRation");
    }
}
