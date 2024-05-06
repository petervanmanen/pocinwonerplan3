package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class EntreekaartAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntreekaartAllPropertiesEquals(Entreekaart expected, Entreekaart actual) {
        assertEntreekaartAutoGeneratedPropertiesEquals(expected, actual);
        assertEntreekaartAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntreekaartAllUpdatablePropertiesEquals(Entreekaart expected, Entreekaart actual) {
        assertEntreekaartUpdatableFieldsEquals(expected, actual);
        assertEntreekaartUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntreekaartAutoGeneratedPropertiesEquals(Entreekaart expected, Entreekaart actual) {
        assertThat(expected)
            .as("Verify Entreekaart auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntreekaartUpdatableFieldsEquals(Entreekaart expected, Entreekaart actual) {
        assertThat(expected)
            .as("Verify Entreekaart relevant properties")
            .satisfies(e -> assertThat(e.getRondleiding()).as("check rondleiding").isEqualTo(actual.getRondleiding()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEntreekaartUpdatableRelationshipsEquals(Entreekaart expected, Entreekaart actual) {}
}
