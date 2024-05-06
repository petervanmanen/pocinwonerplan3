package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DeelprocesAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocesAllPropertiesEquals(Deelproces expected, Deelproces actual) {
        assertDeelprocesAutoGeneratedPropertiesEquals(expected, actual);
        assertDeelprocesAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocesAllUpdatablePropertiesEquals(Deelproces expected, Deelproces actual) {
        assertDeelprocesUpdatableFieldsEquals(expected, actual);
        assertDeelprocesUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocesAutoGeneratedPropertiesEquals(Deelproces expected, Deelproces actual) {
        assertThat(expected)
            .as("Verify Deelproces auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocesUpdatableFieldsEquals(Deelproces expected, Deelproces actual) {
        assertThat(expected)
            .as("Verify Deelproces relevant properties")
            .satisfies(e -> assertThat(e.getDatumafgehandeld()).as("check datumafgehandeld").isEqualTo(actual.getDatumafgehandeld()))
            .satisfies(e -> assertThat(e.getDatumgepland()).as("check datumgepland").isEqualTo(actual.getDatumgepland()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDeelprocesUpdatableRelationshipsEquals(Deelproces expected, Deelproces actual) {
        assertThat(expected)
            .as("Verify Deelproces relationships")
            .satisfies(
                e -> assertThat(e.getIsvanDeelprocestype()).as("check isvanDeelprocestype").isEqualTo(actual.getIsvanDeelprocestype())
            );
    }
}