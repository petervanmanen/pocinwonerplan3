package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ReserveringAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReserveringAllPropertiesEquals(Reservering expected, Reservering actual) {
        assertReserveringAutoGeneratedPropertiesEquals(expected, actual);
        assertReserveringAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReserveringAllUpdatablePropertiesEquals(Reservering expected, Reservering actual) {
        assertReserveringUpdatableFieldsEquals(expected, actual);
        assertReserveringUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReserveringAutoGeneratedPropertiesEquals(Reservering expected, Reservering actual) {
        assertThat(expected)
            .as("Verify Reservering auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReserveringUpdatableFieldsEquals(Reservering expected, Reservering actual) {
        assertThat(expected)
            .as("Verify Reservering relevant properties")
            .satisfies(e -> assertThat(e.getAantal()).as("check aantal").isEqualTo(actual.getAantal()))
            .satisfies(e -> assertThat(e.getBtw()).as("check btw").isEqualTo(actual.getBtw()))
            .satisfies(e -> assertThat(e.getTijdtot()).as("check tijdtot").isEqualTo(actual.getTijdtot()))
            .satisfies(e -> assertThat(e.getTijdvanaf()).as("check tijdvanaf").isEqualTo(actual.getTijdvanaf()))
            .satisfies(e -> assertThat(e.getTotaalprijs()).as("check totaalprijs").isEqualTo(actual.getTotaalprijs()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertReserveringUpdatableRelationshipsEquals(Reservering expected, Reservering actual) {
        assertThat(expected)
            .as("Verify Reservering relationships")
            .satisfies(e -> assertThat(e.getBetreftVoorziening()).as("check betreftVoorziening").isEqualTo(actual.getBetreftVoorziening()))
            .satisfies(e -> assertThat(e.getBetreftZaal()).as("check betreftZaal").isEqualTo(actual.getBetreftZaal()))
            .satisfies(e -> assertThat(e.getHeeftActiviteit()).as("check heeftActiviteit").isEqualTo(actual.getHeeftActiviteit()));
    }
}
