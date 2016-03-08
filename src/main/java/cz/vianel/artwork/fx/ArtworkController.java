package cz.vianel.artwork.fx;

import cz.vianel.artwork.Artwork;
import javafx.fxml.FXML;

import java.util.logging.Logger;

public class ArtworkController /*implements Initializable*/ {

    @FXML private DrawingPanelController drawingPanelController;

    public void initData(Artwork artwork) {
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
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        artwork = new Artwork();
//        imageView.fitWidthProperty().bind(borderPane.widthProperty());
//        imageView.fitHeightProperty().bind(borderPane.heightProperty());
//        imageView.imageProperty().bind(artwork.imageProperty());
//        cropImage.disableProperty().bind(artwork.imageReadyPropertyProperty());
//        cropImageRatio.disableProperty().bind(artwork.imageReadyPropertyProperty());
//    }
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
