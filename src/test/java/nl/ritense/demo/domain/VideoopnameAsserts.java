package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VideoopnameAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVideoopnameAllPropertiesEquals(Videoopname expected, Videoopname actual) {
        assertVideoopnameAutoGeneratedPropertiesEquals(expected, actual);
        assertVideoopnameAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVideoopnameAllUpdatablePropertiesEquals(Videoopname expected, Videoopname actual) {
        assertVideoopnameUpdatableFieldsEquals(expected, actual);
        assertVideoopnameUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVideoopnameAutoGeneratedPropertiesEquals(Videoopname expected, Videoopname actual) {
        assertThat(expected)
            .as("Verify Videoopname auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVideoopnameUpdatableFieldsEquals(Videoopname expected, Videoopname actual) {
        assertThat(expected)
            .as("Verify Videoopname relevant properties")
            .satisfies(e -> assertThat(e.getBestandsgrootte()).as("check bestandsgrootte").isEqualTo(actual.getBestandsgrootte()))
            .satisfies(e -> assertThat(e.getDatumtijd()).as("check datumtijd").isEqualTo(actual.getDatumtijd()))
            .satisfies(e -> assertThat(e.getLengte()).as("check lengte").isEqualTo(actual.getLengte()))
            .satisfies(e -> assertThat(e.getVideoformaat()).as("check videoformaat").isEqualTo(actual.getVideoformaat()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVideoopnameUpdatableRelationshipsEquals(Videoopname expected, Videoopname actual) {}
}
