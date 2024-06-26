package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VerblijfsobjectAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfsobjectAllPropertiesEquals(Verblijfsobject expected, Verblijfsobject actual) {
        assertVerblijfsobjectAutoGeneratedPropertiesEquals(expected, actual);
        assertVerblijfsobjectAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfsobjectAllUpdatablePropertiesEquals(Verblijfsobject expected, Verblijfsobject actual) {
        assertVerblijfsobjectUpdatableFieldsEquals(expected, actual);
        assertVerblijfsobjectUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfsobjectAutoGeneratedPropertiesEquals(Verblijfsobject expected, Verblijfsobject actual) {
        assertThat(expected)
            .as("Verify Verblijfsobject auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfsobjectUpdatableFieldsEquals(Verblijfsobject expected, Verblijfsobject actual) {
        assertThat(expected)
            .as("Verify Verblijfsobject relevant properties")
            .satisfies(e -> assertThat(e.getAantalkamers()).as("check aantalkamers").isEqualTo(actual.getAantalkamers()))
            .satisfies(
                e -> assertThat(e.getDatumbegingeldigheid()).as("check datumbegingeldigheid").isEqualTo(actual.getDatumbegingeldigheid())
            )
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(
                e -> assertThat(e.getDatumeindegeldigheid()).as("check datumeindegeldigheid").isEqualTo(actual.getDatumeindegeldigheid())
            )
            .satisfies(e -> assertThat(e.getDatumingang()).as("check datumingang").isEqualTo(actual.getDatumingang()))
            .satisfies(e -> assertThat(e.getDocumentdatum()).as("check documentdatum").isEqualTo(actual.getDocumentdatum()))
            .satisfies(e -> assertThat(e.getDocumentnr()).as("check documentnr").isEqualTo(actual.getDocumentnr()))
            .satisfies(e -> assertThat(e.getGebruiksdoel()).as("check gebruiksdoel").isEqualTo(actual.getGebruiksdoel()))
            .satisfies(e -> assertThat(e.getGeconstateerd()).as("check geconstateerd").isEqualTo(actual.getGeconstateerd()))
            .satisfies(e -> assertThat(e.getGeometrie()).as("check geometrie").isEqualTo(actual.getGeometrie()))
            .satisfies(
                e ->
                    assertThat(e.getHoogstebouwlaagverblijfsobject())
                        .as("check hoogstebouwlaagverblijfsobject")
                        .isEqualTo(actual.getHoogstebouwlaagverblijfsobject())
            )
            .satisfies(e -> assertThat(e.getIdentificatie()).as("check identificatie").isEqualTo(actual.getIdentificatie()))
            .satisfies(e -> assertThat(e.getInonderzoek()).as("check inonderzoek").isEqualTo(actual.getInonderzoek()))
            .satisfies(
                e ->
                    assertThat(e.getLaagstebouwlaagverblijfsobject())
                        .as("check laagstebouwlaagverblijfsobject")
                        .isEqualTo(actual.getLaagstebouwlaagverblijfsobject())
            )
            .satisfies(
                e -> assertThat(e.getOntsluitingverdieping()).as("check ontsluitingverdieping").isEqualTo(actual.getOntsluitingverdieping())
            )
            .satisfies(e -> assertThat(e.getSoortwoonobject()).as("check soortwoonobject").isEqualTo(actual.getSoortwoonobject()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(
                e ->
                    assertThat(e.getToegangbouwlaagverblijfsobject())
                        .as("check toegangbouwlaagverblijfsobject")
                        .isEqualTo(actual.getToegangbouwlaagverblijfsobject())
            )
            .satisfies(e -> assertThat(e.getVersie()).as("check versie").isEqualTo(actual.getVersie()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfsobjectUpdatableRelationshipsEquals(Verblijfsobject expected, Verblijfsobject actual) {
        assertThat(expected)
            .as("Verify Verblijfsobject relationships")
            .satisfies(
                e -> assertThat(e.getHeeftVastgoedobject()).as("check heeftVastgoedobject").isEqualTo(actual.getHeeftVastgoedobject())
            )
            .satisfies(
                e -> assertThat(e.getMaaktdeeluitvanPands()).as("check maaktdeeluitvanPands").isEqualTo(actual.getMaaktdeeluitvanPands())
            );
    }
}
