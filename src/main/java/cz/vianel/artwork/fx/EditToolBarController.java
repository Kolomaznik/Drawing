package cz.vianel.artwork.fx;

import cz.vianel.artwork.ActionLock;
import cz.vianel.artwork.Artwork;
import cz.vianel.artwork.MainArtwork;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private Rectangle CropRectangle;

    @FXML HBox toolBar;
    @FXML HBox actionControlls;

    private static class Dimensions {
        public double x;
        public double y;
        public double width;
        public double height;

        public Dimensions(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

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
        if (!ActionLock.isLocked()) {
            ActionLock.lock();

            LOG.debug("Image crop action");

            CropRectangle = createCropRectangle();

            Button okBtn = new Button("OK");
            okBtn.getStyleClass().add("editButton");
            okBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    ActionCropImage();
                }
            });

            Button cancelBtn = new Button("Cancel");
            cancelBtn.getStyleClass().add("editButton");
            cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    CancelCropAction();
                }
            });

            actionControlls.getChildren().addAll(
                    okBtn,
                    cancelBtn
            );

            this.imageGroup.getChildren().add(CropRectangle);
        }
    }

    public void rotateImage(ActionEvent actionEvent) {
        if (!ActionLock.isLocked()) {
            ActionLock.lock();
            LOG.debug("Image rotate action");
        }
    }

    public void contrastBrightnessEdit(ActionEvent actionEvent) {
        if (!ActionLock.isLocked()) {
            ActionLock.lock();
            LOG.debug("Image contrastBrightnessEdit action");
        }
    }

    private Rectangle createCropRectangle() {
        Pane wrapperPane = new Pane();

        Point2D actualImagesize = ResisableRectangle.getActualImageSize(imageView);

        Rectangle rec = ResisableRectangle.create(
                actualImagesize.getX()/3,
                actualImagesize.getY()/3,
                actualImagesize.getX()/3,
                actualImagesize.getY()/3,
                imageView,
                Color.rgb(255,255,255,0.75),
                15
        );

        Image img = this.artwork.getImage();

        rec.setFill(Color.rgb(0,0,0,0.4));
        rec.setStroke(Color.WHITE);
        rec.setStrokeWidth(1);
        rec.setStrokeType(StrokeType.INSIDE);

        return rec;
    }

    private void ActionCropImage() {
        Dimensions d = getCropDimensions(CropRectangle, imageView, artwork);

        this.artwork.cropImage((int)d.x, (int)d.y, (int)d.width, (int)d.height);
        MainArtwork.setArtwork(this.artwork);

        removeCropControlls();
        ActionLock.unlock();
    }

    private static Dimensions getCropDimensions(Rectangle rec, ImageView imageView, Artwork artwork) {

        Point2D displaySize = ResisableRectangle.getActualImageSize(imageView);

        double widthRatio = artwork.getImage().getWidth() / displaySize.getX();
        double heightRation = artwork.getImage().getHeight() / displaySize.getY();

        return new Dimensions(
                rec.getX()*widthRatio,
                rec.getY()*widthRatio,
                rec.getWidth()*widthRatio,
                rec.getHeight()*heightRation);
    }

    private void CancelCropAction() {
        removeCropControlls();
        ActionLock.unlock();
    }

    private void removeCropControlls() {
        actionControlls.getChildren().clear();
        imageGroup.getChildren().remove(2, imageGroup.getChildren().size());
    }
}
