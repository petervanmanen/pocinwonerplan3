package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BedrijfsprocesAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBedrijfsprocesAllPropertiesEquals(Bedrijfsproces expected, Bedrijfsproces actual) {
        assertBedrijfsprocesAutoGeneratedPropertiesEquals(expected, actual);
        assertBedrijfsprocesAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBedrijfsprocesAllUpdatablePropertiesEquals(Bedrijfsproces expected, Bedrijfsproces actual) {
        assertBedrijfsprocesUpdatableFieldsEquals(expected, actual);
        assertBedrijfsprocesUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBedrijfsprocesAutoGeneratedPropertiesEquals(Bedrijfsproces expected, Bedrijfsproces actual) {
        assertThat(expected)
            .as("Verify Bedrijfsproces auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBedrijfsprocesUpdatableFieldsEquals(Bedrijfsproces expected, Bedrijfsproces actual) {
        assertThat(expected)
            .as("Verify Bedrijfsproces relevant properties")
            .satisfies(e -> assertThat(e.getAfgerond()).as("check afgerond").isEqualTo(actual.getAfgerond()))
            .satisfies(e -> assertThat(e.getDatumeind()).as("check datumeind").isEqualTo(actual.getDatumeind()))
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBedrijfsprocesUpdatableRelationshipsEquals(Bedrijfsproces expected, Bedrijfsproces actual) {
        assertThat(expected)
            .as("Verify Bedrijfsproces relationships")
            .satisfies(
                e -> assertThat(e.getUitgevoerdbinnenZaaks()).as("check uitgevoerdbinnenZaaks").isEqualTo(actual.getUitgevoerdbinnenZaaks())
            );
    }
}
