package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BesluitAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBesluitAllPropertiesEquals(Besluit expected, Besluit actual) {
        assertBesluitAutoGeneratedPropertiesEquals(expected, actual);
        assertBesluitAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBesluitAllUpdatablePropertiesEquals(Besluit expected, Besluit actual) {
        assertBesluitUpdatableFieldsEquals(expected, actual);
        assertBesluitUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBesluitAutoGeneratedPropertiesEquals(Besluit expected, Besluit actual) {
        assertThat(expected)
            .as("Verify Besluit auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBesluitUpdatableFieldsEquals(Besluit expected, Besluit actual) {
        assertThat(expected)
            .as("Verify Besluit relevant properties")
            .satisfies(e -> assertThat(e.getBesluit()).as("check besluit").isEqualTo(actual.getBesluit()))
            .satisfies(
                e -> assertThat(e.getBesluitidentificatie()).as("check besluitidentificatie").isEqualTo(actual.getBesluitidentificatie())
            )
            .satisfies(e -> assertThat(e.getBesluittoelichting()).as("check besluittoelichting").isEqualTo(actual.getBesluittoelichting()))
            .satisfies(e -> assertThat(e.getDatumbesluit()).as("check datumbesluit").isEqualTo(actual.getDatumbesluit()))
            .satisfies(e -> assertThat(e.getDatumpublicatie()).as("check datumpublicatie").isEqualTo(actual.getDatumpublicatie()))
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()))
            .satisfies(
                e ->
                    assertThat(e.getDatumuiterlijkereactie())
                        .as("check datumuiterlijkereactie")
                        .isEqualTo(actual.getDatumuiterlijkereactie())
            )
            .satisfies(e -> assertThat(e.getDatumverval()).as("check datumverval").isEqualTo(actual.getDatumverval()))
            .satisfies(e -> assertThat(e.getDatumverzending()).as("check datumverzending").isEqualTo(actual.getDatumverzending()))
            .satisfies(e -> assertThat(e.getRedenverval()).as("check redenverval").isEqualTo(actual.getRedenverval()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBesluitUpdatableRelationshipsEquals(Besluit expected, Besluit actual) {
        assertThat(expected)
            .as("Verify Besluit relationships")
            .satisfies(
                e ->
                    assertThat(e.getIsvastgelegdinDocument())
                        .as("check isvastgelegdinDocument")
                        .isEqualTo(actual.getIsvastgelegdinDocument())
            )
            .satisfies(e -> assertThat(e.getIsuitkomstvanZaak()).as("check isuitkomstvanZaak").isEqualTo(actual.getIsuitkomstvanZaak()))
            .satisfies(e -> assertThat(e.getIsvanBesluittype()).as("check isvanBesluittype").isEqualTo(actual.getIsvanBesluittype()))
            .satisfies(
                e ->
                    assertThat(e.getKanvastgelegdzijnalsDocuments())
                        .as("check kanvastgelegdzijnalsDocuments")
                        .isEqualTo(actual.getKanvastgelegdzijnalsDocuments())
            );
    }
}
