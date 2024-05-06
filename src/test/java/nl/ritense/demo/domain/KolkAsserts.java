package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KolkAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKolkAllPropertiesEquals(Kolk expected, Kolk actual) {
        assertKolkAutoGeneratedPropertiesEquals(expected, actual);
        assertKolkAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKolkAllUpdatablePropertiesEquals(Kolk expected, Kolk actual) {
        assertKolkUpdatableFieldsEquals(expected, actual);
        assertKolkUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKolkAutoGeneratedPropertiesEquals(Kolk expected, Kolk actual) {
        assertThat(expected)
            .as("Verify Kolk auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKolkUpdatableFieldsEquals(Kolk expected, Kolk actual) {
        assertThat(expected)
            .as("Verify Kolk relevant properties")
            .satisfies(e -> assertThat(e.getBereikbaarheidkolk()).as("check bereikbaarheidkolk").isEqualTo(actual.getBereikbaarheidkolk()))
            .satisfies(e -> assertThat(e.getRisicogebied()).as("check risicogebied").isEqualTo(actual.getRisicogebied()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKolkUpdatableRelationshipsEquals(Kolk expected, Kolk actual) {}
}