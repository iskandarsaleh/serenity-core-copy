package net.thucydides.core.requirements.model.cucumber;

import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;

import java.util.Optional;

public abstract class NamedScenario {
    public static NamedScenario forScenarioDefinition(Feature feature, ScenarioDefinition scenarioDefinition) {
        return new IdentifiedScenario(feature, scenarioDefinition);
    }

    public static NamedScenario withNoMatchingScenario() { return new UnknownScenario(); }


    /**
     * Return the Given..When..Then part of the scenario
     * @return
     */
    public abstract Optional<String> asGivenWhenThen(ScenarioDisplayOption displayOption);
    public abstract Optional<String> asGivenWhenThen();

    /**
     * Return the example table part of the scenario outline
     * @return
     */
    public abstract Optional<String> asExampleTable();

    /**
     * Return the example table part of the scenario outline
     * @return
     */
    public abstract Optional<String> asExampleTable(ScenarioDisplayOption displayOption);

}
