package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class WaarnemingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaarnemingAllPropertiesEquals(Waarneming expected, Waarneming actual) {
        assertWaarnemingAutoGeneratedPropertiesEquals(expected, actual);
        assertWaarnemingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaarnemingAllUpdatablePropertiesEquals(Waarneming expected, Waarneming actual) {
        assertWaarnemingUpdatableFieldsEquals(expected, actual);
        assertWaarnemingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaarnemingAutoGeneratedPropertiesEquals(Waarneming expected, Waarneming actual) {
        assertThat(expected)
            .as("Verify Waarneming auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaarnemingUpdatableFieldsEquals(Waarneming expected, Waarneming actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaarnemingUpdatableRelationshipsEquals(Waarneming expected, Waarneming actual) {}
}