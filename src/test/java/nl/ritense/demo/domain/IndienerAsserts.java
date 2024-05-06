package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class IndienerAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIndienerAllPropertiesEquals(Indiener expected, Indiener actual) {
        assertIndienerAutoGeneratedPropertiesEquals(expected, actual);
        assertIndienerAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIndienerAllUpdatablePropertiesEquals(Indiener expected, Indiener actual) {
        assertIndienerUpdatableFieldsEquals(expected, actual);
        assertIndienerUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIndienerAutoGeneratedPropertiesEquals(Indiener expected, Indiener actual) {
        assertThat(expected)
            .as("Verify Indiener auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIndienerUpdatableFieldsEquals(Indiener expected, Indiener actual) {
        assertThat(expected)
            .as("Verify Indiener relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIndienerUpdatableRelationshipsEquals(Indiener expected, Indiener actual) {
        assertThat(expected)
            .as("Verify Indiener relationships")
            .satisfies(e -> assertThat(e.getIsCollegelid()).as("check isCollegelid").isEqualTo(actual.getIsCollegelid()))
            .satisfies(e -> assertThat(e.getIsRaadslid()).as("check isRaadslid").isEqualTo(actual.getIsRaadslid()))
            .satisfies(e -> assertThat(e.getIsRechtspersoon()).as("check isRechtspersoon").isEqualTo(actual.getIsRechtspersoon()))
            .satisfies(e -> assertThat(e.getHeeftRaadsstuks()).as("check heeftRaadsstuks").isEqualTo(actual.getHeeftRaadsstuks()));
    }
}
