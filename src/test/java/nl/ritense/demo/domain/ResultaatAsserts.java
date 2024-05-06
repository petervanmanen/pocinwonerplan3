package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultaatAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertResultaatAllPropertiesEquals(Resultaat expected, Resultaat actual) {
        assertResultaatAutoGeneratedPropertiesEquals(expected, actual);
        assertResultaatAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertResultaatAllUpdatablePropertiesEquals(Resultaat expected, Resultaat actual) {
        assertResultaatUpdatableFieldsEquals(expected, actual);
        assertResultaatUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertResultaatAutoGeneratedPropertiesEquals(Resultaat expected, Resultaat actual) {
        assertThat(expected)
            .as("Verify Resultaat auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertResultaatUpdatableFieldsEquals(Resultaat expected, Resultaat actual) {
        assertThat(expected)
            .as("Verify Resultaat relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertResultaatUpdatableRelationshipsEquals(Resultaat expected, Resultaat actual) {
        assertThat(expected)
            .as("Verify Resultaat relationships")
            .satisfies(
                e ->
                    assertThat(e.getSoortresultaatResultaatsoort())
                        .as("check soortresultaatResultaatsoort")
                        .isEqualTo(actual.getSoortresultaatResultaatsoort())
            );
    }
}
