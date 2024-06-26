package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PartijAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartijAllPropertiesEquals(Partij expected, Partij actual) {
        assertPartijAutoGeneratedPropertiesEquals(expected, actual);
        assertPartijAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartijAllUpdatablePropertiesEquals(Partij expected, Partij actual) {
        assertPartijUpdatableFieldsEquals(expected, actual);
        assertPartijUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartijAutoGeneratedPropertiesEquals(Partij expected, Partij actual) {
        assertThat(expected)
            .as("Verify Partij auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartijUpdatableFieldsEquals(Partij expected, Partij actual) {
        assertThat(expected)
            .as("Verify Partij relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(
                e ->
                    assertThat(e.getDatumaanvanggeldigheidpartij())
                        .as("check datumaanvanggeldigheidpartij")
                        .isEqualTo(actual.getDatumaanvanggeldigheidpartij())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidpartij())
                        .as("check datumeindegeldigheidpartij")
                        .isEqualTo(actual.getDatumeindegeldigheidpartij())
            )
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getSoort()).as("check soort").isEqualTo(actual.getSoort()))
            .satisfies(
                e ->
                    assertThat(e.getVerstrekkingsbeperkingmogelijk())
                        .as("check verstrekkingsbeperkingmogelijk")
                        .isEqualTo(actual.getVerstrekkingsbeperkingmogelijk())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPartijUpdatableRelationshipsEquals(Partij expected, Partij actual) {}
}
