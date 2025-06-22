package com.example.petralibrarymanager.graphical;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class BackgroundFader {

    public static void setupCrossfadeBackground(Pane container, List<Image> images, double durationSeconds) {
        if (images == null || images.size() < 2) return;

        // Create two stacked ImageViews
        ImageView imageView1 = new ImageView(images.get(0));
        ImageView imageView2 = new ImageView(images.get(1));

        imageView1.setOpacity(1);
        imageView2.setOpacity(0);

        // Fit to container size
        imageView1.fitWidthProperty().bind(container.widthProperty());
        imageView1.fitHeightProperty().bind(container.heightProperty());

        imageView2.fitWidthProperty().bind(container.widthProperty());
        imageView2.fitHeightProperty().bind(container.heightProperty());

        imageView1.setPreserveRatio(false);
        imageView2.setPreserveRatio(false);

//        container.getChildren().addAll(imageView1, imageView2);
        container.getChildren().add(0, imageView1);
        container.getChildren().add(1, imageView2);

        final int[] index = {1};

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(durationSeconds), e -> {
                    int nextIndex = (index[0] + 1) % images.size();
                    Image nextImage = images.get(nextIndex);

                    imageView2.setImage(nextImage);
                    imageView2.setOpacity(0);

                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), imageView1);
                    fadeOut.setToValue(0);

                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), imageView2);
                    fadeIn.setToValue(1);

                    fadeOut.setOnFinished(ev -> {
                        // Swap roles
                        imageView1.setImage(nextImage);
                        imageView1.setOpacity(1);
                        imageView2.setOpacity(0);
                    });

                    fadeOut.play();
                    fadeIn.play();

                    index[0] = nextIndex;
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
