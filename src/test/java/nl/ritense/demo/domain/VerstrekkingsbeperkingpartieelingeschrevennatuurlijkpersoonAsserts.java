package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonAllPropertiesEquals(
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon expected,
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon actual
    ) {
        assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonAutoGeneratedPropertiesEquals(expected, actual);
        assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonAllUpdatablePropertiesEquals(
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon expected,
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon actual
    ) {
        assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdatableFieldsEquals(expected, actual);
        assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonAutoGeneratedPropertiesEquals(
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon expected,
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon actual
    ) {
        assertThat(expected)
            .as("Verify Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdatableFieldsEquals(
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon expected,
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon actual
    ) {
        assertThat(expected)
            .as("Verify Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon relevant properties")
            .satisfies(
                e -> assertThat(e.getGemeenteverordening()).as("check gemeenteverordening").isEqualTo(actual.getGemeenteverordening())
            )
            .satisfies(e -> assertThat(e.getOmschrijvingderde()).as("check omschrijvingderde").isEqualTo(actual.getOmschrijvingderde()))
            .satisfies(e -> assertThat(e.getPartij()).as("check partij").isEqualTo(actual.getPartij()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdatableRelationshipsEquals(
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon expected,
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon actual
    ) {}
}
