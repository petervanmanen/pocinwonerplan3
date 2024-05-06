package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AansluitputAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAansluitputAllPropertiesEquals(Aansluitput expected, Aansluitput actual) {
        assertAansluitputAutoGeneratedPropertiesEquals(expected, actual);
        assertAansluitputAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAansluitputAllUpdatablePropertiesEquals(Aansluitput expected, Aansluitput actual) {
        assertAansluitputUpdatableFieldsEquals(expected, actual);
        assertAansluitputUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAansluitputAutoGeneratedPropertiesEquals(Aansluitput expected, Aansluitput actual) {
        assertThat(expected)
            .as("Verify Aansluitput auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAansluitputUpdatableFieldsEquals(Aansluitput expected, Aansluitput actual) {
        assertThat(expected)
            .as("Verify Aansluitput relevant properties")
            .satisfies(e -> assertThat(e.getAansluitpunt()).as("check aansluitpunt").isEqualTo(actual.getAansluitpunt()))
            .satisfies(e -> assertThat(e.getRisicogebied()).as("check risicogebied").isEqualTo(actual.getRisicogebied()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAansluitputUpdatableRelationshipsEquals(Aansluitput expected, Aansluitput actual) {}
}
