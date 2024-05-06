package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class EobjecttypebAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEobjecttypebAllPropertiesEquals(Eobjecttypeb expected, Eobjecttypeb actual) {
        assertEobjecttypebAutoGeneratedPropertiesEquals(expected, actual);
        assertEobjecttypebAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEobjecttypebAllUpdatablePropertiesEquals(Eobjecttypeb expected, Eobjecttypeb actual) {
        assertEobjecttypebUpdatableFieldsEquals(expected, actual);
        assertEobjecttypebUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEobjecttypebAutoGeneratedPropertiesEquals(Eobjecttypeb expected, Eobjecttypeb actual) {
        assertThat(expected)
            .as("Verify Eobjecttypeb auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEobjecttypebUpdatableFieldsEquals(Eobjecttypeb expected, Eobjecttypeb actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEobjecttypebUpdatableRelationshipsEquals(Eobjecttypeb expected, Eobjecttypeb actual) {}
}
