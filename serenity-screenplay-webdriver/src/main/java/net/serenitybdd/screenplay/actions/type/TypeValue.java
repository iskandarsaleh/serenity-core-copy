package net.serenitybdd.screenplay.actions.type;

import net.serenitybdd.core.strings.Joiner;
import net.serenitybdd.core.collect.NewList;
import net.serenitybdd.core.collect.NewList;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.KeyNames;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.List;

public abstract class TypeValue implements Interaction {

    protected final String theText;
    protected final List<Keys> followedByKeys;
    private static final String ENTER_KEYS_INTRO_TEXT = " then hits ";

    public TypeValue(String theText) {
        this.theText = theText;
        this.followedByKeys = new ArrayList<>();
    }

    public TypeValue thenHit(Keys... keys) {
        this.followedByKeys.addAll(NewList.of(keys));
        return this;
    }

    public Keys[] getFollowedByKeys() {
        return followedByKeys.toArray(new Keys[]{});
    }

    private String getFollowedByKeysDescriptionFor(List<Keys> keys) {
        if (keys.isEmpty()) {
            return "";
        }
        if (keys.size() == 1) {
            return ENTER_KEYS_INTRO_TEXT + KeyNames.of(keys);
        }
        if (keys.size() == 2) {
            return ENTER_KEYS_INTRO_TEXT + Joiner.on(" and ").join(KeyNames.of(keys));
        }

        String allButLastTwo = Joiner.on(", ").join(KeyNames.allButLastTwo(keys));
        String lastTwoKeys = Joiner.on(" and ").join(KeyNames.lastTwoOf(keys));
        String allKeys = Joiner.on(", ").join(NewList.of(allButLastTwo, lastTwoKeys));

        return ENTER_KEYS_INTRO_TEXT + allKeys;
    }

}
