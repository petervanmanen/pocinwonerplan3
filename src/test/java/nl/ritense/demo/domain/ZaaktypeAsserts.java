package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ZaaktypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertZaaktypeAllPropertiesEquals(Zaaktype expected, Zaaktype actual) {
        assertZaaktypeAutoGeneratedPropertiesEquals(expected, actual);
        assertZaaktypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertZaaktypeAllUpdatablePropertiesEquals(Zaaktype expected, Zaaktype actual) {
        assertZaaktypeUpdatableFieldsEquals(expected, actual);
        assertZaaktypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertZaaktypeAutoGeneratedPropertiesEquals(Zaaktype expected, Zaaktype actual) {
        assertThat(expected)
            .as("Verify Zaaktype auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertZaaktypeUpdatableFieldsEquals(Zaaktype expected, Zaaktype actual) {
        assertThat(expected)
            .as("Verify Zaaktype relevant properties")
            .satisfies(e -> assertThat(e.getArchiefcode()).as("check archiefcode").isEqualTo(actual.getArchiefcode()))
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidzaaktype())
                        .as("check datumbegingeldigheidzaaktype")
                        .isEqualTo(actual.getDatumbegingeldigheidzaaktype())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidzaaktype())
                        .as("check datumeindegeldigheidzaaktype")
                        .isEqualTo(actual.getDatumeindegeldigheidzaaktype())
            )
            .satisfies(
                e ->
                    assertThat(e.getDoorlooptijdbehandeling())
                        .as("check doorlooptijdbehandeling")
                        .isEqualTo(actual.getDoorlooptijdbehandeling())
            )
            .satisfies(
                e -> assertThat(e.getIndicatiepublicatie()).as("check indicatiepublicatie").isEqualTo(actual.getIndicatiepublicatie())
            )
            .satisfies(e -> assertThat(e.getPublicatietekst()).as("check publicatietekst").isEqualTo(actual.getPublicatietekst()))
            .satisfies(
                e ->
                    assertThat(e.getServicenormbehandeling())
                        .as("check servicenormbehandeling")
                        .isEqualTo(actual.getServicenormbehandeling())
            )
            .satisfies(e -> assertThat(e.getTrefwoord()).as("check trefwoord").isEqualTo(actual.getTrefwoord()))
            .satisfies(
                e ->
                    assertThat(e.getVertrouwelijkaanduiding())
                        .as("check vertrouwelijkaanduiding")
                        .isEqualTo(actual.getVertrouwelijkaanduiding())
            )
            .satisfies(e -> assertThat(e.getZaakcategorie()).as("check zaakcategorie").isEqualTo(actual.getZaakcategorie()))
            .satisfies(
                e -> assertThat(e.getZaaktypeomschrijving()).as("check zaaktypeomschrijving").isEqualTo(actual.getZaaktypeomschrijving())
            )
            .satisfies(
                e ->
                    assertThat(e.getZaaktypeomschrijvinggeneriek())
                        .as("check zaaktypeomschrijvinggeneriek")
                        .isEqualTo(actual.getZaaktypeomschrijvinggeneriek())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertZaaktypeUpdatableRelationshipsEquals(Zaaktype expected, Zaaktype actual) {
        assertThat(expected)
            .as("Verify Zaaktype relationships")
            .satisfies(e -> assertThat(e.getHeeftProducttype()).as("check heeftProducttype").isEqualTo(actual.getHeeftProducttype()))
            .satisfies(e -> assertThat(e.getBetreftProduct()).as("check betreftProduct").isEqualTo(actual.getBetreftProduct()))
            .satisfies(
                e ->
                    assertThat(e.getHeeftBedrijfsprocestype())
                        .as("check heeftBedrijfsprocestype")
                        .isEqualTo(actual.getHeeftBedrijfsprocestype())
            )
            .satisfies(
                e ->
                    assertThat(e.getIsverantwoordelijkevoorMedewerker())
                        .as("check isverantwoordelijkevoorMedewerker")
                        .isEqualTo(actual.getIsverantwoordelijkevoorMedewerker())
            )
            .satisfies(
                e ->
                    assertThat(e.getIsaanleidingvoorFormuliersoorts())
                        .as("check isaanleidingvoorFormuliersoorts")
                        .isEqualTo(actual.getIsaanleidingvoorFormuliersoorts())
            );
    }
}
