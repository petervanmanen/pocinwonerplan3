package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BeperkingscategorieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscategorieAllPropertiesEquals(Beperkingscategorie expected, Beperkingscategorie actual) {
        assertBeperkingscategorieAutoGeneratedPropertiesEquals(expected, actual);
        assertBeperkingscategorieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscategorieAllUpdatablePropertiesEquals(Beperkingscategorie expected, Beperkingscategorie actual) {
        assertBeperkingscategorieUpdatableFieldsEquals(expected, actual);
        assertBeperkingscategorieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscategorieAutoGeneratedPropertiesEquals(Beperkingscategorie expected, Beperkingscategorie actual) {
        assertThat(expected)
            .as("Verify Beperkingscategorie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscategorieUpdatableFieldsEquals(Beperkingscategorie expected, Beperkingscategorie actual) {
        assertThat(expected)
            .as("Verify Beperkingscategorie relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getWet()).as("check wet").isEqualTo(actual.getWet()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscategorieUpdatableRelationshipsEquals(Beperkingscategorie expected, Beperkingscategorie actual) {}
}
