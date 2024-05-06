package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DigitaalbestandAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDigitaalbestandAllPropertiesEquals(Digitaalbestand expected, Digitaalbestand actual) {
        assertDigitaalbestandAutoGeneratedPropertiesEquals(expected, actual);
        assertDigitaalbestandAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDigitaalbestandAllUpdatablePropertiesEquals(Digitaalbestand expected, Digitaalbestand actual) {
        assertDigitaalbestandUpdatableFieldsEquals(expected, actual);
        assertDigitaalbestandUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDigitaalbestandAutoGeneratedPropertiesEquals(Digitaalbestand expected, Digitaalbestand actual) {
        assertThat(expected)
            .as("Verify Digitaalbestand auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDigitaalbestandUpdatableFieldsEquals(Digitaalbestand expected, Digitaalbestand actual) {
        assertThat(expected)
            .as("Verify Digitaalbestand relevant properties")
            .satisfies(e -> assertThat(e.getBlob()).as("check blob").isEqualTo(actual.getBlob()))
            .satisfies(e -> assertThat(e.getMimetype()).as("check mimetype").isEqualTo(actual.getMimetype()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDigitaalbestandUpdatableRelationshipsEquals(Digitaalbestand expected, Digitaalbestand actual) {}
}
