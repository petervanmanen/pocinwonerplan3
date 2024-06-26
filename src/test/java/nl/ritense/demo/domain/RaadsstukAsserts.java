package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RaadsstukAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadsstukAllPropertiesEquals(Raadsstuk expected, Raadsstuk actual) {
        assertRaadsstukAutoGeneratedPropertiesEquals(expected, actual);
        assertRaadsstukAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadsstukAllUpdatablePropertiesEquals(Raadsstuk expected, Raadsstuk actual) {
        assertRaadsstukUpdatableFieldsEquals(expected, actual);
        assertRaadsstukUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadsstukAutoGeneratedPropertiesEquals(Raadsstuk expected, Raadsstuk actual) {
        assertThat(expected)
            .as("Verify Raadsstuk auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadsstukUpdatableFieldsEquals(Raadsstuk expected, Raadsstuk actual) {
        assertThat(expected)
            .as("Verify Raadsstuk relevant properties")
            .satisfies(e -> assertThat(e.getBesloten()).as("check besloten").isEqualTo(actual.getBesloten()))
            .satisfies(e -> assertThat(e.getDatumexpiratie()).as("check datumexpiratie").isEqualTo(actual.getDatumexpiratie()))
            .satisfies(e -> assertThat(e.getDatumpublicatie()).as("check datumpublicatie").isEqualTo(actual.getDatumpublicatie()))
            .satisfies(e -> assertThat(e.getDatumregistratie()).as("check datumregistratie").isEqualTo(actual.getDatumregistratie()))
            .satisfies(e -> assertThat(e.getTyperaadsstuk()).as("check typeraadsstuk").isEqualTo(actual.getTyperaadsstuk()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadsstukUpdatableRelationshipsEquals(Raadsstuk expected, Raadsstuk actual) {
        assertThat(expected)
            .as("Verify Raadsstuk relationships")
            .satisfies(e -> assertThat(e.getHeeftTaakveld()).as("check heeftTaakveld").isEqualTo(actual.getHeeftTaakveld()))
            .satisfies(
                e -> assertThat(e.getBehandeltAgendapunts()).as("check behandeltAgendapunts").isEqualTo(actual.getBehandeltAgendapunts())
            )
            .satisfies(e -> assertThat(e.getHoortbijProgrammas()).as("check hoortbijProgrammas").isEqualTo(actual.getHoortbijProgrammas()))
            .satisfies(
                e ->
                    assertThat(e.getWordtbehandeldinVergaderings())
                        .as("check wordtbehandeldinVergaderings")
                        .isEqualTo(actual.getWordtbehandeldinVergaderings())
            )
            .satisfies(e -> assertThat(e.getHeeftCategorie()).as("check heeftCategorie").isEqualTo(actual.getHeeftCategorie()))
            .satisfies(e -> assertThat(e.getHoortbijDossiers()).as("check hoortbijDossiers").isEqualTo(actual.getHoortbijDossiers()))
            .satisfies(e -> assertThat(e.getHeeftIndieners()).as("check heeftIndieners").isEqualTo(actual.getHeeftIndieners()));
    }
}
