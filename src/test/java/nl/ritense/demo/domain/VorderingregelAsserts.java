package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VorderingregelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingregelAllPropertiesEquals(Vorderingregel expected, Vorderingregel actual) {
        assertVorderingregelAutoGeneratedPropertiesEquals(expected, actual);
        assertVorderingregelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingregelAllUpdatablePropertiesEquals(Vorderingregel expected, Vorderingregel actual) {
        assertVorderingregelUpdatableFieldsEquals(expected, actual);
        assertVorderingregelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingregelAutoGeneratedPropertiesEquals(Vorderingregel expected, Vorderingregel actual) {
        assertThat(expected)
            .as("Verify Vorderingregel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingregelUpdatableFieldsEquals(Vorderingregel expected, Vorderingregel actual) {
        assertThat(expected)
            .as("Verify Vorderingregel relevant properties")
            .satisfies(e -> assertThat(e.getAangemaaktdoor()).as("check aangemaaktdoor").isEqualTo(actual.getAangemaaktdoor()))
            .satisfies(e -> assertThat(e.getAanmaakdatum()).as("check aanmaakdatum").isEqualTo(actual.getAanmaakdatum()))
            .satisfies(e -> assertThat(e.getBedragexclbtw()).as("check bedragexclbtw").isEqualTo(actual.getBedragexclbtw()))
            .satisfies(e -> assertThat(e.getBedraginclbtw()).as("check bedraginclbtw").isEqualTo(actual.getBedraginclbtw()))
            .satisfies(e -> assertThat(e.getBtwcategorie()).as("check btwcategorie").isEqualTo(actual.getBtwcategorie()))
            .satisfies(e -> assertThat(e.getGemuteerddoor()).as("check gemuteerddoor").isEqualTo(actual.getGemuteerddoor()))
            .satisfies(e -> assertThat(e.getMutatiedatum()).as("check mutatiedatum").isEqualTo(actual.getMutatiedatum()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getPeriodiek()).as("check periodiek").isEqualTo(actual.getPeriodiek()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVorderingregelUpdatableRelationshipsEquals(Vorderingregel expected, Vorderingregel actual) {}
}
