package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VegetatieobjectAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVegetatieobjectAllPropertiesEquals(Vegetatieobject expected, Vegetatieobject actual) {
        assertVegetatieobjectAutoGeneratedPropertiesEquals(expected, actual);
        assertVegetatieobjectAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVegetatieobjectAllUpdatablePropertiesEquals(Vegetatieobject expected, Vegetatieobject actual) {
        assertVegetatieobjectUpdatableFieldsEquals(expected, actual);
        assertVegetatieobjectUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVegetatieobjectAutoGeneratedPropertiesEquals(Vegetatieobject expected, Vegetatieobject actual) {
        assertThat(expected)
            .as("Verify Vegetatieobject auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVegetatieobjectUpdatableFieldsEquals(Vegetatieobject expected, Vegetatieobject actual) {
        assertThat(expected)
            .as("Verify Vegetatieobject relevant properties")
            .satisfies(e -> assertThat(e.getAfvoeren()).as("check afvoeren").isEqualTo(actual.getAfvoeren()))
            .satisfies(e -> assertThat(e.getBereikbaarheid()).as("check bereikbaarheid").isEqualTo(actual.getBereikbaarheid()))
            .satisfies(e -> assertThat(e.getEcologischbeheer()).as("check ecologischbeheer").isEqualTo(actual.getEcologischbeheer()))
            .satisfies(
                e ->
                    assertThat(e.getKwaliteitsniveauactueel())
                        .as("check kwaliteitsniveauactueel")
                        .isEqualTo(actual.getKwaliteitsniveauactueel())
            )
            .satisfies(
                e ->
                    assertThat(e.getKwaliteitsniveaugewenst())
                        .as("check kwaliteitsniveaugewenst")
                        .isEqualTo(actual.getKwaliteitsniveaugewenst())
            )
            .satisfies(e -> assertThat(e.getKweker()).as("check kweker").isEqualTo(actual.getKweker()))
            .satisfies(e -> assertThat(e.getLeverancier()).as("check leverancier").isEqualTo(actual.getLeverancier()))
            .satisfies(e -> assertThat(e.getEobjectnummer()).as("check eobjectnummer").isEqualTo(actual.getEobjectnummer()))
            .satisfies(e -> assertThat(e.getSoortnaam()).as("check soortnaam").isEqualTo(actual.getSoortnaam()))
            .satisfies(e -> assertThat(e.getTypestandplaats()).as("check typestandplaats").isEqualTo(actual.getTypestandplaats()))
            .satisfies(
                e -> assertThat(e.getTypestandplaatsplus()).as("check typestandplaatsplus").isEqualTo(actual.getTypestandplaatsplus())
            )
            .satisfies(
                e ->
                    assertThat(e.getVegetatieobjectbereikbaarheidplus())
                        .as("check vegetatieobjectbereikbaarheidplus")
                        .isEqualTo(actual.getVegetatieobjectbereikbaarheidplus())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVegetatieobjectUpdatableRelationshipsEquals(Vegetatieobject expected, Vegetatieobject actual) {}
}
