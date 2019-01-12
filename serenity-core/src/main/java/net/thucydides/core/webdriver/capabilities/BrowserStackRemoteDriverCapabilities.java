package net.thucydides.core.webdriver.capabilities;

import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.NameConverter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Provides BrowserStack specific capabilities
 *
 * @author Imran Khan
 */

public class BrowserStackRemoteDriverCapabilities implements RemoteDriverCapabilities {

    private final EnvironmentVariables environmentVariables;

    public BrowserStackRemoteDriverCapabilities(EnvironmentVariables environmentVariables){
        this.environmentVariables = environmentVariables;
    }

    @Override
    public String getUrl() {
        return environmentVariables.injectSystemPropertiesInto(ThucydidesSystemProperty.BROWSERSTACK_URL.from(environmentVariables));
    }

    @Override
    public DesiredCapabilities getCapabilities(DesiredCapabilities capabilities) {
        configureBrowserStackCapabilities(capabilities);
        return capabilities;
    }

    private void configureBrowserStackCapabilities(DesiredCapabilities capabilities) {
        Optional<String> guessedTestName = RemoteTestName.fromCurrentTest();
        guessedTestName.ifPresent(
                name -> capabilities.setCapability("name", name)
        );

        AddCustomCapabilities.startingWith("browserstack.").from(environmentVariables).withAndWithoutPrefixes().to(capabilities);

        String remotePlatform = environmentVariables.getProperty("remote.platform");
        if (isNotEmpty(remotePlatform)) {
            capabilities.setPlatform(Platform.valueOf(remotePlatform));
        }
    }


}
