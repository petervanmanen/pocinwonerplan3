package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PuntAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPuntAllPropertiesEquals(Punt expected, Punt actual) {
        assertPuntAutoGeneratedPropertiesEquals(expected, actual);
        assertPuntAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPuntAllUpdatablePropertiesEquals(Punt expected, Punt actual) {
        assertPuntUpdatableFieldsEquals(expected, actual);
        assertPuntUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPuntAutoGeneratedPropertiesEquals(Punt expected, Punt actual) {
        assertThat(expected)
            .as("Verify Punt auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPuntUpdatableFieldsEquals(Punt expected, Punt actual) {
        assertThat(expected)
            .as("Verify Punt relevant properties")
            .satisfies(e -> assertThat(e.getPunt()).as("check punt").isEqualTo(actual.getPunt()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPuntUpdatableRelationshipsEquals(Punt expected, Punt actual) {}
}
