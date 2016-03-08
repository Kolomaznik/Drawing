package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by Honza on 06.03.2016.
 */
public class ToolBarController implements ArtworkDependent {

    private static final Logger LOG = LoggerFactory.getLogger(ToolBarController.class);

    public static final FileChooser IMAGE_FILE_CHOOSER = new FileChooser();
    static {
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        IMAGE_FILE_CHOOSER.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
    }



    private Artwork artwork;

    @FXML ToggleButton editImage;
    @FXML ToolBar toolBar;

    @Override
    public void setArtwork(Artwork artwork) {
        LOG.trace("setArtwork(artwork: {})", artwork);
        this.artwork = artwork;
        this.editImage.disableProperty().bind(artwork.imageProperty().isNull());

    }

    public void openImage(ActionEvent actionEvent) {
        LOG.trace("openImage(ActionEvent: {})", actionEvent);

        File file = IMAGE_FILE_CHOOSER.showOpenDialog(null);
        if (file != null && file.exists()) {
            LOG.debug("Image open from {}", file.getAbsoluteFile());
            artwork.openImage(file);
        } else {
            LOG.debug("Open new imageProperty canceled.");
        }
    }

    public void editImage(ActionEvent actionEvent) {
    }


}
