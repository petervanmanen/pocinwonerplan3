package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RekeningnummerAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRekeningnummerAllPropertiesEquals(Rekeningnummer expected, Rekeningnummer actual) {
        assertRekeningnummerAutoGeneratedPropertiesEquals(expected, actual);
        assertRekeningnummerAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRekeningnummerAllUpdatablePropertiesEquals(Rekeningnummer expected, Rekeningnummer actual) {
        assertRekeningnummerUpdatableFieldsEquals(expected, actual);
        assertRekeningnummerUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRekeningnummerAutoGeneratedPropertiesEquals(Rekeningnummer expected, Rekeningnummer actual) {
        assertThat(expected)
            .as("Verify Rekeningnummer auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRekeningnummerUpdatableFieldsEquals(Rekeningnummer expected, Rekeningnummer actual) {
        assertThat(expected)
            .as("Verify Rekeningnummer relevant properties")
            .satisfies(e -> assertThat(e.getBic()).as("check bic").isEqualTo(actual.getBic()))
            .satisfies(e -> assertThat(e.getIban()).as("check iban").isEqualTo(actual.getIban()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRekeningnummerUpdatableRelationshipsEquals(Rekeningnummer expected, Rekeningnummer actual) {}
}