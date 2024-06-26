package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class GrondbeheerderAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGrondbeheerderAllPropertiesEquals(Grondbeheerder expected, Grondbeheerder actual) {
        assertGrondbeheerderAutoGeneratedPropertiesEquals(expected, actual);
        assertGrondbeheerderAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGrondbeheerderAllUpdatablePropertiesEquals(Grondbeheerder expected, Grondbeheerder actual) {
        assertGrondbeheerderUpdatableFieldsEquals(expected, actual);
        assertGrondbeheerderUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGrondbeheerderAutoGeneratedPropertiesEquals(Grondbeheerder expected, Grondbeheerder actual) {
        assertThat(expected)
            .as("Verify Grondbeheerder auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGrondbeheerderUpdatableFieldsEquals(Grondbeheerder expected, Grondbeheerder actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGrondbeheerderUpdatableRelationshipsEquals(Grondbeheerder expected, Grondbeheerder actual) {}
}
