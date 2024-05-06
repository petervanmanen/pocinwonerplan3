package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AfspraakstatusAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAfspraakstatusAllPropertiesEquals(Afspraakstatus expected, Afspraakstatus actual) {
        assertAfspraakstatusAutoGeneratedPropertiesEquals(expected, actual);
        assertAfspraakstatusAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAfspraakstatusAllUpdatablePropertiesEquals(Afspraakstatus expected, Afspraakstatus actual) {
        assertAfspraakstatusUpdatableFieldsEquals(expected, actual);
        assertAfspraakstatusUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAfspraakstatusAutoGeneratedPropertiesEquals(Afspraakstatus expected, Afspraakstatus actual) {
        assertThat(expected)
            .as("Verify Afspraakstatus auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAfspraakstatusUpdatableFieldsEquals(Afspraakstatus expected, Afspraakstatus actual) {
        assertThat(expected)
            .as("Verify Afspraakstatus relevant properties")
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAfspraakstatusUpdatableRelationshipsEquals(Afspraakstatus expected, Afspraakstatus actual) {}
}