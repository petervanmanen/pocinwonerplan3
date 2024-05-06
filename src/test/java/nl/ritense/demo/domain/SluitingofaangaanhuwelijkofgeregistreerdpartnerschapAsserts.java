package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SluitingofaangaanhuwelijkofgeregistreerdpartnerschapAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapAllPropertiesEquals(
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap expected,
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap actual
    ) {
        assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapAutoGeneratedPropertiesEquals(expected, actual);
        assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapAllUpdatablePropertiesEquals(
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap expected,
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap actual
    ) {
        assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdatableFieldsEquals(expected, actual);
        assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapAutoGeneratedPropertiesEquals(
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap expected,
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap actual
    ) {
        assertThat(expected)
            .as("Verify Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdatableFieldsEquals(
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap expected,
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap actual
    ) {
        assertThat(expected)
            .as("Verify Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getBuitenlandseplaatsaanvang())
                        .as("check buitenlandseplaatsaanvang")
                        .isEqualTo(actual.getBuitenlandseplaatsaanvang())
            )
            .satisfies(
                e ->
                    assertThat(e.getBuitenlandseregioaanvang())
                        .as("check buitenlandseregioaanvang")
                        .isEqualTo(actual.getBuitenlandseregioaanvang())
            )
            .satisfies(e -> assertThat(e.getDatumaanvang()).as("check datumaanvang").isEqualTo(actual.getDatumaanvang()))
            .satisfies(e -> assertThat(e.getGemeenteaanvang()).as("check gemeenteaanvang").isEqualTo(actual.getGemeenteaanvang()))
            .satisfies(
                e -> assertThat(e.getLandofgebiedaanvang()).as("check landofgebiedaanvang").isEqualTo(actual.getLandofgebiedaanvang())
            )
            .satisfies(
                e ->
                    assertThat(e.getOmschrijvinglocatieaanvang())
                        .as("check omschrijvinglocatieaanvang")
                        .isEqualTo(actual.getOmschrijvinglocatieaanvang())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSluitingofaangaanhuwelijkofgeregistreerdpartnerschapUpdatableRelationshipsEquals(
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap expected,
        Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap actual
    ) {}
}
