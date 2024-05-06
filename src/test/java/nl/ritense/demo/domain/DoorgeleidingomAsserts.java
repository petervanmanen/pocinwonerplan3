package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DoorgeleidingomAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDoorgeleidingomAllPropertiesEquals(Doorgeleidingom expected, Doorgeleidingom actual) {
        assertDoorgeleidingomAutoGeneratedPropertiesEquals(expected, actual);
        assertDoorgeleidingomAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDoorgeleidingomAllUpdatablePropertiesEquals(Doorgeleidingom expected, Doorgeleidingom actual) {
        assertDoorgeleidingomUpdatableFieldsEquals(expected, actual);
        assertDoorgeleidingomUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDoorgeleidingomAutoGeneratedPropertiesEquals(Doorgeleidingom expected, Doorgeleidingom actual) {
        assertThat(expected)
            .as("Verify Doorgeleidingom auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDoorgeleidingomUpdatableFieldsEquals(Doorgeleidingom expected, Doorgeleidingom actual) {
        assertThat(expected)
            .as("Verify Doorgeleidingom relevant properties")
            .satisfies(e -> assertThat(e.getAfdoening()).as("check afdoening").isEqualTo(actual.getAfdoening()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDoorgeleidingomUpdatableRelationshipsEquals(Doorgeleidingom expected, Doorgeleidingom actual) {}
}