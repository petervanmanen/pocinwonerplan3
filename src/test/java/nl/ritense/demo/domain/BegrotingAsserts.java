package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BegrotingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBegrotingAllPropertiesEquals(Begroting expected, Begroting actual) {
        assertBegrotingAutoGeneratedPropertiesEquals(expected, actual);
        assertBegrotingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBegrotingAllUpdatablePropertiesEquals(Begroting expected, Begroting actual) {
        assertBegrotingUpdatableFieldsEquals(expected, actual);
        assertBegrotingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBegrotingAutoGeneratedPropertiesEquals(Begroting expected, Begroting actual) {
        assertThat(expected)
            .as("Verify Begroting auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBegrotingUpdatableFieldsEquals(Begroting expected, Begroting actual) {
        assertThat(expected)
            .as("Verify Begroting relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getNummer()).as("check nummer").isEqualTo(actual.getNummer()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBegrotingUpdatableRelationshipsEquals(Begroting expected, Begroting actual) {}
}
