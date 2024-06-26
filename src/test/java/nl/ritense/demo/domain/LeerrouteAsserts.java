package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LeerrouteAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeerrouteAllPropertiesEquals(Leerroute expected, Leerroute actual) {
        assertLeerrouteAutoGeneratedPropertiesEquals(expected, actual);
        assertLeerrouteAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeerrouteAllUpdatablePropertiesEquals(Leerroute expected, Leerroute actual) {
        assertLeerrouteUpdatableFieldsEquals(expected, actual);
        assertLeerrouteUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeerrouteAutoGeneratedPropertiesEquals(Leerroute expected, Leerroute actual) {
        assertThat(expected)
            .as("Verify Leerroute auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeerrouteUpdatableFieldsEquals(Leerroute expected, Leerroute actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeerrouteUpdatableRelationshipsEquals(Leerroute expected, Leerroute actual) {}
}
