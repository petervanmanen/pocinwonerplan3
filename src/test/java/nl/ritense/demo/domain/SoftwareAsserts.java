package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SoftwareAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoftwareAllPropertiesEquals(Software expected, Software actual) {
        assertSoftwareAutoGeneratedPropertiesEquals(expected, actual);
        assertSoftwareAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoftwareAllUpdatablePropertiesEquals(Software expected, Software actual) {
        assertSoftwareUpdatableFieldsEquals(expected, actual);
        assertSoftwareUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoftwareAutoGeneratedPropertiesEquals(Software expected, Software actual) {
        assertThat(expected)
            .as("Verify Software auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoftwareUpdatableFieldsEquals(Software expected, Software actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoftwareUpdatableRelationshipsEquals(Software expected, Software actual) {}
}
