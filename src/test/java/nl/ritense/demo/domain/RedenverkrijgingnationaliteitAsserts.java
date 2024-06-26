package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RedenverkrijgingnationaliteitAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRedenverkrijgingnationaliteitAllPropertiesEquals(
        Redenverkrijgingnationaliteit expected,
        Redenverkrijgingnationaliteit actual
    ) {
        assertRedenverkrijgingnationaliteitAutoGeneratedPropertiesEquals(expected, actual);
        assertRedenverkrijgingnationaliteitAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRedenverkrijgingnationaliteitAllUpdatablePropertiesEquals(
        Redenverkrijgingnationaliteit expected,
        Redenverkrijgingnationaliteit actual
    ) {
        assertRedenverkrijgingnationaliteitUpdatableFieldsEquals(expected, actual);
        assertRedenverkrijgingnationaliteitUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRedenverkrijgingnationaliteitAutoGeneratedPropertiesEquals(
        Redenverkrijgingnationaliteit expected,
        Redenverkrijgingnationaliteit actual
    ) {
        assertThat(expected)
            .as("Verify Redenverkrijgingnationaliteit auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRedenverkrijgingnationaliteitUpdatableFieldsEquals(
        Redenverkrijgingnationaliteit expected,
        Redenverkrijgingnationaliteit actual
    ) {
        assertThat(expected)
            .as("Verify Redenverkrijgingnationaliteit relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getDatumaanvanggeldigheidverkrijging())
                        .as("check datumaanvanggeldigheidverkrijging")
                        .isEqualTo(actual.getDatumaanvanggeldigheidverkrijging())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidverkrijging())
                        .as("check datumeindegeldigheidverkrijging")
                        .isEqualTo(actual.getDatumeindegeldigheidverkrijging())
            )
            .satisfies(
                e ->
                    assertThat(e.getOmschrijvingverkrijging())
                        .as("check omschrijvingverkrijging")
                        .isEqualTo(actual.getOmschrijvingverkrijging())
            )
            .satisfies(
                e ->
                    assertThat(e.getRedennummerverkrijging())
                        .as("check redennummerverkrijging")
                        .isEqualTo(actual.getRedennummerverkrijging())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRedenverkrijgingnationaliteitUpdatableRelationshipsEquals(
        Redenverkrijgingnationaliteit expected,
        Redenverkrijgingnationaliteit actual
    ) {}
}
