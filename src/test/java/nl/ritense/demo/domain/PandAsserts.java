package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PandAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPandAllPropertiesEquals(Pand expected, Pand actual) {
        assertPandAutoGeneratedPropertiesEquals(expected, actual);
        assertPandAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPandAllUpdatablePropertiesEquals(Pand expected, Pand actual) {
        assertPandUpdatableFieldsEquals(expected, actual);
        assertPandUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPandAutoGeneratedPropertiesEquals(Pand expected, Pand actual) {
        assertThat(expected)
            .as("Verify Pand auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPandUpdatableFieldsEquals(Pand expected, Pand actual) {
        assertThat(expected)
            .as("Verify Pand relevant properties")
            .satisfies(e -> assertThat(e.getBrutoinhoudpand()).as("check brutoinhoudpand").isEqualTo(actual.getBrutoinhoudpand()))
            .satisfies(
                e -> assertThat(e.getDatumbegingeldigheid()).as("check datumbegingeldigheid").isEqualTo(actual.getDatumbegingeldigheid())
            )
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(
                e -> assertThat(e.getDatumeindegeldigheid()).as("check datumeindegeldigheid").isEqualTo(actual.getDatumeindegeldigheid())
            )
            .satisfies(e -> assertThat(e.getDatumingang()).as("check datumingang").isEqualTo(actual.getDatumingang()))
            .satisfies(e -> assertThat(e.getGeconstateerd()).as("check geconstateerd").isEqualTo(actual.getGeconstateerd()))
            .satisfies(
                e ->
                    assertThat(e.getGeometriebovenaanzicht())
                        .as("check geometriebovenaanzicht")
                        .isEqualTo(actual.getGeometriebovenaanzicht())
            )
            .satisfies(e -> assertThat(e.getGeometriemaaiveld()).as("check geometriemaaiveld").isEqualTo(actual.getGeometriemaaiveld()))
            .satisfies(e -> assertThat(e.getGeometriepunt()).as("check geometriepunt").isEqualTo(actual.getGeometriepunt()))
            .satisfies(
                e -> assertThat(e.getHoogstebouwlaagpand()).as("check hoogstebouwlaagpand").isEqualTo(actual.getHoogstebouwlaagpand())
            )
            .satisfies(e -> assertThat(e.getIdentificatie()).as("check identificatie").isEqualTo(actual.getIdentificatie()))
            .satisfies(e -> assertThat(e.getInonderzoek()).as("check inonderzoek").isEqualTo(actual.getInonderzoek()))
            .satisfies(
                e -> assertThat(e.getLaagstebouwlaagpand()).as("check laagstebouwlaagpand").isEqualTo(actual.getLaagstebouwlaagpand())
            )
            .satisfies(
                e ->
                    assertThat(e.getOorspronkelijkbouwjaar())
                        .as("check oorspronkelijkbouwjaar")
                        .isEqualTo(actual.getOorspronkelijkbouwjaar())
            )
            .satisfies(e -> assertThat(e.getOppervlakte()).as("check oppervlakte").isEqualTo(actual.getOppervlakte()))
            .satisfies(
                e ->
                    assertThat(e.getRelatievehoogteliggingpand())
                        .as("check relatievehoogteliggingpand")
                        .isEqualTo(actual.getRelatievehoogteliggingpand())
            )
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(
                e -> assertThat(e.getStatusvoortgangbouw()).as("check statusvoortgangbouw").isEqualTo(actual.getStatusvoortgangbouw())
            )
            .satisfies(e -> assertThat(e.getVersie()).as("check versie").isEqualTo(actual.getVersie()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPandUpdatableRelationshipsEquals(Pand expected, Pand actual) {
        assertThat(expected)
            .as("Verify Pand relationships")
            .satisfies(
                e -> assertThat(e.getHeeftVastgoedobject()).as("check heeftVastgoedobject").isEqualTo(actual.getHeeftVastgoedobject())
            )
            .satisfies(
                e ->
                    assertThat(e.getZonderverblijfsobjectligtinBuurt())
                        .as("check zonderverblijfsobjectligtinBuurt")
                        .isEqualTo(actual.getZonderverblijfsobjectligtinBuurt())
            )
            .satisfies(
                e ->
                    assertThat(e.getMaaktdeeluitvanVerblijfsobjects())
                        .as("check maaktdeeluitvanVerblijfsobjects")
                        .isEqualTo(actual.getMaaktdeeluitvanVerblijfsobjects())
            );
    }
}
