package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternebronAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExternebronAllPropertiesEquals(Externebron expected, Externebron actual) {
        assertExternebronAutoGeneratedPropertiesEquals(expected, actual);
        assertExternebronAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExternebronAllUpdatablePropertiesEquals(Externebron expected, Externebron actual) {
        assertExternebronUpdatableFieldsEquals(expected, actual);
        assertExternebronUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExternebronAutoGeneratedPropertiesEquals(Externebron expected, Externebron actual) {
        assertThat(expected)
            .as("Verify Externebron auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExternebronUpdatableFieldsEquals(Externebron expected, Externebron actual) {
        assertThat(expected)
            .as("Verify Externebron relevant properties")
            .satisfies(e -> assertThat(e.getGuid()).as("check guid").isEqualTo(actual.getGuid()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertExternebronUpdatableRelationshipsEquals(Externebron expected, Externebron actual) {}
}