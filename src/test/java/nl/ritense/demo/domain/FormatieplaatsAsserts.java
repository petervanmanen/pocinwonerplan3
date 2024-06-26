package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FormatieplaatsAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormatieplaatsAllPropertiesEquals(Formatieplaats expected, Formatieplaats actual) {
        assertFormatieplaatsAutoGeneratedPropertiesEquals(expected, actual);
        assertFormatieplaatsAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormatieplaatsAllUpdatablePropertiesEquals(Formatieplaats expected, Formatieplaats actual) {
        assertFormatieplaatsUpdatableFieldsEquals(expected, actual);
        assertFormatieplaatsUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormatieplaatsAutoGeneratedPropertiesEquals(Formatieplaats expected, Formatieplaats actual) {
        assertThat(expected)
            .as("Verify Formatieplaats auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormatieplaatsUpdatableFieldsEquals(Formatieplaats expected, Formatieplaats actual) {
        assertThat(expected)
            .as("Verify Formatieplaats relevant properties")
            .satisfies(e -> assertThat(e.getUrenperweek()).as("check urenperweek").isEqualTo(actual.getUrenperweek()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormatieplaatsUpdatableRelationshipsEquals(Formatieplaats expected, Formatieplaats actual) {}
}
