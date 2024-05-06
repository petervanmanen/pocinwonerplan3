package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ArtefactsoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactsoortAllPropertiesEquals(Artefactsoort expected, Artefactsoort actual) {
        assertArtefactsoortAutoGeneratedPropertiesEquals(expected, actual);
        assertArtefactsoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactsoortAllUpdatablePropertiesEquals(Artefactsoort expected, Artefactsoort actual) {
        assertArtefactsoortUpdatableFieldsEquals(expected, actual);
        assertArtefactsoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactsoortAutoGeneratedPropertiesEquals(Artefactsoort expected, Artefactsoort actual) {
        assertThat(expected)
            .as("Verify Artefactsoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactsoortUpdatableFieldsEquals(Artefactsoort expected, Artefactsoort actual) {
        assertThat(expected)
            .as("Verify Artefactsoort relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtefactsoortUpdatableRelationshipsEquals(Artefactsoort expected, Artefactsoort actual) {}
}
