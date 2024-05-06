package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MailingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMailingAllPropertiesEquals(Mailing expected, Mailing actual) {
        assertMailingAutoGeneratedPropertiesEquals(expected, actual);
        assertMailingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMailingAllUpdatablePropertiesEquals(Mailing expected, Mailing actual) {
        assertMailingUpdatableFieldsEquals(expected, actual);
        assertMailingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMailingAutoGeneratedPropertiesEquals(Mailing expected, Mailing actual) {
        assertThat(expected)
            .as("Verify Mailing auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMailingUpdatableFieldsEquals(Mailing expected, Mailing actual) {
        assertThat(expected)
            .as("Verify Mailing relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMailingUpdatableRelationshipsEquals(Mailing expected, Mailing actual) {
        assertThat(expected)
            .as("Verify Mailing relationships")
            .satisfies(
                e ->
                    assertThat(e.getVersturenaanMuseumrelaties())
                        .as("check versturenaanMuseumrelaties")
                        .isEqualTo(actual.getVersturenaanMuseumrelaties())
            );
    }
}