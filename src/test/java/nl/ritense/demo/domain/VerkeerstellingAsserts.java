package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VerkeerstellingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerkeerstellingAllPropertiesEquals(Verkeerstelling expected, Verkeerstelling actual) {
        assertVerkeerstellingAutoGeneratedPropertiesEquals(expected, actual);
        assertVerkeerstellingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerkeerstellingAllUpdatablePropertiesEquals(Verkeerstelling expected, Verkeerstelling actual) {
        assertVerkeerstellingUpdatableFieldsEquals(expected, actual);
        assertVerkeerstellingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerkeerstellingAutoGeneratedPropertiesEquals(Verkeerstelling expected, Verkeerstelling actual) {
        assertThat(expected)
            .as("Verify Verkeerstelling auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerkeerstellingUpdatableFieldsEquals(Verkeerstelling expected, Verkeerstelling actual) {
        assertThat(expected)
            .as("Verify Verkeerstelling relevant properties")
            .satisfies(e -> assertThat(e.getAantal()).as("check aantal").isEqualTo(actual.getAantal()))
            .satisfies(e -> assertThat(e.getTijdtot()).as("check tijdtot").isEqualTo(actual.getTijdtot()))
            .satisfies(e -> assertThat(e.getTijdvanaf()).as("check tijdvanaf").isEqualTo(actual.getTijdvanaf()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerkeerstellingUpdatableRelationshipsEquals(Verkeerstelling expected, Verkeerstelling actual) {
        assertThat(expected)
            .as("Verify Verkeerstelling relationships")
            .satisfies(
                e -> assertThat(e.getGegenereerddoorSensor()).as("check gegenereerddoorSensor").isEqualTo(actual.getGegenereerddoorSensor())
            );
    }
}