package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MeldingongevalAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingongevalAllPropertiesEquals(Meldingongeval expected, Meldingongeval actual) {
        assertMeldingongevalAutoGeneratedPropertiesEquals(expected, actual);
        assertMeldingongevalAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingongevalAllUpdatablePropertiesEquals(Meldingongeval expected, Meldingongeval actual) {
        assertMeldingongevalUpdatableFieldsEquals(expected, actual);
        assertMeldingongevalUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingongevalAutoGeneratedPropertiesEquals(Meldingongeval expected, Meldingongeval actual) {
        assertThat(expected)
            .as("Verify Meldingongeval auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingongevalUpdatableFieldsEquals(Meldingongeval expected, Meldingongeval actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingongevalUpdatableRelationshipsEquals(Meldingongeval expected, Meldingongeval actual) {}
}
