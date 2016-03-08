package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ArtworkController implements Initializable, ArtworkDependent {

    private static final Logger LOG = LoggerFactory.getLogger(ArtworkController.class);

    private Artwork artwork;

    @FXML ToolBarController toolBarController;

    @FXML BorderPane borderPane;
    @FXML Group imageGroup;
    @FXML ImageView imageView;

    @Override
    public void setArtwork(Artwork artwork) {
        LOG.trace("setArtwork(artwork: {})", artwork);
        this.artwork = artwork;
        this.toolBarController.setArtwork(artwork);
        this.imageView.imageProperty().bind(artwork.imageProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.imageView.fitWidthProperty().bind(borderPane.widthProperty());
        this.imageView.fitHeightProperty().bind(borderPane.heightProperty().subtract(toolBarController.toolBar.heightProperty()));
    }

    //private Artwork artwork;
//
//    @FXML
//    public Button openImage;
//
//    @FXML
//    public Button cropImage;
//
//    @FXML
//    public ComboBox cropImageRatio;
//
//    @FXML
//    private ImageView imageView;
//
//
//    @FXML
//    private BorderPane borderPane;

//
//    public void openImage(ActionEvent actionEvent) {
//        //Show open file dialog
//        File file = Artwork.IMAGE_FILE_CHOOSER.showOpenDialog(null);
//        if (file != null && file.exists()) {
//            artwork.openImage(file);
//            LOGGER.log(Level.INFO, String.format("Image %s open", file.getAbsoluteFile()));
//        } else {
//            LOGGER.log(Level.INFO, "Open new imageProperty canceled.");
//        }
//    }
//
//    public void cropImage(ActionEvent actionEvent) {
//        LOGGER.log(Level.INFO, "cropImage");
//    }
//
//    public void cropImageRation(ActionEvent actionEvent) {
//        LOGGER.log(Level.INFO, "cropImageRation");
//    }
}
