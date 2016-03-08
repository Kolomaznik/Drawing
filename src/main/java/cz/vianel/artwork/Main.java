package cz.vianel.artwork;/**
 * Created by xkoloma1 on 24.02.2016.
 */

import cz.vianel.artwork.fx.ArtworkController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("Start application ARTWORK ... init");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        LOG.debug("Create data model.");
        Artwork artwork = new Artwork();

        LOG.debug("Init user interface.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fx/Artwork.fxml"));
        Parent root = loader.load();

        LOG.debug("Connect data model to user interface.");
        ArtworkController artworkController = loader.<ArtworkController> getController();
        artworkController.initData(artwork);

        LOG.debug("pPolishing user interface.");
        primaryStage.setTitle("Artwork");
        primaryStage.setScene(new Scene(root, 800, 600));

        LOG.info("Start application ARTWORK ... ready.");
        primaryStage.show();

    }
}
