package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VeldAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVeldAllPropertiesEquals(Veld expected, Veld actual) {
        assertVeldAutoGeneratedPropertiesEquals(expected, actual);
        assertVeldAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVeldAllUpdatablePropertiesEquals(Veld expected, Veld actual) {
        assertVeldUpdatableFieldsEquals(expected, actual);
        assertVeldUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVeldAutoGeneratedPropertiesEquals(Veld expected, Veld actual) {
        assertThat(expected)
            .as("Verify Veld auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVeldUpdatableFieldsEquals(Veld expected, Veld actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVeldUpdatableRelationshipsEquals(Veld expected, Veld actual) {
        assertThat(expected)
            .as("Verify Veld relationships")
            .satisfies(e -> assertThat(e.getHeeftBelijnings()).as("check heeftBelijnings").isEqualTo(actual.getHeeftBelijnings()))
            .satisfies(e -> assertThat(e.getHeeftSportpark()).as("check heeftSportpark").isEqualTo(actual.getHeeftSportpark()));
    }
}
