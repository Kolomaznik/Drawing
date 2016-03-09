package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
        this.artwork.imageProperty().addListener((observable, oldValue, newValue) -> switchDisplayGrid(newValue != null));
        this.artwork.showGridProperty().addListener((observable, oldValue, newValue) -> switchDisplayGrid(newValue));
        this.artwork.ratioProperty().addListener((observable, oldValue, newValue) -> switchDisplayGrid(artwork.isShowGrid()));
        this.borderPane.widthProperty().addListener((observable, oldValue, newValue) -> switchDisplayGrid(artwork.isShowGrid()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.imageView.fitWidthProperty().bind(borderPane.widthProperty());
        this.imageView.fitHeightProperty().bind(borderPane.heightProperty()
                .subtract(mainToolBarController.toolBar.heightProperty())
                .subtract(editToolBarController.toolBar.heightProperty())
        );
    }

    private Group gridCanvas;

    public void switchDisplayGrid(boolean show) {
        LOG.trace("switchDisplayGrid(show: {})", show);

        imageGroup.getChildren().remove(gridCanvas);
        if (!show || artwork.getImage() == null) return;

        gridCanvas = new Group();

        double imageWidth = imageView.getFitWidth();
        double imageHeight = imageView.getFitHeight();
        Artwork.Ratio ratio = artwork.getRatio();
        int size = Double.valueOf(Math.max(imageWidth / ratio.width, imageHeight / ratio.height)).intValue();

        for (int r = size; r < imageHeight; r += size) {
            Line lineB = new Line(0, r-1, imageWidth, r-1);
            lineB.setStroke(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
            Line lineA = new Line(0, r+1, imageWidth, r+1);
            lineA.setStroke(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
            gridCanvas.getChildren().add(lineB);
            gridCanvas.getChildren().add(lineA);
        }

        for (int c = size; c < imageWidth; c+= size) {
            Line lineU = new Line(c-1, 0, c-1, imageHeight);
            lineU.setStroke(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
            Line lineD = new Line(c+1, 0, c+1, imageHeight);
            lineD.setStroke(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
            gridCanvas.getChildren().add(lineU);
            gridCanvas.getChildren().add(lineD);
        }

        for (int r = size; r < imageHeight; r += size) {
            Line line = new Line(0, r, imageWidth, r);
            gridCanvas.getChildren().add(line);
        }

        for (int c = size; c < imageWidth; c+= size) {
            Line line = new Line(c, 0, c, imageHeight);
            gridCanvas.getChildren().add(line);
        }

        imageGroup.getChildren().add(gridCanvas);

    }


}
