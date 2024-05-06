package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HandelsnamenmaatschappelijkeactiviteitAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHandelsnamenmaatschappelijkeactiviteitAllPropertiesEquals(
        Handelsnamenmaatschappelijkeactiviteit expected,
        Handelsnamenmaatschappelijkeactiviteit actual
    ) {
        assertHandelsnamenmaatschappelijkeactiviteitAutoGeneratedPropertiesEquals(expected, actual);
        assertHandelsnamenmaatschappelijkeactiviteitAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHandelsnamenmaatschappelijkeactiviteitAllUpdatablePropertiesEquals(
        Handelsnamenmaatschappelijkeactiviteit expected,
        Handelsnamenmaatschappelijkeactiviteit actual
    ) {
        assertHandelsnamenmaatschappelijkeactiviteitUpdatableFieldsEquals(expected, actual);
        assertHandelsnamenmaatschappelijkeactiviteitUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHandelsnamenmaatschappelijkeactiviteitAutoGeneratedPropertiesEquals(
        Handelsnamenmaatschappelijkeactiviteit expected,
        Handelsnamenmaatschappelijkeactiviteit actual
    ) {
        assertThat(expected)
            .as("Verify Handelsnamenmaatschappelijkeactiviteit auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHandelsnamenmaatschappelijkeactiviteitUpdatableFieldsEquals(
        Handelsnamenmaatschappelijkeactiviteit expected,
        Handelsnamenmaatschappelijkeactiviteit actual
    ) {
        assertThat(expected)
            .as("Verify Handelsnamenmaatschappelijkeactiviteit relevant properties")
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
    public static void assertHandelsnamenmaatschappelijkeactiviteitUpdatableRelationshipsEquals(
        Handelsnamenmaatschappelijkeactiviteit expected,
        Handelsnamenmaatschappelijkeactiviteit actual
    ) {}
}
