package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MilieustraatAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMilieustraatAllPropertiesEquals(Milieustraat expected, Milieustraat actual) {
        assertMilieustraatAutoGeneratedPropertiesEquals(expected, actual);
        assertMilieustraatAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMilieustraatAllUpdatablePropertiesEquals(Milieustraat expected, Milieustraat actual) {
        assertMilieustraatUpdatableFieldsEquals(expected, actual);
        assertMilieustraatUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMilieustraatAutoGeneratedPropertiesEquals(Milieustraat expected, Milieustraat actual) {
        assertThat(expected)
            .as("Verify Milieustraat auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMilieustraatUpdatableFieldsEquals(Milieustraat expected, Milieustraat actual) {
        assertThat(expected)
            .as("Verify Milieustraat relevant properties")
            .satisfies(e -> assertThat(e.getAdresaanduiding()).as("check adresaanduiding").isEqualTo(actual.getAdresaanduiding()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMilieustraatUpdatableRelationshipsEquals(Milieustraat expected, Milieustraat actual) {
        assertThat(expected)
            .as("Verify Milieustraat relationships")
            .satisfies(
                e ->
                    assertThat(e.getInzamelpuntvanFracties())
                        .as("check inzamelpuntvanFracties")
                        .isEqualTo(actual.getInzamelpuntvanFracties())
            )
            .satisfies(e -> assertThat(e.getGeldigvoorPas()).as("check geldigvoorPas").isEqualTo(actual.getGeldigvoorPas()));
    }
}
