package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TrajectsoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectsoortAllPropertiesEquals(Trajectsoort expected, Trajectsoort actual) {
        assertTrajectsoortAutoGeneratedPropertiesEquals(expected, actual);
        assertTrajectsoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectsoortAllUpdatablePropertiesEquals(Trajectsoort expected, Trajectsoort actual) {
        assertTrajectsoortUpdatableFieldsEquals(expected, actual);
        assertTrajectsoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectsoortAutoGeneratedPropertiesEquals(Trajectsoort expected, Trajectsoort actual) {
        assertThat(expected)
            .as("Verify Trajectsoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectsoortUpdatableFieldsEquals(Trajectsoort expected, Trajectsoort actual) {
        assertThat(expected)
            .as("Verify Trajectsoort relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectsoortUpdatableRelationshipsEquals(Trajectsoort expected, Trajectsoort actual) {}
}
