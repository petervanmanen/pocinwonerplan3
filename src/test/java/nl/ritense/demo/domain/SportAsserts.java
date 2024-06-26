package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SportAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportAllPropertiesEquals(Sport expected, Sport actual) {
        assertSportAutoGeneratedPropertiesEquals(expected, actual);
        assertSportAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportAllUpdatablePropertiesEquals(Sport expected, Sport actual) {
        assertSportUpdatableFieldsEquals(expected, actual);
        assertSportUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportAutoGeneratedPropertiesEquals(Sport expected, Sport actual) {
        assertThat(expected)
            .as("Verify Sport auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportUpdatableFieldsEquals(Sport expected, Sport actual) {
        assertThat(expected)
            .as("Verify Sport relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportUpdatableRelationshipsEquals(Sport expected, Sport actual) {
        assertThat(expected)
            .as("Verify Sport relationships")
            .satisfies(
                e ->
                    assertThat(e.getOefentuitSportverenigings())
                        .as("check oefentuitSportverenigings")
                        .isEqualTo(actual.getOefentuitSportverenigings())
            );
    }
}
