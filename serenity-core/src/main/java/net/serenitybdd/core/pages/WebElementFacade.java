package net.serenitybdd.core.pages;

import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.FindsByIosUIAutomation;
import net.serenitybdd.core.annotations.ImplementedBy;
import net.thucydides.core.webdriver.ConfigurableTimeouts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.Wait;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ImplementedBy(WebElementFacadeImpl.class)
public interface WebElementFacade extends WebElement, WrapsElement, Locatable, WebElementState, FindsByAccessibilityId, FindsByAndroidUIAutomator,
        FindsByIosUIAutomation, ConfigurableTimeouts {

    <T extends WebElementFacade> T then(String xpathOrCssSelector);

    <T extends WebElementFacade> T findBy(String xpathOrCssSelector);

    List<WebElementFacade> thenFindAll(
            String xpathOrCssSelector);

    <T extends WebElementFacade> T findBy(By selector);

    <T extends WebElementFacade> T find(By bySelector);

    <T extends WebElementFacade> T then(By bySelector);

    String getAttribute(String name);

    List<WebElementFacade> thenFindAll(By selector);

    long getImplicitTimeoutInMilliseconds();

    <T extends WebElementFacade> T withTimeoutOf(int timeout, TimeUnit unit);

    /**
     * Convenience method to chain method calls more fluently.
     */
    <T extends WebElementFacade> T and();

    /**
     * Convenience method to chain method calls more fluently.
     */
    <T extends WebElementFacade> T then();

    List<String> getSelectOptions();

    /**
     * Type a value into a field, making sure that the field is empty first.
     *
     * @param value
     */
    <T extends WebElementFacade> T type(String value);

    /**
     * Type a value into a field and then press Enter, making sure that the field is empty first.
     *
     * @param value
     */
    <T extends WebElementFacade> T typeAndEnter(String value);

    /**
     * Type a value into a field and then press TAB, making sure that the field is empty first.
     * This currently is not supported by all browsers, notably Firefox.
     *
     * @param value
     */
    <T extends WebElementFacade> T typeAndTab(String value);

    void setWindowFocus();

    <T extends WebElementFacade> T deselectAll();

    <T extends WebElementFacade> T deselectByVisibleText(String label);

    <T extends WebElementFacade> T deselectByValue(String value);

    <T extends WebElementFacade> T deselectByIndex(int indexValue);

    <T extends WebElementFacade> T selectByVisibleText(String label);

    <T extends WebElementFacade> T selectByValue(String value);

    <T extends WebElementFacade> T selectByIndex(int indexValue);

    <T extends WebElementFacade> T waitUntilVisible();

    <T extends WebElementFacade> T waitUntilPresent();

    Wait<WebDriver> waitForCondition();

    <T extends WebElementFacade> T waitUntilNotVisible();

    String getValue();

    String getText();

    <T extends WebElementFacade> T waitUntilEnabled();

    <T extends WebElementFacade> T waitUntilClickable();

    <T extends WebElementFacade> T waitUntilDisabled();

    /**
     * Wait for an element to be visible and enabled, and then click on it.
     */
    void click();

    void clear();

    String toString();

    boolean containsElements(By selector);

    boolean containsElements(String xpathOrCssSelector);

    void shouldContainElements(By selector);

    void shouldContainElements(String xpathOrCssSelector);

    boolean hasClass(String cssClassName);

}
