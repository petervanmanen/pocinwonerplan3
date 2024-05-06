package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FormelehistorieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormelehistorieAllPropertiesEquals(Formelehistorie expected, Formelehistorie actual) {
        assertFormelehistorieAutoGeneratedPropertiesEquals(expected, actual);
        assertFormelehistorieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormelehistorieAllUpdatablePropertiesEquals(Formelehistorie expected, Formelehistorie actual) {
        assertFormelehistorieUpdatableFieldsEquals(expected, actual);
        assertFormelehistorieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormelehistorieAutoGeneratedPropertiesEquals(Formelehistorie expected, Formelehistorie actual) {
        assertThat(expected)
            .as("Verify Formelehistorie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormelehistorieUpdatableFieldsEquals(Formelehistorie expected, Formelehistorie actual) {
        assertThat(expected)
            .as("Verify Formelehistorie relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getTijdstipregistratiegegevens())
                        .as("check tijdstipregistratiegegevens")
                        .isEqualTo(actual.getTijdstipregistratiegegevens())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFormelehistorieUpdatableRelationshipsEquals(Formelehistorie expected, Formelehistorie actual) {}
}