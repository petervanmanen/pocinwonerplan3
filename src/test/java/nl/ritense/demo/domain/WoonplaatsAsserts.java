package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class WoonplaatsAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWoonplaatsAllPropertiesEquals(Woonplaats expected, Woonplaats actual) {
        assertWoonplaatsAutoGeneratedPropertiesEquals(expected, actual);
        assertWoonplaatsAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWoonplaatsAllUpdatablePropertiesEquals(Woonplaats expected, Woonplaats actual) {
        assertWoonplaatsUpdatableFieldsEquals(expected, actual);
        assertWoonplaatsUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWoonplaatsAutoGeneratedPropertiesEquals(Woonplaats expected, Woonplaats actual) {
        assertThat(expected)
            .as("Verify Woonplaats auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWoonplaatsUpdatableFieldsEquals(Woonplaats expected, Woonplaats actual) {
        assertThat(expected)
            .as("Verify Woonplaats relevant properties")
            .satisfies(
                e -> assertThat(e.getDatumbegingeldigheid()).as("check datumbegingeldigheid").isEqualTo(actual.getDatumbegingeldigheid())
            )
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(
                e -> assertThat(e.getDatumeindegeldigheid()).as("check datumeindegeldigheid").isEqualTo(actual.getDatumeindegeldigheid())
            )
            .satisfies(e -> assertThat(e.getDatumingang()).as("check datumingang").isEqualTo(actual.getDatumingang()))
            .satisfies(e -> assertThat(e.getGeconstateerd()).as("check geconstateerd").isEqualTo(actual.getGeconstateerd()))
            .satisfies(e -> assertThat(e.getGeometrie()).as("check geometrie").isEqualTo(actual.getGeometrie()))
            .satisfies(e -> assertThat(e.getIdentificatie()).as("check identificatie").isEqualTo(actual.getIdentificatie()))
            .satisfies(e -> assertThat(e.getInonderzoek()).as("check inonderzoek").isEqualTo(actual.getInonderzoek()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getVersie()).as("check versie").isEqualTo(actual.getVersie()))
            .satisfies(e -> assertThat(e.getWoonplaatsnaam()).as("check woonplaatsnaam").isEqualTo(actual.getWoonplaatsnaam()))
            .satisfies(e -> assertThat(e.getWoonplaatsnaamnen()).as("check woonplaatsnaamnen").isEqualTo(actual.getWoonplaatsnaamnen()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWoonplaatsUpdatableRelationshipsEquals(Woonplaats expected, Woonplaats actual) {}
}