package net.serenitybdd.screenplay.webtests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("classpath:sample-web-site/index.html")
public class HomePage extends PageObject {

    public final static String VIEW_PROFILE = ".view-profile";

    public void viewProfile() {
        $(VIEW_PROFILE).click();
    }
}
