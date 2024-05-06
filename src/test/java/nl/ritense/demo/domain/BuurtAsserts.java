package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BuurtAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBuurtAllPropertiesEquals(Buurt expected, Buurt actual) {
        assertBuurtAutoGeneratedPropertiesEquals(expected, actual);
        assertBuurtAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBuurtAllUpdatablePropertiesEquals(Buurt expected, Buurt actual) {
        assertBuurtUpdatableFieldsEquals(expected, actual);
        assertBuurtUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBuurtAutoGeneratedPropertiesEquals(Buurt expected, Buurt actual) {
        assertThat(expected)
            .as("Verify Buurt auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBuurtUpdatableFieldsEquals(Buurt expected, Buurt actual) {
        assertThat(expected)
            .as("Verify Buurt relevant properties")
            .satisfies(e -> assertThat(e.getBuurtcode()).as("check buurtcode").isEqualTo(actual.getBuurtcode()))
            .satisfies(e -> assertThat(e.getBuurtgeometrie()).as("check buurtgeometrie").isEqualTo(actual.getBuurtgeometrie()))
            .satisfies(e -> assertThat(e.getBuurtnaam()).as("check buurtnaam").isEqualTo(actual.getBuurtnaam()))
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidbuurt())
                        .as("check datumbegingeldigheidbuurt")
                        .isEqualTo(actual.getDatumbegingeldigheidbuurt())
            )
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidbuurt())
                        .as("check datumeindegeldigheidbuurt")
                        .isEqualTo(actual.getDatumeindegeldigheidbuurt())
            )
            .satisfies(e -> assertThat(e.getDatumingang()).as("check datumingang").isEqualTo(actual.getDatumingang()))
            .satisfies(e -> assertThat(e.getGeconstateerd()).as("check geconstateerd").isEqualTo(actual.getGeconstateerd()))
            .satisfies(e -> assertThat(e.getIdentificatie()).as("check identificatie").isEqualTo(actual.getIdentificatie()))
            .satisfies(e -> assertThat(e.getInonderzoek()).as("check inonderzoek").isEqualTo(actual.getInonderzoek()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getVersie()).as("check versie").isEqualTo(actual.getVersie()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBuurtUpdatableRelationshipsEquals(Buurt expected, Buurt actual) {
        assertThat(expected)
            .as("Verify Buurt relationships")
            .satisfies(e -> assertThat(e.getLigtinAreaals()).as("check ligtinAreaals").isEqualTo(actual.getLigtinAreaals()));
    }
}
