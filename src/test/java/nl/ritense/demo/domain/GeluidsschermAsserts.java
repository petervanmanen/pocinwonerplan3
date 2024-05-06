package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class GeluidsschermAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGeluidsschermAllPropertiesEquals(Geluidsscherm expected, Geluidsscherm actual) {
        assertGeluidsschermAutoGeneratedPropertiesEquals(expected, actual);
        assertGeluidsschermAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGeluidsschermAllUpdatablePropertiesEquals(Geluidsscherm expected, Geluidsscherm actual) {
        assertGeluidsschermUpdatableFieldsEquals(expected, actual);
        assertGeluidsschermUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGeluidsschermAutoGeneratedPropertiesEquals(Geluidsscherm expected, Geluidsscherm actual) {
        assertThat(expected)
            .as("Verify Geluidsscherm auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGeluidsschermUpdatableFieldsEquals(Geluidsscherm expected, Geluidsscherm actual) {
        assertThat(expected)
            .as("Verify Geluidsscherm relevant properties")
            .satisfies(e -> assertThat(e.getAantaldeuren()).as("check aantaldeuren").isEqualTo(actual.getAantaldeuren()))
            .satisfies(e -> assertThat(e.getAantalpanelen()).as("check aantalpanelen").isEqualTo(actual.getAantalpanelen()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGeluidsschermUpdatableRelationshipsEquals(Geluidsscherm expected, Geluidsscherm actual) {}
}