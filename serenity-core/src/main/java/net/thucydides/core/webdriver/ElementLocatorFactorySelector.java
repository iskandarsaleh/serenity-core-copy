package net.thucydides.core.webdriver;

import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.annotations.locators.SmartElementLocatorFactory;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.appium.AppiumConfiguration;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class ElementLocatorFactorySelector {

    private final int timeoutInSeconds;
    private final EnvironmentVariables environmentVariables;
    private final AppiumConfiguration appiumConfiguration;

    public ElementLocatorFactorySelector(Configuration configuration) {
        this(configuration.getElementTimeout(), configuration.getEnvironmentVariables());
    }

    public ElementLocatorFactorySelector(int timeoutInSeconds, EnvironmentVariables environmentVariables) {
        this.timeoutInSeconds = timeoutInSeconds;
        this.environmentVariables = environmentVariables.copy();
        appiumConfiguration = AppiumConfiguration.from(Injectors.getInjector().getProvider(EnvironmentVariables.class).get());
    }

    public ElementLocatorFactory getLocatorFor(WebDriver driver) {
    	return getLocatorFor(driver, driver);
    }
    
    public ElementLocatorFactory getLocatorFor(SearchContext searchContext, WebDriver driver) {
        String locatorType = ThucydidesSystemProperty.THUCYDIDES_LOCATOR_FACTORY.from(environmentVariables,"SmartElementLocatorFactory");
        if (locatorType.equals("AjaxElementLocatorFactory")) {
            return new AjaxElementLocatorFactory(searchContext, timeoutInSeconds);
        } else if (locatorType.equals("DefaultElementLocatorFactory")) {
            return new DefaultElementLocatorFactory(searchContext);
        } else if (locatorType.equals("SmartElementLocatorFactory")){
        	return new SmartElementLocatorFactory(searchContext, platformFor(driver), timeoutInSeconds);
        } else {
            throw new IllegalArgumentException("Unsupported ElementLocatorFactory implementation: " + locatorType);
        }
    }
    
    private MobilePlatform platformFor(WebDriver driver) {
        if (!WebDriverType.isMobile(driver)) {
            return MobilePlatform.NONE;
        }
        return appiumConfiguration.getTargetPlatform();
    }

    public ElementLocatorFactorySelector withTimeout(int timeoutInSeconds) {
        return new ElementLocatorFactorySelector(timeoutInSeconds, environmentVariables);
    }
}
