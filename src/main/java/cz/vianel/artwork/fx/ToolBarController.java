package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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


    private static final Image editImageShow = new Image(ToolBarController.class.getResourceAsStream("icon/appbar.edit.png"));
    private static final Image editImageHide = new Image(ToolBarController.class.getResourceAsStream("icon/appbar.edit.box.png"));

    private Artwork artwork;

    @FXML ToolBar toolBar;

    @FXML Button editImage;

    @Override
    public void setArtwork(Artwork artwork) {
        LOG.trace("setArtwork(artwork: {})", artwork);
        this.artwork = artwork;
        this.artwork.editImageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ((ImageView)editImage.getGraphic()).setImage(editImageHide);
            } else {
                ((ImageView)editImage.getGraphic()).setImage(editImageShow);
            }
        });
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
        artwork.setEditImage(!artwork.getEditImage());
    }



}
