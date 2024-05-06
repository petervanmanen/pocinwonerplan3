package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RelatieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatieAllPropertiesEquals(Relatie expected, Relatie actual) {
        assertRelatieAutoGeneratedPropertiesEquals(expected, actual);
        assertRelatieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatieAllUpdatablePropertiesEquals(Relatie expected, Relatie actual) {
        assertRelatieUpdatableFieldsEquals(expected, actual);
        assertRelatieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatieAutoGeneratedPropertiesEquals(Relatie expected, Relatie actual) {
        assertThat(expected)
            .as("Verify Relatie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatieUpdatableFieldsEquals(Relatie expected, Relatie actual) {
        assertThat(expected)
            .as("Verify Relatie relevant properties")
            .satisfies(e -> assertThat(e.getRelatiesoort()).as("check relatiesoort").isEqualTo(actual.getRelatiesoort()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRelatieUpdatableRelationshipsEquals(Relatie expected, Relatie actual) {
        assertThat(expected)
            .as("Verify Relatie relationships")
            .satisfies(
                e -> assertThat(e.getIssoortRelatiesoort()).as("check issoortRelatiesoort").isEqualTo(actual.getIssoortRelatiesoort())
            )
            .satisfies(
                e ->
                    assertThat(e.getMaaktonderdeelvanHuishoudens())
                        .as("check maaktonderdeelvanHuishoudens")
                        .isEqualTo(actual.getMaaktonderdeelvanHuishoudens())
            )
            .satisfies(
                e -> assertThat(e.getHeeftrelatieClients()).as("check heeftrelatieClients").isEqualTo(actual.getHeeftrelatieClients())
            );
    }
}
