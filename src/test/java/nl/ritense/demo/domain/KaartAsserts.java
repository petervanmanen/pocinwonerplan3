package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KaartAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKaartAllPropertiesEquals(Kaart expected, Kaart actual) {
        assertKaartAutoGeneratedPropertiesEquals(expected, actual);
        assertKaartAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKaartAllUpdatablePropertiesEquals(Kaart expected, Kaart actual) {
        assertKaartUpdatableFieldsEquals(expected, actual);
        assertKaartUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKaartAutoGeneratedPropertiesEquals(Kaart expected, Kaart actual) {
        assertThat(expected)
            .as("Verify Kaart auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKaartUpdatableFieldsEquals(Kaart expected, Kaart actual) {
        assertThat(expected)
            .as("Verify Kaart relevant properties")
            .satisfies(e -> assertThat(e.getKaart()).as("check kaart").isEqualTo(actual.getKaart()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKaartUpdatableRelationshipsEquals(Kaart expected, Kaart actual) {}
}
