package uk.ac.soton.comp2211.Utility;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
public class ColorBlind {
    private static final Background DEFAULT_COLOR_SCHEME = new Background(new BackgroundFill(Color.web("#b3d9ff"), null, null));
    private static final Background COLOR_BLIND_FRIENDLY_1 = new Background(new BackgroundFill(Color.web("#ff00ff"), null, null));
    private static final Background COLOR_BLIND_FRIENDLY_2 = new Background(new BackgroundFill(Color.web("#ffff00"), null, null));

    private void updateBackgroundColor(Parent parent, Background colorScheme) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof Parent) {
                updateBackgroundColor((Parent) node, colorScheme);
            } else if (node instanceof Region) {
                ((Region) node).setBackground(new Background(new BackgroundFill(colorScheme.getFills().get(0).getFill(), null, null)));
            } else if (node instanceof Control) {
                ((Control) node).setBackground(new Background(new BackgroundFill(colorScheme.getFills().get(0).getFill(), null, null)));
            } else {
                System.out.println("Unsupported node type: " + node.getClass().getName());
            }
        }
    }
}
