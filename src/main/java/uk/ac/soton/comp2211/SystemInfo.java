package uk.ac.soton.comp2211;

public class SystemInfo {

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

    public static void main(String[] args) {
        System.out.println("Java Version: " + javaVersion());
        System.out.println("JavaFX Version: " + javafxVersion());
    }

}