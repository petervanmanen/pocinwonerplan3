package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LandofgebiedAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLandofgebiedAllPropertiesEquals(Landofgebied expected, Landofgebied actual) {
        assertLandofgebiedAutoGeneratedPropertiesEquals(expected, actual);
        assertLandofgebiedAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLandofgebiedAllUpdatablePropertiesEquals(Landofgebied expected, Landofgebied actual) {
        assertLandofgebiedUpdatableFieldsEquals(expected, actual);
        assertLandofgebiedUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLandofgebiedAutoGeneratedPropertiesEquals(Landofgebied expected, Landofgebied actual) {
        assertThat(expected)
            .as("Verify Landofgebied auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLandofgebiedUpdatableFieldsEquals(Landofgebied expected, Landofgebied actual) {
        assertThat(expected)
            .as("Verify Landofgebied relevant properties")
            .satisfies(e -> assertThat(e.getDatumeindeland()).as("check datumeindeland").isEqualTo(actual.getDatumeindeland()))
            .satisfies(e -> assertThat(e.getDatumingangland()).as("check datumingangland").isEqualTo(actual.getDatumingangland()))
            .satisfies(e -> assertThat(e.getLandcode()).as("check landcode").isEqualTo(actual.getLandcode()))
            .satisfies(e -> assertThat(e.getLandcodeiso()).as("check landcodeiso").isEqualTo(actual.getLandcodeiso()))
            .satisfies(e -> assertThat(e.getLandnaam()).as("check landnaam").isEqualTo(actual.getLandnaam()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLandofgebiedUpdatableRelationshipsEquals(Landofgebied expected, Landofgebied actual) {}
}