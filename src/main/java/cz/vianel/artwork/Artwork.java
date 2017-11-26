package cz.vianel.artwork;

import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.omg.CORBA.Bounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
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

    private DoubleProperty gridScale = new SimpleDoubleProperty(1);
    public double getGridScale() {
        return gridScale.get();
    }
    public DoubleProperty gridScaleProperty() {
        return gridScale;
    }
    public void setGridScale(double gridScale) {
        this.gridScale.set(gridScale);
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
            Ratio best = new Ratio(imgWidth / gds, imgHeigth / gds);

            // Pokud poměr vypočítány podle GDS je dobrý, použiji jej.
            if (best.width < 10 && best.height < 10) {
                this.width = best.width;
                this.height = best.height;
                return;
            }

            // Jinak hledám nejlepší poměr menší než 10
            double distance = Double.MAX_VALUE;
            for(int r = Math.min(imgWidth, imgHeigth); r > 0; r--){
                int w = imgWidth / r;
                int h = imgHeigth / r;
                double d = (((double)imgWidth / (double)r) - w) + (((double)imgHeigth / (double)r) - h);
                if (distance > d && w < 10 && h < 10) {
                    best = new Ratio(w,h);
                }
            }

            this.width = best.width;
            this.height = best.height;
        }

        public Ratio applyScale(double scale) {
            return new Ratio(Double.valueOf(width * scale).intValue(), Double.valueOf(height * scale).intValue());
        }

        public double squareSize(double fitWidth, double fitHeight) {
            return Math.min(fitWidth / width, fitHeight / height);
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

    public void saveTo(File file) {
        try {
            ImageIO.write(this.bufferedImage, "png", file);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void cropImage(int x, int y, int width, int height) {
        this.bufferedImage = this.bufferedImage.getSubimage(x, y, width, height);
        imageProperty.set(SwingFXUtils.toFXImage(bufferedImage, null));
        ratioProperty.set(new Ratio(bufferedImage));
    }
}
