package cz.vianel.artwork;

import cz.vianel.artwork.fx.ArtworkController;

public class MainArtwork {
    private static ArtworkController mainController;

    public static void setMainController(ArtworkController controller) {
        MainArtwork.mainController = controller;
    }

    public static void setArtwork(Artwork artwork) {
        MainArtwork.mainController.setArtwork(artwork);
    }
}
