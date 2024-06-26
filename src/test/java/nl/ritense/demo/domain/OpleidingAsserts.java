package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OpleidingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpleidingAllPropertiesEquals(Opleiding expected, Opleiding actual) {
        assertOpleidingAutoGeneratedPropertiesEquals(expected, actual);
        assertOpleidingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpleidingAllUpdatablePropertiesEquals(Opleiding expected, Opleiding actual) {
        assertOpleidingUpdatableFieldsEquals(expected, actual);
        assertOpleidingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpleidingAutoGeneratedPropertiesEquals(Opleiding expected, Opleiding actual) {
        assertThat(expected)
            .as("Verify Opleiding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpleidingUpdatableFieldsEquals(Opleiding expected, Opleiding actual) {
        assertThat(expected)
            .as("Verify Opleiding relevant properties")
            .satisfies(e -> assertThat(e.getInstituut()).as("check instituut").isEqualTo(actual.getInstituut()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getPrijs()).as("check prijs").isEqualTo(actual.getPrijs()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOpleidingUpdatableRelationshipsEquals(Opleiding expected, Opleiding actual) {
        assertThat(expected)
            .as("Verify Opleiding relationships")
            .satisfies(
                e ->
                    assertThat(e.getWordtgegevendoorOnderwijsinstituuts())
                        .as("check wordtgegevendoorOnderwijsinstituuts")
                        .isEqualTo(actual.getWordtgegevendoorOnderwijsinstituuts())
            );
    }
}
