package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KastAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKastAllPropertiesEquals(Kast expected, Kast actual) {
        assertKastAutoGeneratedPropertiesEquals(expected, actual);
        assertKastAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKastAllUpdatablePropertiesEquals(Kast expected, Kast actual) {
        assertKastUpdatableFieldsEquals(expected, actual);
        assertKastUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKastAutoGeneratedPropertiesEquals(Kast expected, Kast actual) {
        assertThat(expected)
            .as("Verify Kast auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKastUpdatableFieldsEquals(Kast expected, Kast actual) {
        assertThat(expected)
            .as("Verify Kast relevant properties")
            .satisfies(e -> assertThat(e.getKastnummer()).as("check kastnummer").isEqualTo(actual.getKastnummer()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKastUpdatableRelationshipsEquals(Kast expected, Kast actual) {
        assertThat(expected)
            .as("Verify Kast relationships")
            .satisfies(e -> assertThat(e.getHeeftStelling()).as("check heeftStelling").isEqualTo(actual.getHeeftStelling()));
    }
}
