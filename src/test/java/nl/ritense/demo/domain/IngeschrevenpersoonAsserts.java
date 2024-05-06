package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class IngeschrevenpersoonAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIngeschrevenpersoonAllPropertiesEquals(Ingeschrevenpersoon expected, Ingeschrevenpersoon actual) {
        assertIngeschrevenpersoonAutoGeneratedPropertiesEquals(expected, actual);
        assertIngeschrevenpersoonAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIngeschrevenpersoonAllUpdatablePropertiesEquals(Ingeschrevenpersoon expected, Ingeschrevenpersoon actual) {
        assertIngeschrevenpersoonUpdatableFieldsEquals(expected, actual);
        assertIngeschrevenpersoonUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIngeschrevenpersoonAutoGeneratedPropertiesEquals(Ingeschrevenpersoon expected, Ingeschrevenpersoon actual) {
        assertThat(expected)
            .as("Verify Ingeschrevenpersoon auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIngeschrevenpersoonUpdatableFieldsEquals(Ingeschrevenpersoon expected, Ingeschrevenpersoon actual) {
        assertThat(expected)
            .as("Verify Ingeschrevenpersoon relevant properties")
            .satisfies(e -> assertThat(e.getAdresherkomst()).as("check adresherkomst").isEqualTo(actual.getAdresherkomst()))
            .satisfies(e -> assertThat(e.getAnummer()).as("check anummer").isEqualTo(actual.getAnummer()))
            .satisfies(
                e -> assertThat(e.getBeschrijvinglocatie()).as("check beschrijvinglocatie").isEqualTo(actual.getBeschrijvinglocatie())
            )
            .satisfies(
                e ->
                    assertThat(e.getBuitenlandsreisdocument())
                        .as("check buitenlandsreisdocument")
                        .isEqualTo(actual.getBuitenlandsreisdocument())
            )
            .satisfies(e -> assertThat(e.getBurgerlijkestaat()).as("check burgerlijkestaat").isEqualTo(actual.getBurgerlijkestaat()))
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidverblijfplaats())
                        .as("check datumbegingeldigheidverblijfplaats")
                        .isEqualTo(actual.getDatumbegingeldigheidverblijfplaats())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidverblijfsplaats())
                        .as("check datumeindegeldigheidverblijfsplaats")
                        .isEqualTo(actual.getDatumeindegeldigheidverblijfsplaats())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatuminschrijvinggemeente())
                        .as("check datuminschrijvinggemeente")
                        .isEqualTo(actual.getDatuminschrijvinggemeente())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumopschortingbijhouding())
                        .as("check datumopschortingbijhouding")
                        .isEqualTo(actual.getDatumopschortingbijhouding())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumvertrekuitnederland())
                        .as("check datumvertrekuitnederland")
                        .isEqualTo(actual.getDatumvertrekuitnederland())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumvestigingnederland())
                        .as("check datumvestigingnederland")
                        .isEqualTo(actual.getDatumvestigingnederland())
            )
            .satisfies(
                e ->
                    assertThat(e.getGemeentevaninschrijving())
                        .as("check gemeentevaninschrijving")
                        .isEqualTo(actual.getGemeentevaninschrijving())
            )
            .satisfies(e -> assertThat(e.getGezinsrelatie()).as("check gezinsrelatie").isEqualTo(actual.getGezinsrelatie()))
            .satisfies(e -> assertThat(e.getIndicatiegeheim()).as("check indicatiegeheim").isEqualTo(actual.getIndicatiegeheim()))
            .satisfies(e -> assertThat(e.getIngezetene()).as("check ingezetene").isEqualTo(actual.getIngezetene()))
            .satisfies(
                e ->
                    assertThat(e.getLandwaarnaarvertrokken())
                        .as("check landwaarnaarvertrokken")
                        .isEqualTo(actual.getLandwaarnaarvertrokken())
            )
            .satisfies(
                e ->
                    assertThat(e.getLandwaarvandaaningeschreven())
                        .as("check landwaarvandaaningeschreven")
                        .isEqualTo(actual.getLandwaarvandaaningeschreven())
            )
            .satisfies(e -> assertThat(e.getOuder1()).as("check ouder1").isEqualTo(actual.getOuder1()))
            .satisfies(e -> assertThat(e.getOuder2()).as("check ouder2").isEqualTo(actual.getOuder2()))
            .satisfies(e -> assertThat(e.getPartnerid()).as("check partnerid").isEqualTo(actual.getPartnerid()))
            .satisfies(e -> assertThat(e.getRedeneindebewoning()).as("check redeneindebewoning").isEqualTo(actual.getRedeneindebewoning()))
            .satisfies(
                e ->
                    assertThat(e.getRedenopschortingbijhouding())
                        .as("check redenopschortingbijhouding")
                        .isEqualTo(actual.getRedenopschortingbijhouding())
            )
            .satisfies(
                e ->
                    assertThat(e.getSignaleringreisdocument())
                        .as("check signaleringreisdocument")
                        .isEqualTo(actual.getSignaleringreisdocument())
            )
            .satisfies(e -> assertThat(e.getVerblijfstitel()).as("check verblijfstitel").isEqualTo(actual.getVerblijfstitel()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertIngeschrevenpersoonUpdatableRelationshipsEquals(Ingeschrevenpersoon expected, Ingeschrevenpersoon actual) {}
}
