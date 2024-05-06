package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class UitstroomredenAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitstroomredenAllPropertiesEquals(Uitstroomreden expected, Uitstroomreden actual) {
        assertUitstroomredenAutoGeneratedPropertiesEquals(expected, actual);
        assertUitstroomredenAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitstroomredenAllUpdatablePropertiesEquals(Uitstroomreden expected, Uitstroomreden actual) {
        assertUitstroomredenUpdatableFieldsEquals(expected, actual);
        assertUitstroomredenUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitstroomredenAutoGeneratedPropertiesEquals(Uitstroomreden expected, Uitstroomreden actual) {
        assertThat(expected)
            .as("Verify Uitstroomreden auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitstroomredenUpdatableFieldsEquals(Uitstroomreden expected, Uitstroomreden actual) {
        assertThat(expected)
            .as("Verify Uitstroomreden relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitstroomredenUpdatableRelationshipsEquals(Uitstroomreden expected, Uitstroomreden actual) {
        assertThat(expected)
            .as("Verify Uitstroomreden relationships")
            .satisfies(
                e ->
                    assertThat(e.getSoortuitstroomredenUitstroomredensoort())
                        .as("check soortuitstroomredenUitstroomredensoort")
                        .isEqualTo(actual.getSoortuitstroomredenUitstroomredensoort())
            );
    }
}