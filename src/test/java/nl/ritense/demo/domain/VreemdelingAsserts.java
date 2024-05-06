package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VreemdelingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVreemdelingAllPropertiesEquals(Vreemdeling expected, Vreemdeling actual) {
        assertVreemdelingAutoGeneratedPropertiesEquals(expected, actual);
        assertVreemdelingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVreemdelingAllUpdatablePropertiesEquals(Vreemdeling expected, Vreemdeling actual) {
        assertVreemdelingUpdatableFieldsEquals(expected, actual);
        assertVreemdelingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVreemdelingAutoGeneratedPropertiesEquals(Vreemdeling expected, Vreemdeling actual) {
        assertThat(expected)
            .as("Verify Vreemdeling auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVreemdelingUpdatableFieldsEquals(Vreemdeling expected, Vreemdeling actual) {
        assertThat(expected)
            .as("Verify Vreemdeling relevant properties")
            .satisfies(e -> assertThat(e.getSociaalreferent()).as("check sociaalreferent").isEqualTo(actual.getSociaalreferent()))
            .satisfies(e -> assertThat(e.getVnummer()).as("check vnummer").isEqualTo(actual.getVnummer()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVreemdelingUpdatableRelationshipsEquals(Vreemdeling expected, Vreemdeling actual) {}
}
