package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class StrooirouteAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrooirouteAllPropertiesEquals(Strooiroute expected, Strooiroute actual) {
        assertStrooirouteAutoGeneratedPropertiesEquals(expected, actual);
        assertStrooirouteAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrooirouteAllUpdatablePropertiesEquals(Strooiroute expected, Strooiroute actual) {
        assertStrooirouteUpdatableFieldsEquals(expected, actual);
        assertStrooirouteUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrooirouteAutoGeneratedPropertiesEquals(Strooiroute expected, Strooiroute actual) {
        assertThat(expected)
            .as("Verify Strooiroute auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrooirouteUpdatableFieldsEquals(Strooiroute expected, Strooiroute actual) {
        assertThat(expected)
            .as("Verify Strooiroute relevant properties")
            .satisfies(e -> assertThat(e.getEroute()).as("check eroute").isEqualTo(actual.getEroute()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrooirouteUpdatableRelationshipsEquals(Strooiroute expected, Strooiroute actual) {}
}
