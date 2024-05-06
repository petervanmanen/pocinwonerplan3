package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SbiactiviteitAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSbiactiviteitAllPropertiesEquals(Sbiactiviteit expected, Sbiactiviteit actual) {
        assertSbiactiviteitAutoGeneratedPropertiesEquals(expected, actual);
        assertSbiactiviteitAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSbiactiviteitAllUpdatablePropertiesEquals(Sbiactiviteit expected, Sbiactiviteit actual) {
        assertSbiactiviteitUpdatableFieldsEquals(expected, actual);
        assertSbiactiviteitUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSbiactiviteitAutoGeneratedPropertiesEquals(Sbiactiviteit expected, Sbiactiviteit actual) {
        assertThat(expected)
            .as("Verify Sbiactiviteit auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSbiactiviteitUpdatableFieldsEquals(Sbiactiviteit expected, Sbiactiviteit actual) {
        assertThat(expected)
            .as("Verify Sbiactiviteit relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getDatumeindesbiactiviteit())
                        .as("check datumeindesbiactiviteit")
                        .isEqualTo(actual.getDatumeindesbiactiviteit())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumingangsbiactiviteit())
                        .as("check datumingangsbiactiviteit")
                        .isEqualTo(actual.getDatumingangsbiactiviteit())
            )
            .satisfies(e -> assertThat(e.getHoofdniveau()).as("check hoofdniveau").isEqualTo(actual.getHoofdniveau()))
            .satisfies(
                e ->
                    assertThat(e.getHoofdniveauomschrijving())
                        .as("check hoofdniveauomschrijving")
                        .isEqualTo(actual.getHoofdniveauomschrijving())
            )
            .satisfies(e -> assertThat(e.getNaamactiviteit()).as("check naamactiviteit").isEqualTo(actual.getNaamactiviteit()))
            .satisfies(e -> assertThat(e.getSbicode()).as("check sbicode").isEqualTo(actual.getSbicode()))
            .satisfies(e -> assertThat(e.getSbigroep()).as("check sbigroep").isEqualTo(actual.getSbigroep()))
            .satisfies(
                e -> assertThat(e.getSbigroepomschrijving()).as("check sbigroepomschrijving").isEqualTo(actual.getSbigroepomschrijving())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSbiactiviteitUpdatableRelationshipsEquals(Sbiactiviteit expected, Sbiactiviteit actual) {}
}
