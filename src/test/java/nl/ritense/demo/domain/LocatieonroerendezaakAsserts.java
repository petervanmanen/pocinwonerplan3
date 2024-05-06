package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LocatieonroerendezaakAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieonroerendezaakAllPropertiesEquals(Locatieonroerendezaak expected, Locatieonroerendezaak actual) {
        assertLocatieonroerendezaakAutoGeneratedPropertiesEquals(expected, actual);
        assertLocatieonroerendezaakAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieonroerendezaakAllUpdatablePropertiesEquals(
        Locatieonroerendezaak expected,
        Locatieonroerendezaak actual
    ) {
        assertLocatieonroerendezaakUpdatableFieldsEquals(expected, actual);
        assertLocatieonroerendezaakUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieonroerendezaakAutoGeneratedPropertiesEquals(
        Locatieonroerendezaak expected,
        Locatieonroerendezaak actual
    ) {
        assertThat(expected)
            .as("Verify Locatieonroerendezaak auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieonroerendezaakUpdatableFieldsEquals(Locatieonroerendezaak expected, Locatieonroerendezaak actual) {
        assertThat(expected)
            .as("Verify Locatieonroerendezaak relevant properties")
            .satisfies(e -> assertThat(e.getAdrestype()).as("check adrestype").isEqualTo(actual.getAdrestype()))
            .satisfies(e -> assertThat(e.getCultuurcodebebouwd()).as("check cultuurcodebebouwd").isEqualTo(actual.getCultuurcodebebouwd()))
            .satisfies(
                e -> assertThat(e.getDatumbegingeldigheid()).as("check datumbegingeldigheid").isEqualTo(actual.getDatumbegingeldigheid())
            )
            .satisfies(
                e -> assertThat(e.getDatumeindegeldigheid()).as("check datumeindegeldigheid").isEqualTo(actual.getDatumeindegeldigheid())
            )
            .satisfies(e -> assertThat(e.getGeometrie()).as("check geometrie").isEqualTo(actual.getGeometrie()))
            .satisfies(
                e -> assertThat(e.getLocatieomschrijving()).as("check locatieomschrijving").isEqualTo(actual.getLocatieomschrijving())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieonroerendezaakUpdatableRelationshipsEquals(
        Locatieonroerendezaak expected,
        Locatieonroerendezaak actual
    ) {}
}