package util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class NodeAnimation {
    public static Timeline popLabel(Label label) {
        Timeline show = new Timeline(
                new KeyFrame(Duration.millis(1), new KeyValue(label.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(1000), new KeyValue(label.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(1300), new KeyValue(label.opacityProperty(), 0))
        );
        return show;
    }
}
