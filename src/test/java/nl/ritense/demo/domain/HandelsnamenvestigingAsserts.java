package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HandelsnamenvestigingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHandelsnamenvestigingAllPropertiesEquals(Handelsnamenvestiging expected, Handelsnamenvestiging actual) {
        assertHandelsnamenvestigingAutoGeneratedPropertiesEquals(expected, actual);
        assertHandelsnamenvestigingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHandelsnamenvestigingAllUpdatablePropertiesEquals(
        Handelsnamenvestiging expected,
        Handelsnamenvestiging actual
    ) {
        assertHandelsnamenvestigingUpdatableFieldsEquals(expected, actual);
        assertHandelsnamenvestigingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHandelsnamenvestigingAutoGeneratedPropertiesEquals(
        Handelsnamenvestiging expected,
        Handelsnamenvestiging actual
    ) {
        assertThat(expected)
            .as("Verify Handelsnamenvestiging auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHandelsnamenvestigingUpdatableFieldsEquals(Handelsnamenvestiging expected, Handelsnamenvestiging actual) {
        assertThat(expected)
            .as("Verify Handelsnamenvestiging relevant properties")
            .satisfies(e -> assertThat(e.getHandelsnaam()).as("check handelsnaam").isEqualTo(actual.getHandelsnaam()))
            .satisfies(e -> assertThat(e.getVerkortenaam()).as("check verkortenaam").isEqualTo(actual.getVerkortenaam()))
            .satisfies(e -> assertThat(e.getVolgorde()).as("check volgorde").isEqualTo(actual.getVolgorde()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHandelsnamenvestigingUpdatableRelationshipsEquals(
        Handelsnamenvestiging expected,
        Handelsnamenvestiging actual
    ) {}
}
