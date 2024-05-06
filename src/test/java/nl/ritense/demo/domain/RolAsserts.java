package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RolAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRolAllPropertiesEquals(Rol expected, Rol actual) {
        assertRolAutoGeneratedPropertiesEquals(expected, actual);
        assertRolAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRolAllUpdatablePropertiesEquals(Rol expected, Rol actual) {
        assertRolUpdatableFieldsEquals(expected, actual);
        assertRolUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRolAutoGeneratedPropertiesEquals(Rol expected, Rol actual) {
        assertThat(expected)
            .as("Verify Rol auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRolUpdatableFieldsEquals(Rol expected, Rol actual) {
        assertThat(expected)
            .as("Verify Rol relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRolUpdatableRelationshipsEquals(Rol expected, Rol actual) {
        assertThat(expected)
            .as("Verify Rol relationships")
            .satisfies(e -> assertThat(e.getHeeftWerknemers()).as("check heeftWerknemers").isEqualTo(actual.getHeeftWerknemers()));
    }
}