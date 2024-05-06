package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ArtefactAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactAllPropertiesEquals(Artefact expected, Artefact actual) {
        assertArtefactAutoGeneratedPropertiesEquals(expected, actual);
        assertArtefactAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactAllUpdatablePropertiesEquals(Artefact expected, Artefact actual) {
        assertArtefactUpdatableFieldsEquals(expected, actual);
        assertArtefactUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactAutoGeneratedPropertiesEquals(Artefact expected, Artefact actual) {
        assertThat(expected)
            .as("Verify Artefact auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactUpdatableFieldsEquals(Artefact expected, Artefact actual) {
        assertThat(expected)
            .as("Verify Artefact relevant properties")
            .satisfies(e -> assertThat(e.getArtefectnummer()).as("check artefectnummer").isEqualTo(actual.getArtefectnummer()))
            .satisfies(e -> assertThat(e.getBeschrijving()).as("check beschrijving").isEqualTo(actual.getBeschrijving()))
            .satisfies(e -> assertThat(e.getConserveren()).as("check conserveren").isEqualTo(actual.getConserveren()))
            .satisfies(e -> assertThat(e.getDatering()).as("check datering").isEqualTo(actual.getDatering()))
            .satisfies(e -> assertThat(e.getDateringcomplex()).as("check dateringcomplex").isEqualTo(actual.getDateringcomplex()))
            .satisfies(e -> assertThat(e.getDeterminatieniveau()).as("check determinatieniveau").isEqualTo(actual.getDeterminatieniveau()))
            .satisfies(e -> assertThat(e.getDianummer()).as("check dianummer").isEqualTo(actual.getDianummer()))
            .satisfies(e -> assertThat(e.getDoosnummer()).as("check doosnummer").isEqualTo(actual.getDoosnummer()))
            .satisfies(e -> assertThat(e.getExposabel()).as("check exposabel").isEqualTo(actual.getExposabel()))
            .satisfies(e -> assertThat(e.getFotonummer()).as("check fotonummer").isEqualTo(actual.getFotonummer()))
            .satisfies(e -> assertThat(e.getFunctie()).as("check functie").isEqualTo(actual.getFunctie()))
            .satisfies(e -> assertThat(e.getHerkomst()).as("check herkomst").isEqualTo(actual.getHerkomst()))
            .satisfies(e -> assertThat(e.getKey()).as("check key").isEqualTo(actual.getKey()))
            .satisfies(e -> assertThat(e.getKeydoos()).as("check keydoos").isEqualTo(actual.getKeydoos()))
            .satisfies(
                e -> assertThat(e.getKeymagazijnplaatsing()).as("check keymagazijnplaatsing").isEqualTo(actual.getKeymagazijnplaatsing())
            )
            .satisfies(e -> assertThat(e.getKeyput()).as("check keyput").isEqualTo(actual.getKeyput()))
            .satisfies(e -> assertThat(e.getKeyvondst()).as("check keyvondst").isEqualTo(actual.getKeyvondst()))
            .satisfies(e -> assertThat(e.getLiteratuur()).as("check literatuur").isEqualTo(actual.getLiteratuur()))
            .satisfies(e -> assertThat(e.getMaten()).as("check maten").isEqualTo(actual.getMaten()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOpmerkingen()).as("check opmerkingen").isEqualTo(actual.getOpmerkingen()))
            .satisfies(e -> assertThat(e.getOrigine()).as("check origine").isEqualTo(actual.getOrigine()))
            .satisfies(e -> assertThat(e.getProjectcd()).as("check projectcd").isEqualTo(actual.getProjectcd()))
            .satisfies(e -> assertThat(e.getPutnummer()).as("check putnummer").isEqualTo(actual.getPutnummer()))
            .satisfies(
                e -> assertThat(e.getRestauratiewenselijk()).as("check restauratiewenselijk").isEqualTo(actual.getRestauratiewenselijk())
            )
            .satisfies(e -> assertThat(e.getTekeningnummer()).as("check tekeningnummer").isEqualTo(actual.getTekeningnummer()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()))
            .satisfies(e -> assertThat(e.getVondstnummer()).as("check vondstnummer").isEqualTo(actual.getVondstnummer()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactUpdatableRelationshipsEquals(Artefact expected, Artefact actual) {
        assertThat(expected)
            .as("Verify Artefact relationships")
            .satisfies(e -> assertThat(e.getZitinDoos()).as("check zitinDoos").isEqualTo(actual.getZitinDoos()))
            .satisfies(
                e ->
                    assertThat(e.getIsvansoortArtefactsoort())
                        .as("check isvansoortArtefactsoort")
                        .isEqualTo(actual.getIsvansoortArtefactsoort())
            )
            .satisfies(e -> assertThat(e.getBevatVondst()).as("check bevatVondst").isEqualTo(actual.getBevatVondst()));
    }
}