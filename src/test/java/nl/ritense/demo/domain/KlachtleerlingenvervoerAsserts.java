package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KlachtleerlingenvervoerAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlachtleerlingenvervoerAllPropertiesEquals(Klachtleerlingenvervoer expected, Klachtleerlingenvervoer actual) {
        assertKlachtleerlingenvervoerAutoGeneratedPropertiesEquals(expected, actual);
        assertKlachtleerlingenvervoerAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlachtleerlingenvervoerAllUpdatablePropertiesEquals(
        Klachtleerlingenvervoer expected,
        Klachtleerlingenvervoer actual
    ) {
        assertKlachtleerlingenvervoerUpdatableFieldsEquals(expected, actual);
        assertKlachtleerlingenvervoerUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlachtleerlingenvervoerAutoGeneratedPropertiesEquals(
        Klachtleerlingenvervoer expected,
        Klachtleerlingenvervoer actual
    ) {
        assertThat(expected)
            .as("Verify Klachtleerlingenvervoer auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlachtleerlingenvervoerUpdatableFieldsEquals(
        Klachtleerlingenvervoer expected,
        Klachtleerlingenvervoer actual
    ) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlachtleerlingenvervoerUpdatableRelationshipsEquals(
        Klachtleerlingenvervoer expected,
        Klachtleerlingenvervoer actual
    ) {}
}