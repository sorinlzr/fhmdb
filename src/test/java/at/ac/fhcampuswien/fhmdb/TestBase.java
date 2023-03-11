package at.ac.fhcampuswien.fhmdb;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    private static boolean isJavaFxInitialized = false;

    @BeforeAll
    public static void setUpJavaFX() {
        if (!isJavaFxInitialized) {
            isJavaFxInitialized = true;
            Platform.startup(() -> {});
        }
    }
}
