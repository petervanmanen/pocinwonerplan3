package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BelangtypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBelangtypeAllPropertiesEquals(Belangtype expected, Belangtype actual) {
        assertBelangtypeAutoGeneratedPropertiesEquals(expected, actual);
        assertBelangtypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBelangtypeAllUpdatablePropertiesEquals(Belangtype expected, Belangtype actual) {
        assertBelangtypeUpdatableFieldsEquals(expected, actual);
        assertBelangtypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBelangtypeAutoGeneratedPropertiesEquals(Belangtype expected, Belangtype actual) {
        assertThat(expected)
            .as("Verify Belangtype auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBelangtypeUpdatableFieldsEquals(Belangtype expected, Belangtype actual) {
        assertThat(expected)
            .as("Verify Belangtype relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBelangtypeUpdatableRelationshipsEquals(Belangtype expected, Belangtype actual) {}
}
