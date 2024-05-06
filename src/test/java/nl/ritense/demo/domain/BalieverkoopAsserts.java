package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BalieverkoopAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieverkoopAllPropertiesEquals(Balieverkoop expected, Balieverkoop actual) {
        assertBalieverkoopAutoGeneratedPropertiesEquals(expected, actual);
        assertBalieverkoopAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieverkoopAllUpdatablePropertiesEquals(Balieverkoop expected, Balieverkoop actual) {
        assertBalieverkoopUpdatableFieldsEquals(expected, actual);
        assertBalieverkoopUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieverkoopAutoGeneratedPropertiesEquals(Balieverkoop expected, Balieverkoop actual) {
        assertThat(expected)
            .as("Verify Balieverkoop auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieverkoopUpdatableFieldsEquals(Balieverkoop expected, Balieverkoop actual) {
        assertThat(expected)
            .as("Verify Balieverkoop relevant properties")
            .satisfies(e -> assertThat(e.getAantal()).as("check aantal").isEqualTo(actual.getAantal()))
            .satisfies(e -> assertThat(e.getKanaal()).as("check kanaal").isEqualTo(actual.getKanaal()))
            .satisfies(e -> assertThat(e.getVerkooptijd()).as("check verkooptijd").isEqualTo(actual.getVerkooptijd()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBalieverkoopUpdatableRelationshipsEquals(Balieverkoop expected, Balieverkoop actual) {
        assertThat(expected)
            .as("Verify Balieverkoop relationships")
            .satisfies(e -> assertThat(e.getTegenprijsPrijs()).as("check tegenprijsPrijs").isEqualTo(actual.getTegenprijsPrijs()))
            .satisfies(e -> assertThat(e.getBetreftProduct()).as("check betreftProduct").isEqualTo(actual.getBetreftProduct()));
    }
}