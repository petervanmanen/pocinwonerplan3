package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RapportagemomentAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRapportagemomentAllPropertiesEquals(Rapportagemoment expected, Rapportagemoment actual) {
        assertRapportagemomentAutoGeneratedPropertiesEquals(expected, actual);
        assertRapportagemomentAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRapportagemomentAllUpdatablePropertiesEquals(Rapportagemoment expected, Rapportagemoment actual) {
        assertRapportagemomentUpdatableFieldsEquals(expected, actual);
        assertRapportagemomentUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRapportagemomentAutoGeneratedPropertiesEquals(Rapportagemoment expected, Rapportagemoment actual) {
        assertThat(expected)
            .as("Verify Rapportagemoment auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRapportagemomentUpdatableFieldsEquals(Rapportagemoment expected, Rapportagemoment actual) {
        assertThat(expected)
            .as("Verify Rapportagemoment relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getTermijn()).as("check termijn").isEqualTo(actual.getTermijn()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRapportagemomentUpdatableRelationshipsEquals(Rapportagemoment expected, Rapportagemoment actual) {
        assertThat(expected)
            .as("Verify Rapportagemoment relationships")
            .satisfies(e -> assertThat(e.getHeeftSubsidie()).as("check heeftSubsidie").isEqualTo(actual.getHeeftSubsidie()))
            .satisfies(
                e ->
                    assertThat(e.getProjectleiderRechtspersoon())
                        .as("check projectleiderRechtspersoon")
                        .isEqualTo(actual.getProjectleiderRechtspersoon())
            );
    }
}