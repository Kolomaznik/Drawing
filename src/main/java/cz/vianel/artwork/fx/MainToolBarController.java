package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by Honza on 06.03.2016.
 */
public class MainToolBarController implements ArtworkDependent, Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(MainToolBarController.class);

    public static final FileChooser IMAGE_FILE_CHOOSER = new FileChooser();
    static {
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        IMAGE_FILE_CHOOSER.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
    }

    private static final Image editImageShow = new Image(MainToolBarController.class.getResourceAsStream("icon/appbar.edit.png"));
    private static final Image editImageHide = new Image(MainToolBarController.class.getResourceAsStream("icon/appbar.edit.box.png"));

    private static final Image gridShow = new Image(MainToolBarController.class.getResourceAsStream("icon/appbar.page.corner.grid.png"));
    private static final Image gridHide = new Image(MainToolBarController.class.getResourceAsStream("icon/appbar.page.corner.png"));

    private static final String[] choiceRatioLabels = {"1/3", "1/2", "1", "2", "3"};
    private static final double[] choiceRatioValues = {1d/3d, 1d/2d, 1.0, 2.0, 3.0};

    private Artwork artwork;

    @FXML ToolBar toolBar;
    @FXML Button editImageSwitch;
    @FXML Button gridSwitch;
    @FXML Label ratioLabel;
    @FXML ChoiceBox choiceRatio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceRatio.getItems().addAll(choiceRatioLabels);
        choiceRatio.setValue(choiceRatioLabels[2]);
    }

    @Override
    public void setArtwork(Artwork artwork) {
        LOG.trace("setArtwork(artwork: {})", artwork);

        this.artwork = artwork;
        this.artwork.editImageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ((ImageView)editImageSwitch.getGraphic()).setImage(editImageHide);
            } else {
                ((ImageView)editImageSwitch.getGraphic()).setImage(editImageShow);
            }
        });
        this.artwork.showGridProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ((ImageView)gridSwitch.getGraphic()).setImage(gridShow);
            } else {
                ((ImageView)gridSwitch.getGraphic()).setImage(gridHide);
            }
        });
        this.choiceRatio.valueProperty().addListener((observable, oldValue, newValue) -> {
            int index = Arrays.asList(choiceRatioLabels).indexOf(newValue);
            artwork.setGridScale(choiceRatioValues[index]);
        });
        this.editImageSwitch.disableProperty().bind(artwork.imageProperty().isNull());
        this.gridSwitch.disableProperty().bind(artwork.imageProperty().isNull());
        this.choiceRatio.disableProperty().bind(artwork.imageProperty().isNull());
        this.ratioLabel.textProperty().bind(artwork.ratioProperty().asString());
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
        LOG.trace("editImage(ActionEvent: {})", actionEvent);

        artwork.setEditImage(!artwork.isEditImage());
    }


    public void switchGrid(ActionEvent actionEvent) {
        LOG.trace("switchGrid(ActionEvent: {})", actionEvent);

        artwork.setShowGrid(!artwork.isShowGrid());
    }

}
