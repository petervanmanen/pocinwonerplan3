package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BeperkingscoresoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoresoortAllPropertiesEquals(Beperkingscoresoort expected, Beperkingscoresoort actual) {
        assertBeperkingscoresoortAutoGeneratedPropertiesEquals(expected, actual);
        assertBeperkingscoresoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoresoortAllUpdatablePropertiesEquals(Beperkingscoresoort expected, Beperkingscoresoort actual) {
        assertBeperkingscoresoortUpdatableFieldsEquals(expected, actual);
        assertBeperkingscoresoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoresoortAutoGeneratedPropertiesEquals(Beperkingscoresoort expected, Beperkingscoresoort actual) {
        assertThat(expected)
            .as("Verify Beperkingscoresoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoresoortUpdatableFieldsEquals(Beperkingscoresoort expected, Beperkingscoresoort actual) {
        assertThat(expected)
            .as("Verify Beperkingscoresoort relevant properties")
            .satisfies(e -> assertThat(e.getVraag()).as("check vraag").isEqualTo(actual.getVraag()))
            .satisfies(e -> assertThat(e.getWet()).as("check wet").isEqualTo(actual.getWet()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoresoortUpdatableRelationshipsEquals(Beperkingscoresoort expected, Beperkingscoresoort actual) {}
}
