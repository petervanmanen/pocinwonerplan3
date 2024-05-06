package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RitAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRitAllPropertiesEquals(Rit expected, Rit actual) {
        assertRitAutoGeneratedPropertiesEquals(expected, actual);
        assertRitAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRitAllUpdatablePropertiesEquals(Rit expected, Rit actual) {
        assertRitUpdatableFieldsEquals(expected, actual);
        assertRitUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRitAutoGeneratedPropertiesEquals(Rit expected, Rit actual) {
        assertThat(expected)
            .as("Verify Rit auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRitUpdatableFieldsEquals(Rit expected, Rit actual) {
        assertThat(expected)
            .as("Verify Rit relevant properties")
            .satisfies(e -> assertThat(e.getEindtijd()).as("check eindtijd").isEqualTo(actual.getEindtijd()))
            .satisfies(e -> assertThat(e.getRitcode()).as("check ritcode").isEqualTo(actual.getRitcode()))
            .satisfies(e -> assertThat(e.getStarttijd()).as("check starttijd").isEqualTo(actual.getStarttijd()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRitUpdatableRelationshipsEquals(Rit expected, Rit actual) {
        assertThat(expected)
            .as("Verify Rit relationships")
            .satisfies(
                e ->
                    assertThat(e.getUitgevoerdmetVuilniswagen())
                        .as("check uitgevoerdmetVuilniswagen")
                        .isEqualTo(actual.getUitgevoerdmetVuilniswagen())
            );
    }
}