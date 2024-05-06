package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OnderwijsloopbaanAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijsloopbaanAllPropertiesEquals(Onderwijsloopbaan expected, Onderwijsloopbaan actual) {
        assertOnderwijsloopbaanAutoGeneratedPropertiesEquals(expected, actual);
        assertOnderwijsloopbaanAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijsloopbaanAllUpdatablePropertiesEquals(Onderwijsloopbaan expected, Onderwijsloopbaan actual) {
        assertOnderwijsloopbaanUpdatableFieldsEquals(expected, actual);
        assertOnderwijsloopbaanUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijsloopbaanAutoGeneratedPropertiesEquals(Onderwijsloopbaan expected, Onderwijsloopbaan actual) {
        assertThat(expected)
            .as("Verify Onderwijsloopbaan auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijsloopbaanUpdatableFieldsEquals(Onderwijsloopbaan expected, Onderwijsloopbaan actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijsloopbaanUpdatableRelationshipsEquals(Onderwijsloopbaan expected, Onderwijsloopbaan actual) {
        assertThat(expected)
            .as("Verify Onderwijsloopbaan relationships")
            .satisfies(e -> assertThat(e.getHeeftLeerling()).as("check heeftLeerling").isEqualTo(actual.getHeeftLeerling()))
            .satisfies(e -> assertThat(e.getKentSchools()).as("check kentSchools").isEqualTo(actual.getKentSchools()));
    }
}