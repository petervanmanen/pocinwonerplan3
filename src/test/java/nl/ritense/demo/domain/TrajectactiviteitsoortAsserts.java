package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TrajectactiviteitsoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectactiviteitsoortAllPropertiesEquals(Trajectactiviteitsoort expected, Trajectactiviteitsoort actual) {
        assertTrajectactiviteitsoortAutoGeneratedPropertiesEquals(expected, actual);
        assertTrajectactiviteitsoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectactiviteitsoortAllUpdatablePropertiesEquals(
        Trajectactiviteitsoort expected,
        Trajectactiviteitsoort actual
    ) {
        assertTrajectactiviteitsoortUpdatableFieldsEquals(expected, actual);
        assertTrajectactiviteitsoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectactiviteitsoortAutoGeneratedPropertiesEquals(
        Trajectactiviteitsoort expected,
        Trajectactiviteitsoort actual
    ) {
        assertThat(expected)
            .as("Verify Trajectactiviteitsoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectactiviteitsoortUpdatableFieldsEquals(Trajectactiviteitsoort expected, Trajectactiviteitsoort actual) {
        assertThat(expected)
            .as("Verify Trajectactiviteitsoort relevant properties")
            .satisfies(e -> assertThat(e.getAanleverensrg()).as("check aanleverensrg").isEqualTo(actual.getAanleverensrg()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getTypetrajectsrg()).as("check typetrajectsrg").isEqualTo(actual.getTypetrajectsrg()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectactiviteitsoortUpdatableRelationshipsEquals(
        Trajectactiviteitsoort expected,
        Trajectactiviteitsoort actual
    ) {}
}