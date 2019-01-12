package net.serenitybdd.screenplay.actions;

import net.serenitybdd.core.collect.NewList;
import net.serenitybdd.screenplay.Interaction;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.List;

public abstract class EnterValue implements Interaction {

    protected final String theText;
    protected final List<Keys> followedByKeys;

    public EnterValue(String theText) {
        this.theText = theText;
        this.followedByKeys = new ArrayList<>();
    }

    public EnterValue thenHit(Keys... keys) {
        this.followedByKeys.addAll(NewList.of(keys));
        return this;
    }

    public Keys[] getFollowedByKeys() {
        return followedByKeys.toArray(new Keys[]{});
    }


}
