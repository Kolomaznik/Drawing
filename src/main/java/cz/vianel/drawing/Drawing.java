package cz.vianel.drawing;

import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by xkoloma1 on 29.02.2016.
 */
public class Drawing {

    private static final Logger LOGGER = Logger.getLogger(Drawing.class.getSimpleName());

    public static final FileChooser IMAGE_FILE_CHOOSER = new FileChooser();
    static {
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        IMAGE_FILE_CHOOSER.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
    }

    private BufferedImage bufferedImage;

    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    public Image getImage() {
        return imageProperty.get();
    }

    public ReadOnlyObjectProperty<Image> imageProperty() {
        return imageProperty;
    }

    private BooleanProperty imageReadyProperty = new SimpleBooleanProperty(true);


    public boolean isImageReady() {
        return imageReadyProperty.get();
    }

    public ReadOnlyBooleanProperty imageReadyPropertyProperty() {
        return imageReadyProperty;
    }

    public void openImage(File file) {
        try {
            bufferedImage = ImageIO.read(file);
            imageProperty.set(SwingFXUtils.toFXImage(bufferedImage, null));
            imageReadyProperty.set(false);
            LOGGER.log(Level.FINE, String.format("Image %s successfully opened", file));
        } catch (IOException ex) {
            bufferedImage = null;
            imageProperty.set(null);
            imageReadyProperty.set(true);
            LOGGER.log(Level.SEVERE, "File can't be open.", ex);
        }
    }
}
