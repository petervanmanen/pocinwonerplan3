package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ProducttypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProducttypeAllPropertiesEquals(Producttype expected, Producttype actual) {
        assertProducttypeAutoGeneratedPropertiesEquals(expected, actual);
        assertProducttypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProducttypeAllUpdatablePropertiesEquals(Producttype expected, Producttype actual) {
        assertProducttypeUpdatableFieldsEquals(expected, actual);
        assertProducttypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProducttypeAutoGeneratedPropertiesEquals(Producttype expected, Producttype actual) {
        assertThat(expected)
            .as("Verify Producttype auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProducttypeUpdatableFieldsEquals(Producttype expected, Producttype actual) {
        assertThat(expected)
            .as("Verify Producttype relevant properties")
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProducttypeUpdatableRelationshipsEquals(Producttype expected, Producttype actual) {
        assertThat(expected)
            .as("Verify Producttype relationships")
            .satisfies(
                e ->
                    assertThat(e.getHeeftBedrijfsprocestype())
                        .as("check heeftBedrijfsprocestype")
                        .isEqualTo(actual.getHeeftBedrijfsprocestype())
            );
    }
}
