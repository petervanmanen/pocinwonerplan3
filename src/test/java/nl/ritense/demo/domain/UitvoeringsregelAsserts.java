package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class UitvoeringsregelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoeringsregelAllPropertiesEquals(Uitvoeringsregel expected, Uitvoeringsregel actual) {
        assertUitvoeringsregelAutoGeneratedPropertiesEquals(expected, actual);
        assertUitvoeringsregelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoeringsregelAllUpdatablePropertiesEquals(Uitvoeringsregel expected, Uitvoeringsregel actual) {
        assertUitvoeringsregelUpdatableFieldsEquals(expected, actual);
        assertUitvoeringsregelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoeringsregelAutoGeneratedPropertiesEquals(Uitvoeringsregel expected, Uitvoeringsregel actual) {
        assertThat(expected)
            .as("Verify Uitvoeringsregel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoeringsregelUpdatableFieldsEquals(Uitvoeringsregel expected, Uitvoeringsregel actual) {
        assertThat(expected)
            .as("Verify Uitvoeringsregel relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getRegel()).as("check regel").isEqualTo(actual.getRegel()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoeringsregelUpdatableRelationshipsEquals(Uitvoeringsregel expected, Uitvoeringsregel actual) {}
}
