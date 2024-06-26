package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BijzonderheidsoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBijzonderheidsoortAllPropertiesEquals(Bijzonderheidsoort expected, Bijzonderheidsoort actual) {
        assertBijzonderheidsoortAutoGeneratedPropertiesEquals(expected, actual);
        assertBijzonderheidsoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBijzonderheidsoortAllUpdatablePropertiesEquals(Bijzonderheidsoort expected, Bijzonderheidsoort actual) {
        assertBijzonderheidsoortUpdatableFieldsEquals(expected, actual);
        assertBijzonderheidsoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBijzonderheidsoortAutoGeneratedPropertiesEquals(Bijzonderheidsoort expected, Bijzonderheidsoort actual) {
        assertThat(expected)
            .as("Verify Bijzonderheidsoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBijzonderheidsoortUpdatableFieldsEquals(Bijzonderheidsoort expected, Bijzonderheidsoort actual) {
        assertThat(expected)
            .as("Verify Bijzonderheidsoort relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBijzonderheidsoortUpdatableRelationshipsEquals(Bijzonderheidsoort expected, Bijzonderheidsoort actual) {}
}
