package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ArtworkController implements Initializable, ArtworkDependent {

    private static final Logger LOG = LoggerFactory.getLogger(ArtworkController.class);

    private Artwork artwork;

    @FXML MainToolBarController mainToolBarController;
    @FXML EditToolBarController editToolBarController;

    @FXML BorderPane borderPane;
    @FXML Group imageGroup;
    @FXML ImageView imageView;

    @Override
    public void setArtwork(Artwork artwork) {
        LOG.trace("setArtwork(artwork: {})", artwork);
        this.artwork = artwork;
        this.mainToolBarController.setArtwork(artwork);
        this.editToolBarController.setArtwork(artwork);
        this.imageView.imageProperty().bind(artwork.imageProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.imageView.fitWidthProperty().bind(borderPane.widthProperty());
        this.imageView.fitHeightProperty().bind(borderPane.heightProperty()
                .subtract(mainToolBarController.toolBar.heightProperty())
                .subtract(editToolBarController.toolBar.heightProperty())
        );
    }

}
