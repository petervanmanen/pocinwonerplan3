package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DienstAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDienstAllPropertiesEquals(Dienst expected, Dienst actual) {
        assertDienstAutoGeneratedPropertiesEquals(expected, actual);
        assertDienstAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDienstAllUpdatablePropertiesEquals(Dienst expected, Dienst actual) {
        assertDienstUpdatableFieldsEquals(expected, actual);
        assertDienstUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDienstAutoGeneratedPropertiesEquals(Dienst expected, Dienst actual) {
        assertThat(expected)
            .as("Verify Dienst auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDienstUpdatableFieldsEquals(Dienst expected, Dienst actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDienstUpdatableRelationshipsEquals(Dienst expected, Dienst actual) {
        assertThat(expected)
            .as("Verify Dienst relationships")
            .satisfies(e -> assertThat(e.getStartZaaktype()).as("check startZaaktype").isEqualTo(actual.getStartZaaktype()))
            .satisfies(e -> assertThat(e.getHeeftOnderwerp()).as("check heeftOnderwerp").isEqualTo(actual.getHeeftOnderwerp()))
            .satisfies(e -> assertThat(e.getBetreftProduct()).as("check betreftProduct").isEqualTo(actual.getBetreftProduct()));
    }
}
