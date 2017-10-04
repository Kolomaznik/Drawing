package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    @FXML StackPane imageGroup;
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
        this.borderPane.heightProperty().addListener((observable, oldValue, newValue) -> switchDisplayGrid(artwork.isShowGrid()));
        this.artwork.gridScaleProperty().addListener((observable, oldValue, newValue) -> switchDisplayGrid(artwork.isShowGrid()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.imageView.fitWidthProperty().bind(borderPane.widthProperty());
        this.imageView.fitHeightProperty().bind(borderPane.heightProperty()
                .subtract(mainToolBarController.toolBar.heightProperty())
                .subtract(editToolBarController.toolBar.heightProperty())
        );
    }

    private Canvas gridCanvas;

    public void switchDisplayGrid(boolean show) {
        LOG.trace("switchDisplayGrid(show: {})", show);

        imageGroup.getChildren().remove(gridCanvas);
        if (!show || artwork.getImage() == null) return;

        LOG.info("grid scale: {}", artwork.getGridScale());
        Artwork.Ratio ratio = artwork.getRatio().applyScale(artwork.getGridScale());
        double square = ratio.squareSize(
                borderPane.getWidth() - 1, // width
                borderPane.getHeight()     // height
                        - mainToolBarController.toolBar.getHeight()
                        - editToolBarController.toolBar.getHeight() - 1);
        double width = ratio.width * square;
        double height = ratio.height * square;


        gridCanvas = new Canvas(width, height);
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        // Bílé obrysy mřížky
        gc.setStroke(Color.WHITE);
        for (double r = square; r < height; r += square) {
            gc.strokeLine(0, r-1, width, r-1);
            gc.strokeLine(0, r+1, width, r+1);
        }
        for (double c = square; c < width; c+= square) {
            gc.strokeLine(c-1, 0, c-1, height);
            gc.strokeLine(c+1, 0, c+1, height);
        }
        // černá mřížka
        gc.setStroke(Color.BLACK);
        for (double r = square; r < height; r += square) {
            gc.strokeLine(0, r, width, r);
        }
        for (double c = square; c < width; c+= square) {
            gc.strokeLine(c, 0, c, height);
        }

        imageGroup.getChildren().add(gridCanvas);
    }


}
