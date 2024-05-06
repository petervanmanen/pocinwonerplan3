package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoresoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScoresoortAllPropertiesEquals(Scoresoort expected, Scoresoort actual) {
        assertScoresoortAutoGeneratedPropertiesEquals(expected, actual);
        assertScoresoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScoresoortAllUpdatablePropertiesEquals(Scoresoort expected, Scoresoort actual) {
        assertScoresoortUpdatableFieldsEquals(expected, actual);
        assertScoresoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScoresoortAutoGeneratedPropertiesEquals(Scoresoort expected, Scoresoort actual) {
        assertThat(expected)
            .as("Verify Scoresoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScoresoortUpdatableFieldsEquals(Scoresoort expected, Scoresoort actual) {
        assertThat(expected)
            .as("Verify Scoresoort relevant properties")
            .satisfies(e -> assertThat(e.getNiveau()).as("check niveau").isEqualTo(actual.getNiveau()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertScoresoortUpdatableRelationshipsEquals(Scoresoort expected, Scoresoort actual) {}
}