package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VervoersmiddelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVervoersmiddelAllPropertiesEquals(Vervoersmiddel expected, Vervoersmiddel actual) {
        assertVervoersmiddelAutoGeneratedPropertiesEquals(expected, actual);
        assertVervoersmiddelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVervoersmiddelAllUpdatablePropertiesEquals(Vervoersmiddel expected, Vervoersmiddel actual) {
        assertVervoersmiddelUpdatableFieldsEquals(expected, actual);
        assertVervoersmiddelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVervoersmiddelAutoGeneratedPropertiesEquals(Vervoersmiddel expected, Vervoersmiddel actual) {
        assertThat(expected)
            .as("Verify Vervoersmiddel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVervoersmiddelUpdatableFieldsEquals(Vervoersmiddel expected, Vervoersmiddel actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVervoersmiddelUpdatableRelationshipsEquals(Vervoersmiddel expected, Vervoersmiddel actual) {}
}
