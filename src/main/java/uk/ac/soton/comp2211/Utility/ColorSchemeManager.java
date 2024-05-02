package uk.ac.soton.comp2211.utility;

import uk.ac.soton.comp2211.control.MainController;

public class ColorSchemeManager {

    private MainController mainController;

    public ColorSchemeManager(MainController mainController) {
        this.mainController = mainController;
    }

    public static String applyCss(String schemeName) {
        String cssFile = "/path/to/default_style.css"; // 默认CSS路径
        switch (schemeName) {
            case "Color Blind Friendly 1":
                cssFile = "/css/1.css";
                break;
            case "Color Blind Friendly 2":
                cssFile = "/css/2.css";
                break;
        }
        return cssFile;
    }
}
