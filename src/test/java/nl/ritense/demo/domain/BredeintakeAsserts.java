package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BredeintakeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBredeintakeAllPropertiesEquals(Bredeintake expected, Bredeintake actual) {
        assertBredeintakeAutoGeneratedPropertiesEquals(expected, actual);
        assertBredeintakeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBredeintakeAllUpdatablePropertiesEquals(Bredeintake expected, Bredeintake actual) {
        assertBredeintakeUpdatableFieldsEquals(expected, actual);
        assertBredeintakeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBredeintakeAutoGeneratedPropertiesEquals(Bredeintake expected, Bredeintake actual) {
        assertThat(expected)
            .as("Verify Bredeintake auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBredeintakeUpdatableFieldsEquals(Bredeintake expected, Bredeintake actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBredeintakeUpdatableRelationshipsEquals(Bredeintake expected, Bredeintake actual) {}
}