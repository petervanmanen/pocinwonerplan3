package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctiehuisAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFunctiehuisAllPropertiesEquals(Functiehuis expected, Functiehuis actual) {
        assertFunctiehuisAutoGeneratedPropertiesEquals(expected, actual);
        assertFunctiehuisAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFunctiehuisAllUpdatablePropertiesEquals(Functiehuis expected, Functiehuis actual) {
        assertFunctiehuisUpdatableFieldsEquals(expected, actual);
        assertFunctiehuisUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFunctiehuisAutoGeneratedPropertiesEquals(Functiehuis expected, Functiehuis actual) {
        assertThat(expected)
            .as("Verify Functiehuis auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFunctiehuisUpdatableFieldsEquals(Functiehuis expected, Functiehuis actual) {
        assertThat(expected)
            .as("Verify Functiehuis relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFunctiehuisUpdatableRelationshipsEquals(Functiehuis expected, Functiehuis actual) {}
}
