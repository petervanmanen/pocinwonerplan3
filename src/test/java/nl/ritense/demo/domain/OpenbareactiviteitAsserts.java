package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenbareactiviteitAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpenbareactiviteitAllPropertiesEquals(Openbareactiviteit expected, Openbareactiviteit actual) {
        assertOpenbareactiviteitAutoGeneratedPropertiesEquals(expected, actual);
        assertOpenbareactiviteitAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpenbareactiviteitAllUpdatablePropertiesEquals(Openbareactiviteit expected, Openbareactiviteit actual) {
        assertOpenbareactiviteitUpdatableFieldsEquals(expected, actual);
        assertOpenbareactiviteitUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpenbareactiviteitAutoGeneratedPropertiesEquals(Openbareactiviteit expected, Openbareactiviteit actual) {
        assertThat(expected)
            .as("Verify Openbareactiviteit auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpenbareactiviteitUpdatableFieldsEquals(Openbareactiviteit expected, Openbareactiviteit actual) {
        assertThat(expected)
            .as("Verify Openbareactiviteit relevant properties")
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()))
            .satisfies(e -> assertThat(e.getEvenmentnaam()).as("check evenmentnaam").isEqualTo(actual.getEvenmentnaam()))
            .satisfies(
                e -> assertThat(e.getLocatieomschrijving()).as("check locatieomschrijving").isEqualTo(actual.getLocatieomschrijving())
            )
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpenbareactiviteitUpdatableRelationshipsEquals(Openbareactiviteit expected, Openbareactiviteit actual) {}
}
