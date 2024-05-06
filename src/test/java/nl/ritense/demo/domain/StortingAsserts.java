package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class StortingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStortingAllPropertiesEquals(Storting expected, Storting actual) {
        assertStortingAutoGeneratedPropertiesEquals(expected, actual);
        assertStortingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStortingAllUpdatablePropertiesEquals(Storting expected, Storting actual) {
        assertStortingUpdatableFieldsEquals(expected, actual);
        assertStortingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStortingAutoGeneratedPropertiesEquals(Storting expected, Storting actual) {
        assertThat(expected)
            .as("Verify Storting auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStortingUpdatableFieldsEquals(Storting expected, Storting actual) {
        assertThat(expected)
            .as("Verify Storting relevant properties")
            .satisfies(e -> assertThat(e.getDatumtijd()).as("check datumtijd").isEqualTo(actual.getDatumtijd()))
            .satisfies(e -> assertThat(e.getGewicht()).as("check gewicht").isEqualTo(actual.getGewicht()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStortingUpdatableRelationshipsEquals(Storting expected, Storting actual) {
        assertThat(expected)
            .as("Verify Storting relationships")
            .satisfies(e -> assertThat(e.getBijMilieustraat()).as("check bijMilieustraat").isEqualTo(actual.getBijMilieustraat()))
            .satisfies(e -> assertThat(e.getFractieFracties()).as("check fractieFracties").isEqualTo(actual.getFractieFracties()))
            .satisfies(
                e ->
                    assertThat(e.getUitgevoerdestortingPas())
                        .as("check uitgevoerdestortingPas")
                        .isEqualTo(actual.getUitgevoerdestortingPas())
            );
    }
}