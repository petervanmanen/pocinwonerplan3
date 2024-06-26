package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FinancielesituatieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFinancielesituatieAllPropertiesEquals(Financielesituatie expected, Financielesituatie actual) {
        assertFinancielesituatieAutoGeneratedPropertiesEquals(expected, actual);
        assertFinancielesituatieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFinancielesituatieAllUpdatablePropertiesEquals(Financielesituatie expected, Financielesituatie actual) {
        assertFinancielesituatieUpdatableFieldsEquals(expected, actual);
        assertFinancielesituatieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFinancielesituatieAutoGeneratedPropertiesEquals(Financielesituatie expected, Financielesituatie actual) {
        assertThat(expected)
            .as("Verify Financielesituatie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFinancielesituatieUpdatableFieldsEquals(Financielesituatie expected, Financielesituatie actual) {
        assertThat(expected)
            .as("Verify Financielesituatie relevant properties")
            .satisfies(e -> assertThat(e.getDatumvastgesteld()).as("check datumvastgesteld").isEqualTo(actual.getDatumvastgesteld()))
            .satisfies(e -> assertThat(e.getSchuld()).as("check schuld").isEqualTo(actual.getSchuld()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFinancielesituatieUpdatableRelationshipsEquals(Financielesituatie expected, Financielesituatie actual) {}
}
