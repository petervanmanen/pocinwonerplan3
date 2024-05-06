package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AomstatusAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAomstatusAllPropertiesEquals(Aomstatus expected, Aomstatus actual) {
        assertAomstatusAutoGeneratedPropertiesEquals(expected, actual);
        assertAomstatusAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAomstatusAllUpdatablePropertiesEquals(Aomstatus expected, Aomstatus actual) {
        assertAomstatusUpdatableFieldsEquals(expected, actual);
        assertAomstatusUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAomstatusAutoGeneratedPropertiesEquals(Aomstatus expected, Aomstatus actual) {
        assertThat(expected)
            .as("Verify Aomstatus auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAomstatusUpdatableFieldsEquals(Aomstatus expected, Aomstatus actual) {
        assertThat(expected)
            .as("Verify Aomstatus relevant properties")
            .satisfies(e -> assertThat(e.getDatumbeginstatus()).as("check datumbeginstatus").isEqualTo(actual.getDatumbeginstatus()))
            .satisfies(e -> assertThat(e.getDatumeindestatus()).as("check datumeindestatus").isEqualTo(actual.getDatumeindestatus()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getStatuscode()).as("check statuscode").isEqualTo(actual.getStatuscode()))
            .satisfies(e -> assertThat(e.getStatusvolgorde()).as("check statusvolgorde").isEqualTo(actual.getStatusvolgorde()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAomstatusUpdatableRelationshipsEquals(Aomstatus expected, Aomstatus actual) {}
}
