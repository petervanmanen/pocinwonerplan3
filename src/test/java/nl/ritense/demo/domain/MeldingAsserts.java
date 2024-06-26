package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MeldingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingAllPropertiesEquals(Melding expected, Melding actual) {
        assertMeldingAutoGeneratedPropertiesEquals(expected, actual);
        assertMeldingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingAllUpdatablePropertiesEquals(Melding expected, Melding actual) {
        assertMeldingUpdatableFieldsEquals(expected, actual);
        assertMeldingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingAutoGeneratedPropertiesEquals(Melding expected, Melding actual) {
        assertThat(expected)
            .as("Verify Melding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingUpdatableFieldsEquals(Melding expected, Melding actual) {
        assertThat(expected)
            .as("Verify Melding relevant properties")
            .satisfies(e -> assertThat(e.getVierentwintiguurs()).as("check vierentwintiguurs").isEqualTo(actual.getVierentwintiguurs()))
            .satisfies(e -> assertThat(e.getDatumtijd()).as("check datumtijd").isEqualTo(actual.getDatumtijd()))
            .satisfies(e -> assertThat(e.getIllegaal()).as("check illegaal").isEqualTo(actual.getIllegaal()))
            .satisfies(e -> assertThat(e.getMeldingnummer()).as("check meldingnummer").isEqualTo(actual.getMeldingnummer()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMeldingUpdatableRelationshipsEquals(Melding expected, Melding actual) {
        assertThat(expected)
            .as("Verify Melding relationships")
            .satisfies(
                e ->
                    assertThat(e.getHoofdcategorieCategorie())
                        .as("check hoofdcategorieCategorie")
                        .isEqualTo(actual.getHoofdcategorieCategorie())
            )
            .satisfies(
                e -> assertThat(e.getSubcategorieCategorie()).as("check subcategorieCategorie").isEqualTo(actual.getSubcategorieCategorie())
            )
            .satisfies(
                e -> assertThat(e.getBetreftContainertype()).as("check betreftContainertype").isEqualTo(actual.getBetreftContainertype())
            )
            .satisfies(e -> assertThat(e.getBetreftFractie()).as("check betreftFractie").isEqualTo(actual.getBetreftFractie()))
            .satisfies(e -> assertThat(e.getBetreftLocatie()).as("check betreftLocatie").isEqualTo(actual.getBetreftLocatie()))
            .satisfies(e -> assertThat(e.getMelderMedewerker()).as("check melderMedewerker").isEqualTo(actual.getMelderMedewerker()))
            .satisfies(
                e -> assertThat(e.getUitvoerderLeverancier()).as("check uitvoerderLeverancier").isEqualTo(actual.getUitvoerderLeverancier())
            )
            .satisfies(
                e -> assertThat(e.getUitvoerderMedewerker()).as("check uitvoerderMedewerker").isEqualTo(actual.getUitvoerderMedewerker())
            )
            .satisfies(
                e -> assertThat(e.getBetreftBeheerobjects()).as("check betreftBeheerobjects").isEqualTo(actual.getBetreftBeheerobjects())
            )
            .satisfies(e -> assertThat(e.getHeeftSchouwronde()).as("check heeftSchouwronde").isEqualTo(actual.getHeeftSchouwronde()));
    }
}
