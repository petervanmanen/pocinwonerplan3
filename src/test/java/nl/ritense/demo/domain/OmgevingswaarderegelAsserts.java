package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OmgevingswaarderegelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaarderegelAllPropertiesEquals(Omgevingswaarderegel expected, Omgevingswaarderegel actual) {
        assertOmgevingswaarderegelAutoGeneratedPropertiesEquals(expected, actual);
        assertOmgevingswaarderegelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaarderegelAllUpdatablePropertiesEquals(Omgevingswaarderegel expected, Omgevingswaarderegel actual) {
        assertOmgevingswaarderegelUpdatableFieldsEquals(expected, actual);
        assertOmgevingswaarderegelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaarderegelAutoGeneratedPropertiesEquals(Omgevingswaarderegel expected, Omgevingswaarderegel actual) {
        assertThat(expected)
            .as("Verify Omgevingswaarderegel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaarderegelUpdatableFieldsEquals(Omgevingswaarderegel expected, Omgevingswaarderegel actual) {
        assertThat(expected)
            .as("Verify Omgevingswaarderegel relevant properties")
            .satisfies(e -> assertThat(e.getGroep()).as("check groep").isEqualTo(actual.getGroep()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaarderegelUpdatableRelationshipsEquals(Omgevingswaarderegel expected, Omgevingswaarderegel actual) {
        assertThat(expected)
            .as("Verify Omgevingswaarderegel relationships")
            .satisfies(
                e ->
                    assertThat(e.getBeschrijftOmgevingsnorms())
                        .as("check beschrijftOmgevingsnorms")
                        .isEqualTo(actual.getBeschrijftOmgevingsnorms())
            )
            .satisfies(
                e ->
                    assertThat(e.getBeschrijftOmgevingswaardes())
                        .as("check beschrijftOmgevingswaardes")
                        .isEqualTo(actual.getBeschrijftOmgevingswaardes())
            );
    }
}