package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectsoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectsoortAllPropertiesEquals(Projectsoort expected, Projectsoort actual) {
        assertProjectsoortAutoGeneratedPropertiesEquals(expected, actual);
        assertProjectsoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectsoortAllUpdatablePropertiesEquals(Projectsoort expected, Projectsoort actual) {
        assertProjectsoortUpdatableFieldsEquals(expected, actual);
        assertProjectsoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectsoortAutoGeneratedPropertiesEquals(Projectsoort expected, Projectsoort actual) {
        assertThat(expected)
            .as("Verify Projectsoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectsoortUpdatableFieldsEquals(Projectsoort expected, Projectsoort actual) {
        assertThat(expected)
            .as("Verify Projectsoort relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProjectsoortUpdatableRelationshipsEquals(Projectsoort expected, Projectsoort actual) {}
}
