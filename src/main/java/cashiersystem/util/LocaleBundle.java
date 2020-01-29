package cashiersystem.util;

import java.util.ResourceBundle;

public class LocaleBundle {
    private static ResourceBundle resource = ResourceBundle.getBundle("view_en");

    public static ResourceBundle getResource() {
        return resource;
    }

    public static void switchLanguage(String language) {
        switch (language) {
            case "ru":
                resource = ResourceBundle.getBundle("view_ru", new UTF8Control());
                break;
            case "en":
                resource = ResourceBundle.getBundle("view_en");
                break;
            default:
                resource = ResourceBundle.getBundle("view");
        }
    }
}