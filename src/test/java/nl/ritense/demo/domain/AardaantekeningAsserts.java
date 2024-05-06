package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AardaantekeningAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAardaantekeningAllPropertiesEquals(Aardaantekening expected, Aardaantekening actual) {
        assertAardaantekeningAutoGeneratedPropertiesEquals(expected, actual);
        assertAardaantekeningAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAardaantekeningAllUpdatablePropertiesEquals(Aardaantekening expected, Aardaantekening actual) {
        assertAardaantekeningUpdatableFieldsEquals(expected, actual);
        assertAardaantekeningUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAardaantekeningAutoGeneratedPropertiesEquals(Aardaantekening expected, Aardaantekening actual) {
        assertThat(expected)
            .as("Verify Aardaantekening auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAardaantekeningUpdatableFieldsEquals(Aardaantekening expected, Aardaantekening actual) {
        assertThat(expected)
            .as("Verify Aardaantekening relevant properties")
            .satisfies(
                e -> assertThat(e.getCodeaardaantekening()).as("check codeaardaantekening").isEqualTo(actual.getCodeaardaantekening())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidaardaantekening())
                        .as("check datumbegingeldigheidaardaantekening")
                        .isEqualTo(actual.getDatumbegingeldigheidaardaantekening())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidaardaantekening())
                        .as("check datumeindegeldigheidaardaantekening")
                        .isEqualTo(actual.getDatumeindegeldigheidaardaantekening())
            )
            .satisfies(
                e -> assertThat(e.getNaamaardaantekening()).as("check naamaardaantekening").isEqualTo(actual.getNaamaardaantekening())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAardaantekeningUpdatableRelationshipsEquals(Aardaantekening expected, Aardaantekening actual) {}
}
