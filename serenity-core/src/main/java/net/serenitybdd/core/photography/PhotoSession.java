package net.serenitybdd.core.photography;

import net.thucydides.core.screenshots.BlurLevel;
import net.thucydides.core.webdriver.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static net.serenitybdd.core.photography.ScreenshotNegative.prepareNegativeIn;

public class PhotoSession {

    private final WebDriver driver;
    private final Path outputDirectory;
    private final Darkroom darkroom;
    private BlurLevel blurLevel;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public PhotoSession(WebDriver driver,  Darkroom darkroom, Path outputDirectory, BlurLevel blurLevel) {
        this.driver = driver;
        this.outputDirectory = outputDirectory;
        this.blurLevel = blurLevel;
        this.darkroom = darkroom;

        darkroom.isOpenForBusiness();
    }

    public ScreenshotPhoto takeScreenshot() {
        byte[] screenshotData = null;
        if(WebDriverFactory.isAlive(driver) && driver instanceof TakesScreenshot){
            try {
                screenshotData = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            } catch (Exception e) {
                LOGGER.warn("Failed to take screenshot", e);
                return ScreenshotPhoto.None;
            }
        }

        if (screenshotData == null || screenshotData.length == 0) {
            return ScreenshotPhoto.None;
        }

        return storedScreenshot(screenshotData);
    }

    private ScreenshotPhoto storedScreenshot(byte[] screenshotData) {
        try {
            Path screenshotPath = screenshotPathFor(screenshotData);
            ScreenshotReceipt screenshotReceipt = storeScreenshot(screenshotData, screenshotPath);
            LOGGER.debug("Screenshot scheduled to be saved to {}", screenshotPath);
            return ScreenshotPhoto.forScreenshotAt(screenshotReceipt.getDestinationPath());
        } catch (IOException e) {
            LOGGER.warn("Failed to save screenshot", e);
            return ScreenshotPhoto.None;
        }
    }

    private ScreenshotReceipt storeScreenshot(byte[] screenshotData, Path screenshotPath) throws IOException {
        Path screenshotsDirectory = DarkroomFileSystem.get().getPath("/var/screenshots");

        Files.createDirectories(screenshotsDirectory);

        ScreenshotNegative screenshotNegative = prepareNegativeIn(screenshotsDirectory)
                .withScreenshotData(screenshotData)
                .andBlurringOf(blurLevel)
                .andTargetPathOf(screenshotPath);

        return darkroom.sendNegative(screenshotNegative);
    }

    private Path screenshotPathFor(byte[] screenshotData) {
        String screenshotFilename = ScreenshotDigest.forScreenshotData(screenshotData);
        return outputDirectory.resolve(screenshotFilename);
    }
}
