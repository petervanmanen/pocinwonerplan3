package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassificatieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassificatieAllPropertiesEquals(Classificatie expected, Classificatie actual) {
        assertClassificatieAutoGeneratedPropertiesEquals(expected, actual);
        assertClassificatieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassificatieAllUpdatablePropertiesEquals(Classificatie expected, Classificatie actual) {
        assertClassificatieUpdatableFieldsEquals(expected, actual);
        assertClassificatieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassificatieAutoGeneratedPropertiesEquals(Classificatie expected, Classificatie actual) {
        assertThat(expected)
            .as("Verify Classificatie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassificatieUpdatableFieldsEquals(Classificatie expected, Classificatie actual) {
        assertThat(expected)
            .as("Verify Classificatie relevant properties")
            .satisfies(
                e -> assertThat(e.getBevatpersoonsgegevens()).as("check bevatpersoonsgegevens").isEqualTo(actual.getBevatpersoonsgegevens())
            )
            .satisfies(
                e ->
                    assertThat(e.getGerelateerdpersoonsgegevens())
                        .as("check gerelateerdpersoonsgegevens")
                        .isEqualTo(actual.getGerelateerdpersoonsgegevens())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClassificatieUpdatableRelationshipsEquals(Classificatie expected, Classificatie actual) {
        assertThat(expected)
            .as("Verify Classificatie relationships")
            .satisfies(
                e ->
                    assertThat(e.getGeclassificeerdalsGegevens())
                        .as("check geclassificeerdalsGegevens")
                        .isEqualTo(actual.getGeclassificeerdalsGegevens())
            );
    }
}
