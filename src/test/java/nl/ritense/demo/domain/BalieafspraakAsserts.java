package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BalieafspraakAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieafspraakAllPropertiesEquals(Balieafspraak expected, Balieafspraak actual) {
        assertBalieafspraakAutoGeneratedPropertiesEquals(expected, actual);
        assertBalieafspraakAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieafspraakAllUpdatablePropertiesEquals(Balieafspraak expected, Balieafspraak actual) {
        assertBalieafspraakUpdatableFieldsEquals(expected, actual);
        assertBalieafspraakUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieafspraakAutoGeneratedPropertiesEquals(Balieafspraak expected, Balieafspraak actual) {
        assertThat(expected)
            .as("Verify Balieafspraak auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieafspraakUpdatableFieldsEquals(Balieafspraak expected, Balieafspraak actual) {
        assertThat(expected)
            .as("Verify Balieafspraak relevant properties")
            .satisfies(e -> assertThat(e.getEindtijdgepland()).as("check eindtijdgepland").isEqualTo(actual.getEindtijdgepland()))
            .satisfies(e -> assertThat(e.getNotitie()).as("check notitie").isEqualTo(actual.getNotitie()))
            .satisfies(e -> assertThat(e.getStarttijdgepland()).as("check starttijdgepland").isEqualTo(actual.getStarttijdgepland()))
            .satisfies(e -> assertThat(e.getTijdaangemaakt()).as("check tijdaangemaakt").isEqualTo(actual.getTijdaangemaakt()))
            .satisfies(e -> assertThat(e.getTijdsduurgepland()).as("check tijdsduurgepland").isEqualTo(actual.getTijdsduurgepland()))
            .satisfies(e -> assertThat(e.getToelichting()).as("check toelichting").isEqualTo(actual.getToelichting()))
            .satisfies(
                e ->
                    assertThat(e.getWachttijdnastartafspraak())
                        .as("check wachttijdnastartafspraak")
                        .isEqualTo(actual.getWachttijdnastartafspraak())
            )
            .satisfies(e -> assertThat(e.getWachttijdtotaal()).as("check wachttijdtotaal").isEqualTo(actual.getWachttijdtotaal()))
            .satisfies(
                e ->
                    assertThat(e.getWachttijdvoorstartafspraak())
                        .as("check wachttijdvoorstartafspraak")
                        .isEqualTo(actual.getWachttijdvoorstartafspraak())
            )
            .satisfies(
                e -> assertThat(e.getWerkelijketijdsduur()).as("check werkelijketijdsduur").isEqualTo(actual.getWerkelijketijdsduur())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieafspraakUpdatableRelationshipsEquals(Balieafspraak expected, Balieafspraak actual) {
        assertThat(expected)
            .as("Verify Balieafspraak relationships")
            .satisfies(
                e ->
                    assertThat(e.getMondtuitinKlantcontact())
                        .as("check mondtuitinKlantcontact")
                        .isEqualTo(actual.getMondtuitinKlantcontact())
            )
            .satisfies(
                e -> assertThat(e.getHeeftAfspraakstatus()).as("check heeftAfspraakstatus").isEqualTo(actual.getHeeftAfspraakstatus())
            )
            .satisfies(e -> assertThat(e.getMetMedewerker()).as("check metMedewerker").isEqualTo(actual.getMetMedewerker()))
            .satisfies(
                e -> assertThat(e.getHeeftbetrekkingopZaak()).as("check heeftbetrekkingopZaak").isEqualTo(actual.getHeeftbetrekkingopZaak())
            );
    }
}
