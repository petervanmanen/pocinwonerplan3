package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VorderingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingAllPropertiesEquals(Vordering expected, Vordering actual) {
        assertVorderingAutoGeneratedPropertiesEquals(expected, actual);
        assertVorderingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingAllUpdatablePropertiesEquals(Vordering expected, Vordering actual) {
        assertVorderingUpdatableFieldsEquals(expected, actual);
        assertVorderingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingAutoGeneratedPropertiesEquals(Vordering expected, Vordering actual) {
        assertThat(expected)
            .as("Verify Vordering auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingUpdatableFieldsEquals(Vordering expected, Vordering actual) {
        assertThat(expected)
            .as("Verify Vordering relevant properties")
            .satisfies(e -> assertThat(e.getAangemaaktdoor()).as("check aangemaaktdoor").isEqualTo(actual.getAangemaaktdoor()))
            .satisfies(e -> assertThat(e.getBedragbtw()).as("check bedragbtw").isEqualTo(actual.getBedragbtw()))
            .satisfies(e -> assertThat(e.getDatumaanmaak()).as("check datumaanmaak").isEqualTo(actual.getDatumaanmaak()))
            .satisfies(e -> assertThat(e.getDatummutatie()).as("check datummutatie").isEqualTo(actual.getDatummutatie()))
            .satisfies(e -> assertThat(e.getGeaccordeerd()).as("check geaccordeerd").isEqualTo(actual.getGeaccordeerd()))
            .satisfies(e -> assertThat(e.getGeaccordeerddoor()).as("check geaccordeerddoor").isEqualTo(actual.getGeaccordeerddoor()))
            .satisfies(e -> assertThat(e.getGeaccordeerdop()).as("check geaccordeerdop").isEqualTo(actual.getGeaccordeerdop()))
            .satisfies(e -> assertThat(e.getGeexporteerd()).as("check geexporteerd").isEqualTo(actual.getGeexporteerd()))
            .satisfies(e -> assertThat(e.getGemuteerddoor()).as("check gemuteerddoor").isEqualTo(actual.getGemuteerddoor()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getTotaalbedrag()).as("check totaalbedrag").isEqualTo(actual.getTotaalbedrag()))
            .satisfies(
                e -> assertThat(e.getTotaalbedraginclusief()).as("check totaalbedraginclusief").isEqualTo(actual.getTotaalbedraginclusief())
            )
            .satisfies(e -> assertThat(e.getVorderingnummer()).as("check vorderingnummer").isEqualTo(actual.getVorderingnummer()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingUpdatableRelationshipsEquals(Vordering expected, Vordering actual) {}
}
