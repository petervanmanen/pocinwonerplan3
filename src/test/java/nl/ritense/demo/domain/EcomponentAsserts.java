package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class EcomponentAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEcomponentAllPropertiesEquals(Ecomponent expected, Ecomponent actual) {
        assertEcomponentAutoGeneratedPropertiesEquals(expected, actual);
        assertEcomponentAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEcomponentAllUpdatablePropertiesEquals(Ecomponent expected, Ecomponent actual) {
        assertEcomponentUpdatableFieldsEquals(expected, actual);
        assertEcomponentUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEcomponentAutoGeneratedPropertiesEquals(Ecomponent expected, Ecomponent actual) {
        assertThat(expected)
            .as("Verify Ecomponent auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEcomponentUpdatableFieldsEquals(Ecomponent expected, Ecomponent actual) {
        assertThat(expected)
            .as("Verify Ecomponent relevant properties")
            .satisfies(e -> assertThat(e.getBedrag()).as("check bedrag").isEqualTo(actual.getBedrag()))
            .satisfies(
                e ->
                    assertThat(e.getDatumbeginbetrekkingop())
                        .as("check datumbeginbetrekkingop")
                        .isEqualTo(actual.getDatumbeginbetrekkingop())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindebetrekkingop())
                        .as("check datumeindebetrekkingop")
                        .isEqualTo(actual.getDatumeindebetrekkingop())
            )
            .satisfies(e -> assertThat(e.getDebetcredit()).as("check debetcredit").isEqualTo(actual.getDebetcredit()))
            .satisfies(e -> assertThat(e.getGroep()).as("check groep").isEqualTo(actual.getGroep()))
            .satisfies(e -> assertThat(e.getGroepcode()).as("check groepcode").isEqualTo(actual.getGroepcode()))
            .satisfies(e -> assertThat(e.getGrootboekcode()).as("check grootboekcode").isEqualTo(actual.getGrootboekcode()))
            .satisfies(
                e -> assertThat(e.getGrootboekomschrijving()).as("check grootboekomschrijving").isEqualTo(actual.getGrootboekomschrijving())
            )
            .satisfies(e -> assertThat(e.getKostenplaats()).as("check kostenplaats").isEqualTo(actual.getKostenplaats()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getRekeningnummer()).as("check rekeningnummer").isEqualTo(actual.getRekeningnummer()))
            .satisfies(e -> assertThat(e.getToelichting()).as("check toelichting").isEqualTo(actual.getToelichting()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEcomponentUpdatableRelationshipsEquals(Ecomponent expected, Ecomponent actual) {}
}