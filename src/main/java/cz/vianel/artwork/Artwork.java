package cz.vianel.artwork;

import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;


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


    private BooleanProperty editImage = new SimpleBooleanProperty(false);
    public boolean isEditImage() {
        return editImage.get();
    }
    public BooleanProperty editImageProperty() {
        return editImage;
    }
    public void setEditImage(boolean editImage) {
        this.editImage.set(editImage);
    }

    private BooleanProperty showGrid = new SimpleBooleanProperty(true);
    public boolean isShowGrid() {
        return showGrid.get();
    }
    public BooleanProperty showGridProperty() {
        return showGrid;
    }
    public void setShowGrid(boolean showGrid) {
        this.showGrid.set(showGrid);
    }

    private ObjectProperty<Ratio> ratioProperty = new SimpleObjectProperty<>(NONE);
    public Ratio getRatio() {
        return ratioProperty.get();
    }
    public ReadOnlyObjectProperty<Ratio> ratioProperty() {
        return ratioProperty;
    }

    public void openImage(File file) {
        LOG.trace("openImage(File {})", file.getAbsoluteFile());
        try {
            bufferedImage = ImageIO.read(file);
            imageProperty.set(SwingFXUtils.toFXImage(bufferedImage, null));
            ratioProperty.set(new Ratio(bufferedImage));
            LOG.info("Image {} successfully opened", file);
        } catch (IOException ex) {
            bufferedImage = null;
            imageProperty.set(null);
            ratioProperty.set(NONE);
            LOG.warn("File can't be open.", ex);
        }
    }

    public static Ratio NONE = new Ratio(1,1) {
        @Override
        public boolean equals(Object o) {
            return NONE == o;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "";
        }
    };

    public static class Ratio {

        public final int width;
        public final int height;

        public Ratio(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public Ratio(BufferedImage bufferedImage) {
            int imgWidth = bufferedImage.getWidth();
            int imgHeigth = bufferedImage.getHeight();
            int gds = BigInteger.valueOf(imgWidth).gcd(BigInteger.valueOf(imgHeigth)).intValue();
            width = imgWidth / gds;
            height = imgHeigth / gds;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Ratio ration = (Ratio) o;
            return width == ration.width &&
                    height == ration.height;
        }

        @Override
        public int hashCode() {
            return Objects.hash(width, height);
        }

        @Override
        public String toString() {
            return String.format("%d:%d", width, height);
        }
    }

}
