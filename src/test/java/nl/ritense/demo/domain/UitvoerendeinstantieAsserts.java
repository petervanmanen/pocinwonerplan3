package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class UitvoerendeinstantieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerendeinstantieAllPropertiesEquals(Uitvoerendeinstantie expected, Uitvoerendeinstantie actual) {
        assertUitvoerendeinstantieAutoGeneratedPropertiesEquals(expected, actual);
        assertUitvoerendeinstantieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerendeinstantieAllUpdatablePropertiesEquals(Uitvoerendeinstantie expected, Uitvoerendeinstantie actual) {
        assertUitvoerendeinstantieUpdatableFieldsEquals(expected, actual);
        assertUitvoerendeinstantieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerendeinstantieAutoGeneratedPropertiesEquals(Uitvoerendeinstantie expected, Uitvoerendeinstantie actual) {
        assertThat(expected)
            .as("Verify Uitvoerendeinstantie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerendeinstantieUpdatableFieldsEquals(Uitvoerendeinstantie expected, Uitvoerendeinstantie actual) {
        assertThat(expected)
            .as("Verify Uitvoerendeinstantie relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerendeinstantieUpdatableRelationshipsEquals(Uitvoerendeinstantie expected, Uitvoerendeinstantie actual) {}
}
