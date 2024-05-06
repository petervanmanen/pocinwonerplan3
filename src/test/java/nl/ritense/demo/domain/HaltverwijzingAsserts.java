package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HaltverwijzingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHaltverwijzingAllPropertiesEquals(Haltverwijzing expected, Haltverwijzing actual) {
        assertHaltverwijzingAutoGeneratedPropertiesEquals(expected, actual);
        assertHaltverwijzingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHaltverwijzingAllUpdatablePropertiesEquals(Haltverwijzing expected, Haltverwijzing actual) {
        assertHaltverwijzingUpdatableFieldsEquals(expected, actual);
        assertHaltverwijzingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHaltverwijzingAutoGeneratedPropertiesEquals(Haltverwijzing expected, Haltverwijzing actual) {
        assertThat(expected)
            .as("Verify Haltverwijzing auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHaltverwijzingUpdatableFieldsEquals(Haltverwijzing expected, Haltverwijzing actual) {
        assertThat(expected)
            .as("Verify Haltverwijzing relevant properties")
            .satisfies(e -> assertThat(e.getAfdoening()).as("check afdoening").isEqualTo(actual.getAfdoening()))
            .satisfies(e -> assertThat(e.getDatummutatie()).as("check datummutatie").isEqualTo(actual.getDatummutatie()))
            .satisfies(e -> assertThat(e.getDatumretour()).as("check datumretour").isEqualTo(actual.getDatumretour()))
            .satisfies(e -> assertThat(e.getMemo()).as("check memo").isEqualTo(actual.getMemo()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHaltverwijzingUpdatableRelationshipsEquals(Haltverwijzing expected, Haltverwijzing actual) {}
}