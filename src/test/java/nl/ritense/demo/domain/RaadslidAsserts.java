package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RaadslidAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadslidAllPropertiesEquals(Raadslid expected, Raadslid actual) {
        assertRaadslidAutoGeneratedPropertiesEquals(expected, actual);
        assertRaadslidAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadslidAllUpdatablePropertiesEquals(Raadslid expected, Raadslid actual) {
        assertRaadslidUpdatableFieldsEquals(expected, actual);
        assertRaadslidUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadslidAutoGeneratedPropertiesEquals(Raadslid expected, Raadslid actual) {
        assertThat(expected)
            .as("Verify Raadslid auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadslidUpdatableFieldsEquals(Raadslid expected, Raadslid actual) {
        assertThat(expected)
            .as("Verify Raadslid relevant properties")
            .satisfies(e -> assertThat(e.getAchternaam()).as("check achternaam").isEqualTo(actual.getAchternaam()))
            .satisfies(e -> assertThat(e.getDatumaanstelling()).as("check datumaanstelling").isEqualTo(actual.getDatumaanstelling()))
            .satisfies(e -> assertThat(e.getDatumuittreding()).as("check datumuittreding").isEqualTo(actual.getDatumuittreding()))
            .satisfies(e -> assertThat(e.getFractie()).as("check fractie").isEqualTo(actual.getFractie()))
            .satisfies(e -> assertThat(e.getTitel()).as("check titel").isEqualTo(actual.getTitel()))
            .satisfies(e -> assertThat(e.getVoornaam()).as("check voornaam").isEqualTo(actual.getVoornaam()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRaadslidUpdatableRelationshipsEquals(Raadslid expected, Raadslid actual) {
        assertThat(expected)
            .as("Verify Raadslid relationships")
            .satisfies(
                e ->
                    assertThat(e.getIslidvanRaadscommissies())
                        .as("check islidvanRaadscommissies")
                        .isEqualTo(actual.getIslidvanRaadscommissies())
            );
    }
}
