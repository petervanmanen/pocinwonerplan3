package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DeelprocestypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocestypeAllPropertiesEquals(Deelprocestype expected, Deelprocestype actual) {
        assertDeelprocestypeAutoGeneratedPropertiesEquals(expected, actual);
        assertDeelprocestypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocestypeAllUpdatablePropertiesEquals(Deelprocestype expected, Deelprocestype actual) {
        assertDeelprocestypeUpdatableFieldsEquals(expected, actual);
        assertDeelprocestypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocestypeAutoGeneratedPropertiesEquals(Deelprocestype expected, Deelprocestype actual) {
        assertThat(expected)
            .as("Verify Deelprocestype auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocestypeUpdatableFieldsEquals(Deelprocestype expected, Deelprocestype actual) {
        assertThat(expected)
            .as("Verify Deelprocestype relevant properties")
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocestypeUpdatableRelationshipsEquals(Deelprocestype expected, Deelprocestype actual) {
        assertThat(expected)
            .as("Verify Deelprocestype relationships")
            .satisfies(
                e ->
                    assertThat(e.getIsdeelvanBedrijfsprocestype())
                        .as("check isdeelvanBedrijfsprocestype")
                        .isEqualTo(actual.getIsdeelvanBedrijfsprocestype())
            );
    }
}
