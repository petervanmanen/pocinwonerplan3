package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HoofdstukAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHoofdstukAllPropertiesEquals(Hoofdstuk expected, Hoofdstuk actual) {
        assertHoofdstukAutoGeneratedPropertiesEquals(expected, actual);
        assertHoofdstukAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHoofdstukAllUpdatablePropertiesEquals(Hoofdstuk expected, Hoofdstuk actual) {
        assertHoofdstukUpdatableFieldsEquals(expected, actual);
        assertHoofdstukUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHoofdstukAutoGeneratedPropertiesEquals(Hoofdstuk expected, Hoofdstuk actual) {
        assertThat(expected)
            .as("Verify Hoofdstuk auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHoofdstukUpdatableFieldsEquals(Hoofdstuk expected, Hoofdstuk actual) {
        assertThat(expected)
            .as("Verify Hoofdstuk relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getNummer()).as("check nummer").isEqualTo(actual.getNummer()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHoofdstukUpdatableRelationshipsEquals(Hoofdstuk expected, Hoofdstuk actual) {
        assertThat(expected)
            .as("Verify Hoofdstuk relationships")
            .satisfies(e -> assertThat(e.getBinnenPeriodes()).as("check binnenPeriodes").isEqualTo(actual.getBinnenPeriodes()));
    }
}