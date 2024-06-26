package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VerblijfplaatsazcAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfplaatsazcAllPropertiesEquals(Verblijfplaatsazc expected, Verblijfplaatsazc actual) {
        assertVerblijfplaatsazcAutoGeneratedPropertiesEquals(expected, actual);
        assertVerblijfplaatsazcAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfplaatsazcAllUpdatablePropertiesEquals(Verblijfplaatsazc expected, Verblijfplaatsazc actual) {
        assertVerblijfplaatsazcUpdatableFieldsEquals(expected, actual);
        assertVerblijfplaatsazcUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfplaatsazcAutoGeneratedPropertiesEquals(Verblijfplaatsazc expected, Verblijfplaatsazc actual) {
        assertThat(expected)
            .as("Verify Verblijfplaatsazc auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfplaatsazcUpdatableFieldsEquals(Verblijfplaatsazc expected, Verblijfplaatsazc actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfplaatsazcUpdatableRelationshipsEquals(Verblijfplaatsazc expected, Verblijfplaatsazc actual) {}
}
