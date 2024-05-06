package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CaseaanmeldingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaseaanmeldingAllPropertiesEquals(Caseaanmelding expected, Caseaanmelding actual) {
        assertCaseaanmeldingAutoGeneratedPropertiesEquals(expected, actual);
        assertCaseaanmeldingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaseaanmeldingAllUpdatablePropertiesEquals(Caseaanmelding expected, Caseaanmelding actual) {
        assertCaseaanmeldingUpdatableFieldsEquals(expected, actual);
        assertCaseaanmeldingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaseaanmeldingAutoGeneratedPropertiesEquals(Caseaanmelding expected, Caseaanmelding actual) {
        assertThat(expected)
            .as("Verify Caseaanmelding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaseaanmeldingUpdatableFieldsEquals(Caseaanmelding expected, Caseaanmelding actual) {
        assertThat(expected)
            .as("Verify Caseaanmelding relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCaseaanmeldingUpdatableRelationshipsEquals(Caseaanmelding expected, Caseaanmelding actual) {}
}
