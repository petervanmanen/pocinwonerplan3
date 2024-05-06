package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LeveringsvormAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeveringsvormAllPropertiesEquals(Leveringsvorm expected, Leveringsvorm actual) {
        assertLeveringsvormAutoGeneratedPropertiesEquals(expected, actual);
        assertLeveringsvormAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeveringsvormAllUpdatablePropertiesEquals(Leveringsvorm expected, Leveringsvorm actual) {
        assertLeveringsvormUpdatableFieldsEquals(expected, actual);
        assertLeveringsvormUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeveringsvormAutoGeneratedPropertiesEquals(Leveringsvorm expected, Leveringsvorm actual) {
        assertThat(expected)
            .as("Verify Leveringsvorm auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeveringsvormUpdatableFieldsEquals(Leveringsvorm expected, Leveringsvorm actual) {
        assertThat(expected)
            .as("Verify Leveringsvorm relevant properties")
            .satisfies(e -> assertThat(e.getLeveringsvormcode()).as("check leveringsvormcode").isEqualTo(actual.getLeveringsvormcode()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getWet()).as("check wet").isEqualTo(actual.getWet()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeveringsvormUpdatableRelationshipsEquals(Leveringsvorm expected, Leveringsvorm actual) {}
}
