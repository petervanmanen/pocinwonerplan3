package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HotelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHotelAllPropertiesEquals(Hotel expected, Hotel actual) {
        assertHotelAutoGeneratedPropertiesEquals(expected, actual);
        assertHotelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHotelAllUpdatablePropertiesEquals(Hotel expected, Hotel actual) {
        assertHotelUpdatableFieldsEquals(expected, actual);
        assertHotelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHotelAutoGeneratedPropertiesEquals(Hotel expected, Hotel actual) {
        assertThat(expected)
            .as("Verify Hotel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHotelUpdatableFieldsEquals(Hotel expected, Hotel actual) {
        assertThat(expected)
            .as("Verify Hotel relevant properties")
            .satisfies(e -> assertThat(e.getAantalkamers()).as("check aantalkamers").isEqualTo(actual.getAantalkamers()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHotelUpdatableRelationshipsEquals(Hotel expected, Hotel actual) {}
}
