package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KlantcontactAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantcontactAllPropertiesEquals(Klantcontact expected, Klantcontact actual) {
        assertKlantcontactAutoGeneratedPropertiesEquals(expected, actual);
        assertKlantcontactAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantcontactAllUpdatablePropertiesEquals(Klantcontact expected, Klantcontact actual) {
        assertKlantcontactUpdatableFieldsEquals(expected, actual);
        assertKlantcontactUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantcontactAutoGeneratedPropertiesEquals(Klantcontact expected, Klantcontact actual) {
        assertThat(expected)
            .as("Verify Klantcontact auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantcontactUpdatableFieldsEquals(Klantcontact expected, Klantcontact actual) {
        assertThat(expected)
            .as("Verify Klantcontact relevant properties")
            .satisfies(e -> assertThat(e.getEindtijd()).as("check eindtijd").isEqualTo(actual.getEindtijd()))
            .satisfies(e -> assertThat(e.getKanaal()).as("check kanaal").isEqualTo(actual.getKanaal()))
            .satisfies(e -> assertThat(e.getNotitie()).as("check notitie").isEqualTo(actual.getNotitie()))
            .satisfies(e -> assertThat(e.getStarttijd()).as("check starttijd").isEqualTo(actual.getStarttijd()))
            .satisfies(e -> assertThat(e.getTijdsduur()).as("check tijdsduur").isEqualTo(actual.getTijdsduur()))
            .satisfies(e -> assertThat(e.getToelichting()).as("check toelichting").isEqualTo(actual.getToelichting()))
            .satisfies(e -> assertThat(e.getWachttijdtotaal()).as("check wachttijdtotaal").isEqualTo(actual.getWachttijdtotaal()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantcontactUpdatableRelationshipsEquals(Klantcontact expected, Klantcontact actual) {
        assertThat(expected)
            .as("Verify Klantcontact relationships")
            .satisfies(
                e ->
                    assertThat(e.getHeeftklantcontactenBetrokkene())
                        .as("check heeftklantcontactenBetrokkene")
                        .isEqualTo(actual.getHeeftklantcontactenBetrokkene())
            )
            .satisfies(
                e -> assertThat(e.getHeeftbetrekkingopZaak()).as("check heeftbetrekkingopZaak").isEqualTo(actual.getHeeftbetrekkingopZaak())
            )
            .satisfies(
                e ->
                    assertThat(e.getIsgevoerddoorMedewerker())
                        .as("check isgevoerddoorMedewerker")
                        .isEqualTo(actual.getIsgevoerddoorMedewerker())
            )
            .satisfies(
                e ->
                    assertThat(e.getHeeftTelefoononderwerp())
                        .as("check heeftTelefoononderwerp")
                        .isEqualTo(actual.getHeeftTelefoononderwerp())
            )
            .satisfies(
                e -> assertThat(e.getMondtuitinTelefoontje()).as("check mondtuitinTelefoontje").isEqualTo(actual.getMondtuitinTelefoontje())
            );
    }
}
