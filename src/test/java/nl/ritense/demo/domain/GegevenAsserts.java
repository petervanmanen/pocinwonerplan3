package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class GegevenAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGegevenAllPropertiesEquals(Gegeven expected, Gegeven actual) {
        assertGegevenAutoGeneratedPropertiesEquals(expected, actual);
        assertGegevenAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGegevenAllUpdatablePropertiesEquals(Gegeven expected, Gegeven actual) {
        assertGegevenUpdatableFieldsEquals(expected, actual);
        assertGegevenUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGegevenAutoGeneratedPropertiesEquals(Gegeven expected, Gegeven actual) {
        assertThat(expected)
            .as("Verify Gegeven auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGegevenUpdatableFieldsEquals(Gegeven expected, Gegeven actual) {
        assertThat(expected)
            .as("Verify Gegeven relevant properties")
            .satisfies(e -> assertThat(e.getAlias()).as("check alias").isEqualTo(actual.getAlias()))
            .satisfies(e -> assertThat(e.getEaguid()).as("check eaguid").isEqualTo(actual.getEaguid()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getStereotype()).as("check stereotype").isEqualTo(actual.getStereotype()))
            .satisfies(e -> assertThat(e.getToelichting()).as("check toelichting").isEqualTo(actual.getToelichting()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGegevenUpdatableRelationshipsEquals(Gegeven expected, Gegeven actual) {
        assertThat(expected)
            .as("Verify Gegeven relationships")
            .satisfies(
                e ->
                    assertThat(e.getGeclassificeerdalsClassificaties())
                        .as("check geclassificeerdalsClassificaties")
                        .isEqualTo(actual.getGeclassificeerdalsClassificaties())
            )
            .satisfies(e -> assertThat(e.getBevatApplicatie()).as("check bevatApplicatie").isEqualTo(actual.getBevatApplicatie()));
    }
}
