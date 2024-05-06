package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PachterAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPachterAllPropertiesEquals(Pachter expected, Pachter actual) {
        assertPachterAutoGeneratedPropertiesEquals(expected, actual);
        assertPachterAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPachterAllUpdatablePropertiesEquals(Pachter expected, Pachter actual) {
        assertPachterUpdatableFieldsEquals(expected, actual);
        assertPachterUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPachterAutoGeneratedPropertiesEquals(Pachter expected, Pachter actual) {
        assertThat(expected)
            .as("Verify Pachter auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPachterUpdatableFieldsEquals(Pachter expected, Pachter actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPachterUpdatableRelationshipsEquals(Pachter expected, Pachter actual) {}
}