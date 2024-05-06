package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ArchiefcategorieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArchiefcategorieAllPropertiesEquals(Archiefcategorie expected, Archiefcategorie actual) {
        assertArchiefcategorieAutoGeneratedPropertiesEquals(expected, actual);
        assertArchiefcategorieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArchiefcategorieAllUpdatablePropertiesEquals(Archiefcategorie expected, Archiefcategorie actual) {
        assertArchiefcategorieUpdatableFieldsEquals(expected, actual);
        assertArchiefcategorieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArchiefcategorieAutoGeneratedPropertiesEquals(Archiefcategorie expected, Archiefcategorie actual) {
        assertThat(expected)
            .as("Verify Archiefcategorie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArchiefcategorieUpdatableFieldsEquals(Archiefcategorie expected, Archiefcategorie actual) {
        assertThat(expected)
            .as("Verify Archiefcategorie relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getNummer()).as("check nummer").isEqualTo(actual.getNummer()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArchiefcategorieUpdatableRelationshipsEquals(Archiefcategorie expected, Archiefcategorie actual) {
        assertThat(expected)
            .as("Verify Archiefcategorie relationships")
            .satisfies(e -> assertThat(e.getValtbinnenArchiefs()).as("check valtbinnenArchiefs").isEqualTo(actual.getValtbinnenArchiefs()));
    }
}
