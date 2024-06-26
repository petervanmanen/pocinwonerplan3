package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RelatiesoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatiesoortAllPropertiesEquals(Relatiesoort expected, Relatiesoort actual) {
        assertRelatiesoortAutoGeneratedPropertiesEquals(expected, actual);
        assertRelatiesoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatiesoortAllUpdatablePropertiesEquals(Relatiesoort expected, Relatiesoort actual) {
        assertRelatiesoortUpdatableFieldsEquals(expected, actual);
        assertRelatiesoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatiesoortAutoGeneratedPropertiesEquals(Relatiesoort expected, Relatiesoort actual) {
        assertThat(expected)
            .as("Verify Relatiesoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatiesoortUpdatableFieldsEquals(Relatiesoort expected, Relatiesoort actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatiesoortUpdatableRelationshipsEquals(Relatiesoort expected, Relatiesoort actual) {}
}
