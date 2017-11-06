package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.shape.Rectangle;

/**
 * Created by Honza on 06.03.2016.
 */
public class EditToolBarController implements ArtworkDependent {

    private static final Logger LOG = LoggerFactory.getLogger(EditToolBarController.class);

    private Artwork artwork;
    private Pane imageGroup;
    private ImageView imageView;

    @FXML ToolBar toolBar;

    @Override
    public void setArtwork(Artwork artwork) {
        LOG.trace("setArtwork(artwork: {})", artwork);
        this.artwork = artwork;
        this.toolBar.visibleProperty().bind(artwork.editImageProperty());
    }

    public void setImageGroup(Pane imageGroup) {
        this.imageGroup = imageGroup;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void cropImage(ActionEvent actionEvent) {
        LOG.debug("Image crop action");
        this.imageGroup.getChildren().add(createCropRectangle());
    }

    private Rectangle createCropRectangle() {
        Rectangle rec = ResisableRectangle.create(
                this.imageView.getFitWidth()/3,
                this.imageView.getFitHeight()/3,
                this.imageView.getFitWidth()/3*2,
                this.imageView.getFitHeight()/3*2,
                Color.rgb(255,255,255,0.75),
                5
        );

        Image img = this.artwork.getImage();

        /*rec.setStyle("-fx-fill: #ffff0050");
        rec.setStyle("-fx-stroke: white");
        rec.setStyle("-fx-stroke-width: 3px");
        rec.setStyle("-fx-stroke-type: outside");*/

        rec.setFill(Color.rgb(0,0,0,0.5));
        rec.setStroke(Color.WHITE);
        rec.setStrokeWidth(3);
        rec.setStrokeType(StrokeType.INSIDE);

        return rec;
    }

    public void rotateImage(ActionEvent actionEvent) {
        LOG.debug("Image rotate action");
    }

    public void contrastBrightnessEdit(ActionEvent actionEvent) {
        LOG.debug("Image contrastBrightnessEdit action");
    }
}
