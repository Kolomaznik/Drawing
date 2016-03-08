package cz.vianel.artwork;

import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by xkoloma1 on 29.02.2016.
 */
public class Artwork {

    private static final Logger LOG = LoggerFactory.getLogger(Artwork.class);

    private BufferedImage bufferedImage;

    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    public Image getImage() {
        return imageProperty.get();
    }

    public ReadOnlyObjectProperty<Image> imageProperty() {
        return imageProperty;
    }

    public void openImage(File file) {
        LOG.trace("openImage(File {})", file.getAbsoluteFile());
        try {
            bufferedImage = ImageIO.read(file);
            imageProperty.set(SwingFXUtils.toFXImage(bufferedImage, null));
            LOG.info("Image {} successfully opened", file);
        } catch (IOException ex) {
            bufferedImage = null;
            imageProperty.set(null);
            LOG.warn("File can't be open.", ex);
        }
    }
}
