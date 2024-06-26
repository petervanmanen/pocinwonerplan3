package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BevoegdgezagAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBevoegdgezagAllPropertiesEquals(Bevoegdgezag expected, Bevoegdgezag actual) {
        assertBevoegdgezagAutoGeneratedPropertiesEquals(expected, actual);
        assertBevoegdgezagAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBevoegdgezagAllUpdatablePropertiesEquals(Bevoegdgezag expected, Bevoegdgezag actual) {
        assertBevoegdgezagUpdatableFieldsEquals(expected, actual);
        assertBevoegdgezagUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBevoegdgezagAutoGeneratedPropertiesEquals(Bevoegdgezag expected, Bevoegdgezag actual) {
        assertThat(expected)
            .as("Verify Bevoegdgezag auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBevoegdgezagUpdatableFieldsEquals(Bevoegdgezag expected, Bevoegdgezag actual) {
        assertThat(expected)
            .as("Verify Bevoegdgezag relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBevoegdgezagUpdatableRelationshipsEquals(Bevoegdgezag expected, Bevoegdgezag actual) {}
}
