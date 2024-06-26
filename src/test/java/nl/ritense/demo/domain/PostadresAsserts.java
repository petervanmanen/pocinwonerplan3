package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PostadresAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostadresAllPropertiesEquals(Postadres expected, Postadres actual) {
        assertPostadresAutoGeneratedPropertiesEquals(expected, actual);
        assertPostadresAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostadresAllUpdatablePropertiesEquals(Postadres expected, Postadres actual) {
        assertPostadresUpdatableFieldsEquals(expected, actual);
        assertPostadresUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostadresAutoGeneratedPropertiesEquals(Postadres expected, Postadres actual) {
        assertThat(expected)
            .as("Verify Postadres auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostadresUpdatableFieldsEquals(Postadres expected, Postadres actual) {
        assertThat(expected)
            .as("Verify Postadres relevant properties")
            .satisfies(e -> assertThat(e.getPostadrestype()).as("check postadrestype").isEqualTo(actual.getPostadrestype()))
            .satisfies(
                e ->
                    assertThat(e.getPostbusofantwoordnummer())
                        .as("check postbusofantwoordnummer")
                        .isEqualTo(actual.getPostbusofantwoordnummer())
            )
            .satisfies(e -> assertThat(e.getPostcodepostadres()).as("check postcodepostadres").isEqualTo(actual.getPostcodepostadres()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostadresUpdatableRelationshipsEquals(Postadres expected, Postadres actual) {
        assertThat(expected)
            .as("Verify Postadres relationships")
            .satisfies(
                e ->
                    assertThat(e.getHeeftalscorrespondentieadrespostadresinWoonplaats())
                        .as("check heeftalscorrespondentieadrespostadresinWoonplaats")
                        .isEqualTo(actual.getHeeftalscorrespondentieadrespostadresinWoonplaats())
            );
    }
}
