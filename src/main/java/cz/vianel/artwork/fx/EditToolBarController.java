package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by Honza on 06.03.2016.
 */
public class EditToolBarController implements ArtworkDependent {

    private static final Logger LOG = LoggerFactory.getLogger(EditToolBarController.class);

    private Artwork artwork;

    @FXML ToolBar toolBar;

    @Override
    public void setArtwork(Artwork artwork) {
        LOG.trace("setArtwork(artwork: {})", artwork);
        this.artwork = artwork;
        this.toolBar.visibleProperty().bind(artwork.editImageProperty());
    }
}
