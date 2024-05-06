package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class IndelingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIndelingAllPropertiesEquals(Indeling expected, Indeling actual) {
        assertIndelingAutoGeneratedPropertiesEquals(expected, actual);
        assertIndelingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIndelingAllUpdatablePropertiesEquals(Indeling expected, Indeling actual) {
        assertIndelingUpdatableFieldsEquals(expected, actual);
        assertIndelingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIndelingAutoGeneratedPropertiesEquals(Indeling expected, Indeling actual) {
        assertThat(expected)
            .as("Verify Indeling auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIndelingUpdatableFieldsEquals(Indeling expected, Indeling actual) {
        assertThat(expected)
            .as("Verify Indeling relevant properties")
            .satisfies(e -> assertThat(e.getIndelingsoort()).as("check indelingsoort").isEqualTo(actual.getIndelingsoort()))
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
    public static void assertIndelingUpdatableRelationshipsEquals(Indeling expected, Indeling actual) {
        assertThat(expected)
            .as("Verify Indeling relationships")
            .satisfies(e -> assertThat(e.getHoortbijArchief()).as("check hoortbijArchief").isEqualTo(actual.getHoortbijArchief()))
            .satisfies(
                e -> assertThat(e.getValtbinnenIndeling2()).as("check valtbinnenIndeling2").isEqualTo(actual.getValtbinnenIndeling2())
            );
    }
}