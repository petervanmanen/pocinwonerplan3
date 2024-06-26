package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class StellingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStellingAllPropertiesEquals(Stelling expected, Stelling actual) {
        assertStellingAutoGeneratedPropertiesEquals(expected, actual);
        assertStellingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStellingAllUpdatablePropertiesEquals(Stelling expected, Stelling actual) {
        assertStellingUpdatableFieldsEquals(expected, actual);
        assertStellingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStellingAutoGeneratedPropertiesEquals(Stelling expected, Stelling actual) {
        assertThat(expected)
            .as("Verify Stelling auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStellingUpdatableFieldsEquals(Stelling expected, Stelling actual) {
        assertThat(expected)
            .as("Verify Stelling relevant properties")
            .satisfies(e -> assertThat(e.getInhoud()).as("check inhoud").isEqualTo(actual.getInhoud()))
            .satisfies(e -> assertThat(e.getStellingcode()).as("check stellingcode").isEqualTo(actual.getStellingcode()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStellingUpdatableRelationshipsEquals(Stelling expected, Stelling actual) {
        assertThat(expected)
            .as("Verify Stelling relationships")
            .satisfies(e -> assertThat(e.getHeeftDepot()).as("check heeftDepot").isEqualTo(actual.getHeeftDepot()));
    }
}
