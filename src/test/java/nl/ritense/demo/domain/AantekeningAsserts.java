package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AantekeningAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAantekeningAllPropertiesEquals(Aantekening expected, Aantekening actual) {
        assertAantekeningAutoGeneratedPropertiesEquals(expected, actual);
        assertAantekeningAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAantekeningAllUpdatablePropertiesEquals(Aantekening expected, Aantekening actual) {
        assertAantekeningUpdatableFieldsEquals(expected, actual);
        assertAantekeningUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAantekeningAutoGeneratedPropertiesEquals(Aantekening expected, Aantekening actual) {
        assertThat(expected)
            .as("Verify Aantekening auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAantekeningUpdatableFieldsEquals(Aantekening expected, Aantekening actual) {
        assertThat(expected)
            .as("Verify Aantekening relevant properties")
            .satisfies(e -> assertThat(e.getAard()).as("check aard").isEqualTo(actual.getAard()))
            .satisfies(e -> assertThat(e.getBegrenzing()).as("check begrenzing").isEqualTo(actual.getBegrenzing()))
            .satisfies(
                e ->
                    assertThat(e.getBetreftgedeeltevanperceel())
                        .as("check betreftgedeeltevanperceel")
                        .isEqualTo(actual.getBetreftgedeeltevanperceel())
            )
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(e -> assertThat(e.getDatumeinderecht()).as("check datumeinderecht").isEqualTo(actual.getDatumeinderecht()))
            .satisfies(e -> assertThat(e.getIdentificatie()).as("check identificatie").isEqualTo(actual.getIdentificatie()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAantekeningUpdatableRelationshipsEquals(Aantekening expected, Aantekening actual) {
        assertThat(expected)
            .as("Verify Aantekening relationships")
            .satisfies(
                e -> assertThat(e.getEmptyTenaamstelling()).as("check emptyTenaamstelling").isEqualTo(actual.getEmptyTenaamstelling())
            );
    }
}
