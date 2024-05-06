package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferteaanvraagAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferteaanvraagAllPropertiesEquals(Offerteaanvraag expected, Offerteaanvraag actual) {
        assertOfferteaanvraagAutoGeneratedPropertiesEquals(expected, actual);
        assertOfferteaanvraagAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferteaanvraagAllUpdatablePropertiesEquals(Offerteaanvraag expected, Offerteaanvraag actual) {
        assertOfferteaanvraagUpdatableFieldsEquals(expected, actual);
        assertOfferteaanvraagUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferteaanvraagAutoGeneratedPropertiesEquals(Offerteaanvraag expected, Offerteaanvraag actual) {
        assertThat(expected)
            .as("Verify Offerteaanvraag auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferteaanvraagUpdatableFieldsEquals(Offerteaanvraag expected, Offerteaanvraag actual) {
        assertThat(expected)
            .as("Verify Offerteaanvraag relevant properties")
            .satisfies(e -> assertThat(e.getDatumaanvraag()).as("check datumaanvraag").isEqualTo(actual.getDatumaanvraag()))
            .satisfies(e -> assertThat(e.getDatumsluiting()).as("check datumsluiting").isEqualTo(actual.getDatumsluiting()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferteaanvraagUpdatableRelationshipsEquals(Offerteaanvraag expected, Offerteaanvraag actual) {
        assertThat(expected)
            .as("Verify Offerteaanvraag relationships")
            .satisfies(
                e -> assertThat(e.getBetreftAanbesteding()).as("check betreftAanbesteding").isEqualTo(actual.getBetreftAanbesteding())
            )
            .satisfies(
                e -> assertThat(e.getGerichtaanLeverancier()).as("check gerichtaanLeverancier").isEqualTo(actual.getGerichtaanLeverancier())
            );
    }
}