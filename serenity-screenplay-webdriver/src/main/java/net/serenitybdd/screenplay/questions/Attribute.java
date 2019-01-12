package net.serenitybdd.screenplay.questions;

import io.vavr.collection.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;

public class Attribute extends TargetedUIState<String> {

    private final String attributeName;

    public Attribute(Target target, Actor actor, String attributeName) {
        super(target,actor);
        this.attributeName = attributeName;
    }

    public static UIStateReaderWithNameBuilder<Attribute> of(Target target) {
        return new UIStateReaderWithNameBuilder<>(target, Attribute.class);
    }

    public String resolve() {
        return target.resolveFor(actor).getAttribute(attributeName);
    }

    public java.util.List<String> resolveAll() {
        List<WebElementFacade> resolvedElements = List.ofAll(target.resolveAllFor(actor));
        return resolvedElements.map(element -> element.getAttribute(attributeName)).asJava();
    }
}
