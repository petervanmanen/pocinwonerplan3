package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class KadastraleonroerendezaakAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastraleonroerendezaakAllPropertiesEquals(
        Kadastraleonroerendezaak expected,
        Kadastraleonroerendezaak actual
    ) {
        assertKadastraleonroerendezaakAutoGeneratedPropertiesEquals(expected, actual);
        assertKadastraleonroerendezaakAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastraleonroerendezaakAllUpdatablePropertiesEquals(
        Kadastraleonroerendezaak expected,
        Kadastraleonroerendezaak actual
    ) {
        assertKadastraleonroerendezaakUpdatableFieldsEquals(expected, actual);
        assertKadastraleonroerendezaakUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastraleonroerendezaakAutoGeneratedPropertiesEquals(
        Kadastraleonroerendezaak expected,
        Kadastraleonroerendezaak actual
    ) {
        assertThat(expected)
            .as("Verify Kadastraleonroerendezaak auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastraleonroerendezaakUpdatableFieldsEquals(
        Kadastraleonroerendezaak expected,
        Kadastraleonroerendezaak actual
    ) {
        assertThat(expected)
            .as("Verify Kadastraleonroerendezaak relevant properties")
            .satisfies(e -> assertThat(e.getEmpty()).as("check empty").isEqualTo(actual.getEmpty()))
            .satisfies(
                e ->
                    assertThat(e.getAppartementsrechtvolgnummer())
                        .as("check appartementsrechtvolgnummer")
                        .isEqualTo(actual.getAppartementsrechtvolgnummer())
            )
            .satisfies(e -> assertThat(e.getBegrenzing()).as("check begrenzing").isEqualTo(actual.getBegrenzing()))
            .satisfies(
                e -> assertThat(e.getCultuurcodeonbebouwd()).as("check cultuurcodeonbebouwd").isEqualTo(actual.getCultuurcodeonbebouwd())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidkadastraleonroerendezaak())
                        .as("check datumbegingeldigheidkadastraleonroerendezaak")
                        .isEqualTo(actual.getDatumbegingeldigheidkadastraleonroerendezaak())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidkadastraleonroerendezaak())
                        .as("check datumeindegeldigheidkadastraleonroerendezaak")
                        .isEqualTo(actual.getDatumeindegeldigheidkadastraleonroerendezaak())
            )
            .satisfies(e -> assertThat(e.getIdentificatie()).as("check identificatie").isEqualTo(actual.getIdentificatie()))
            .satisfies(e -> assertThat(e.getKadastralegemeente()).as("check kadastralegemeente").isEqualTo(actual.getKadastralegemeente()))
            .satisfies(
                e ->
                    assertThat(e.getKadastralegemeentecode())
                        .as("check kadastralegemeentecode")
                        .isEqualTo(actual.getKadastralegemeentecode())
            )
            .satisfies(e -> assertThat(e.getKoopjaar()).as("check koopjaar").isEqualTo(actual.getKoopjaar()))
            .satisfies(
                e -> assertThat(e.getKoopsom()).as("check koopsom").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getKoopsom())
            )
            .satisfies(
                e ->
                    assertThat(e.getLandinrichtingrentebedrag())
                        .as("check landinrichtingrentebedrag")
                        .usingComparator(bigDecimalCompareTo)
                        .isEqualTo(actual.getLandinrichtingrentebedrag())
            )
            .satisfies(
                e ->
                    assertThat(e.getLandinrichtingrenteeindejaar())
                        .as("check landinrichtingrenteeindejaar")
                        .isEqualTo(actual.getLandinrichtingrenteeindejaar())
            )
            .satisfies(e -> assertThat(e.getLigging()).as("check ligging").isEqualTo(actual.getLigging()))
            .satisfies(
                e -> assertThat(e.getLocatieomschrijving()).as("check locatieomschrijving").isEqualTo(actual.getLocatieomschrijving())
            )
            .satisfies(e -> assertThat(e.getOppervlakte()).as("check oppervlakte").isEqualTo(actual.getOppervlakte()))
            .satisfies(e -> assertThat(e.getOud()).as("check oud").isEqualTo(actual.getOud()))
            .satisfies(e -> assertThat(e.getPerceelnummer()).as("check perceelnummer").isEqualTo(actual.getPerceelnummer()))
            .satisfies(e -> assertThat(e.getSectie()).as("check sectie").isEqualTo(actual.getSectie()))
            .satisfies(e -> assertThat(e.getValutacode()).as("check valutacode").isEqualTo(actual.getValutacode()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastraleonroerendezaakUpdatableRelationshipsEquals(
        Kadastraleonroerendezaak expected,
        Kadastraleonroerendezaak actual
    ) {}
}