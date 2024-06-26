package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class B1Asserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertB1AllPropertiesEquals(B1 expected, B1 actual) {
        assertB1AutoGeneratedPropertiesEquals(expected, actual);
        assertB1AllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertB1AllUpdatablePropertiesEquals(B1 expected, B1 actual) {
        assertB1UpdatableFieldsEquals(expected, actual);
        assertB1UpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertB1AutoGeneratedPropertiesEquals(B1 expected, B1 actual) {
        assertThat(expected)
            .as("Verify B1 auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertB1UpdatableFieldsEquals(B1 expected, B1 actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertB1UpdatableRelationshipsEquals(B1 expected, B1 actual) {}
}
