package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class InitiatiefnemerAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInitiatiefnemerAllPropertiesEquals(Initiatiefnemer expected, Initiatiefnemer actual) {
        assertInitiatiefnemerAutoGeneratedPropertiesEquals(expected, actual);
        assertInitiatiefnemerAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInitiatiefnemerAllUpdatablePropertiesEquals(Initiatiefnemer expected, Initiatiefnemer actual) {
        assertInitiatiefnemerUpdatableFieldsEquals(expected, actual);
        assertInitiatiefnemerUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInitiatiefnemerAutoGeneratedPropertiesEquals(Initiatiefnemer expected, Initiatiefnemer actual) {
        assertThat(expected)
            .as("Verify Initiatiefnemer auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInitiatiefnemerUpdatableFieldsEquals(Initiatiefnemer expected, Initiatiefnemer actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInitiatiefnemerUpdatableRelationshipsEquals(Initiatiefnemer expected, Initiatiefnemer actual) {}
}