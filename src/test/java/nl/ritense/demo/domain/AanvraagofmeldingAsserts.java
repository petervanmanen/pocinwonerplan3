package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AanvraagofmeldingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAanvraagofmeldingAllPropertiesEquals(Aanvraagofmelding expected, Aanvraagofmelding actual) {
        assertAanvraagofmeldingAutoGeneratedPropertiesEquals(expected, actual);
        assertAanvraagofmeldingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAanvraagofmeldingAllUpdatablePropertiesEquals(Aanvraagofmelding expected, Aanvraagofmelding actual) {
        assertAanvraagofmeldingUpdatableFieldsEquals(expected, actual);
        assertAanvraagofmeldingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAanvraagofmeldingAutoGeneratedPropertiesEquals(Aanvraagofmelding expected, Aanvraagofmelding actual) {
        assertThat(expected)
            .as("Verify Aanvraagofmelding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAanvraagofmeldingUpdatableFieldsEquals(Aanvraagofmelding expected, Aanvraagofmelding actual) {
        assertThat(expected)
            .as("Verify Aanvraagofmelding relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()))
            .satisfies(e -> assertThat(e.getOpmerkingen()).as("check opmerkingen").isEqualTo(actual.getOpmerkingen()))
            .satisfies(e -> assertThat(e.getReden()).as("check reden").isEqualTo(actual.getReden()))
            .satisfies(
                e ->
                    assertThat(e.getSoortverzuimofaanvraag())
                        .as("check soortverzuimofaanvraag")
                        .isEqualTo(actual.getSoortverzuimofaanvraag())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAanvraagofmeldingUpdatableRelationshipsEquals(Aanvraagofmelding expected, Aanvraagofmelding actual) {}
}
