package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassjAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassjAllPropertiesEquals(Classj expected, Classj actual) {
        assertClassjAutoGeneratedPropertiesEquals(expected, actual);
        assertClassjAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassjAllUpdatablePropertiesEquals(Classj expected, Classj actual) {
        assertClassjUpdatableFieldsEquals(expected, actual);
        assertClassjUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassjAutoGeneratedPropertiesEquals(Classj expected, Classj actual) {
        assertThat(expected)
            .as("Verify Classj auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassjUpdatableFieldsEquals(Classj expected, Classj actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassjUpdatableRelationshipsEquals(Classj expected, Classj actual) {}
}