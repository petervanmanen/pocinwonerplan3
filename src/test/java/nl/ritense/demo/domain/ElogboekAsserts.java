package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ElogboekAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertElogboekAllPropertiesEquals(Elogboek expected, Elogboek actual) {
        assertElogboekAutoGeneratedPropertiesEquals(expected, actual);
        assertElogboekAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertElogboekAllUpdatablePropertiesEquals(Elogboek expected, Elogboek actual) {
        assertElogboekUpdatableFieldsEquals(expected, actual);
        assertElogboekUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertElogboekAutoGeneratedPropertiesEquals(Elogboek expected, Elogboek actual) {
        assertThat(expected)
            .as("Verify Elogboek auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertElogboekUpdatableFieldsEquals(Elogboek expected, Elogboek actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertElogboekUpdatableRelationshipsEquals(Elogboek expected, Elogboek actual) {}
}
