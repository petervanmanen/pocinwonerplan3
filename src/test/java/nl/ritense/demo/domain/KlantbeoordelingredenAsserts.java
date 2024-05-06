package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KlantbeoordelingredenAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantbeoordelingredenAllPropertiesEquals(Klantbeoordelingreden expected, Klantbeoordelingreden actual) {
        assertKlantbeoordelingredenAutoGeneratedPropertiesEquals(expected, actual);
        assertKlantbeoordelingredenAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantbeoordelingredenAllUpdatablePropertiesEquals(
        Klantbeoordelingreden expected,
        Klantbeoordelingreden actual
    ) {
        assertKlantbeoordelingredenUpdatableFieldsEquals(expected, actual);
        assertKlantbeoordelingredenUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantbeoordelingredenAutoGeneratedPropertiesEquals(
        Klantbeoordelingreden expected,
        Klantbeoordelingreden actual
    ) {
        assertThat(expected)
            .as("Verify Klantbeoordelingreden auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantbeoordelingredenUpdatableFieldsEquals(Klantbeoordelingreden expected, Klantbeoordelingreden actual) {
        assertThat(expected)
            .as("Verify Klantbeoordelingreden relevant properties")
            .satisfies(e -> assertThat(e.getReden()).as("check reden").isEqualTo(actual.getReden()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKlantbeoordelingredenUpdatableRelationshipsEquals(
        Klantbeoordelingreden expected,
        Klantbeoordelingreden actual
    ) {
        assertThat(expected)
            .as("Verify Klantbeoordelingreden relationships")
            .satisfies(
                e -> assertThat(e.getHeeftKlantbeoordeling()).as("check heeftKlantbeoordeling").isEqualTo(actual.getHeeftKlantbeoordeling())
            );
    }
}
