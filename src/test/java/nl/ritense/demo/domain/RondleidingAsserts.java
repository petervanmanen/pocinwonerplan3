package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RondleidingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRondleidingAllPropertiesEquals(Rondleiding expected, Rondleiding actual) {
        assertRondleidingAutoGeneratedPropertiesEquals(expected, actual);
        assertRondleidingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRondleidingAllUpdatablePropertiesEquals(Rondleiding expected, Rondleiding actual) {
        assertRondleidingUpdatableFieldsEquals(expected, actual);
        assertRondleidingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRondleidingAutoGeneratedPropertiesEquals(Rondleiding expected, Rondleiding actual) {
        assertThat(expected)
            .as("Verify Rondleiding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRondleidingUpdatableFieldsEquals(Rondleiding expected, Rondleiding actual) {
        assertThat(expected)
            .as("Verify Rondleiding relevant properties")
            .satisfies(e -> assertThat(e.getEindtijd()).as("check eindtijd").isEqualTo(actual.getEindtijd()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getStarttijd()).as("check starttijd").isEqualTo(actual.getStarttijd()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRondleidingUpdatableRelationshipsEquals(Rondleiding expected, Rondleiding actual) {
        assertThat(expected)
            .as("Verify Rondleiding relationships")
            .satisfies(
                e -> assertThat(e.getVoorTentoonstelling()).as("check voorTentoonstelling").isEqualTo(actual.getVoorTentoonstelling())
            );
    }
}
