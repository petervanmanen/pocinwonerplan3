package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DossierAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDossierAllPropertiesEquals(Dossier expected, Dossier actual) {
        assertDossierAutoGeneratedPropertiesEquals(expected, actual);
        assertDossierAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDossierAllUpdatablePropertiesEquals(Dossier expected, Dossier actual) {
        assertDossierUpdatableFieldsEquals(expected, actual);
        assertDossierUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDossierAutoGeneratedPropertiesEquals(Dossier expected, Dossier actual) {
        assertThat(expected)
            .as("Verify Dossier auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDossierUpdatableFieldsEquals(Dossier expected, Dossier actual) {
        assertThat(expected)
            .as("Verify Dossier relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDossierUpdatableRelationshipsEquals(Dossier expected, Dossier actual) {
        assertThat(expected)
            .as("Verify Dossier relationships")
            .satisfies(e -> assertThat(e.getHoortbijRaadsstuks()).as("check hoortbijRaadsstuks").isEqualTo(actual.getHoortbijRaadsstuks()));
    }
}
