package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OmgevingswaardeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaardeAllPropertiesEquals(Omgevingswaarde expected, Omgevingswaarde actual) {
        assertOmgevingswaardeAutoGeneratedPropertiesEquals(expected, actual);
        assertOmgevingswaardeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaardeAllUpdatablePropertiesEquals(Omgevingswaarde expected, Omgevingswaarde actual) {
        assertOmgevingswaardeUpdatableFieldsEquals(expected, actual);
        assertOmgevingswaardeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaardeAutoGeneratedPropertiesEquals(Omgevingswaarde expected, Omgevingswaarde actual) {
        assertThat(expected)
            .as("Verify Omgevingswaarde auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaardeUpdatableFieldsEquals(Omgevingswaarde expected, Omgevingswaarde actual) {
        assertThat(expected)
            .as("Verify Omgevingswaarde relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(
                e -> assertThat(e.getOmgevingswaardegroep()).as("check omgevingswaardegroep").isEqualTo(actual.getOmgevingswaardegroep())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOmgevingswaardeUpdatableRelationshipsEquals(Omgevingswaarde expected, Omgevingswaarde actual) {
        assertThat(expected)
            .as("Verify Omgevingswaarde relationships")
            .satisfies(
                e ->
                    assertThat(e.getBeschrijftOmgevingswaarderegels())
                        .as("check beschrijftOmgevingswaarderegels")
                        .isEqualTo(actual.getBeschrijftOmgevingswaarderegels())
            );
    }
}
