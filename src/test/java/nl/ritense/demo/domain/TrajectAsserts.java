package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TrajectAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectAllPropertiesEquals(Traject expected, Traject actual) {
        assertTrajectAutoGeneratedPropertiesEquals(expected, actual);
        assertTrajectAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectAllUpdatablePropertiesEquals(Traject expected, Traject actual) {
        assertTrajectUpdatableFieldsEquals(expected, actual);
        assertTrajectUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectAutoGeneratedPropertiesEquals(Traject expected, Traject actual) {
        assertThat(expected)
            .as("Verify Traject auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectUpdatableFieldsEquals(Traject expected, Traject actual) {
        assertThat(expected)
            .as("Verify Traject relevant properties")
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()))
            .satisfies(e -> assertThat(e.getDatumtoekenning()).as("check datumtoekenning").isEqualTo(actual.getDatumtoekenning()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getResultaat()).as("check resultaat").isEqualTo(actual.getResultaat()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrajectUpdatableRelationshipsEquals(Traject expected, Traject actual) {
        assertThat(expected)
            .as("Verify Traject relationships")
            .satisfies(
                e ->
                    assertThat(e.getHeeftresultaatResultaat())
                        .as("check heeftresultaatResultaat")
                        .isEqualTo(actual.getHeeftresultaatResultaat())
            )
            .satisfies(
                e ->
                    assertThat(e.getHeeftuitstroomredenUitstroomreden())
                        .as("check heeftuitstroomredenUitstroomreden")
                        .isEqualTo(actual.getHeeftuitstroomredenUitstroomreden())
            )
            .satisfies(
                e ->
                    assertThat(e.getIstrajectsoortTrajectsoort())
                        .as("check istrajectsoortTrajectsoort")
                        .isEqualTo(actual.getIstrajectsoortTrajectsoort())
            )
            .satisfies(
                e ->
                    assertThat(e.getHeeftparticipatietrajectClient())
                        .as("check heeftparticipatietrajectClient")
                        .isEqualTo(actual.getHeeftparticipatietrajectClient())
            )
            .satisfies(e -> assertThat(e.getHeefttrajectClient()).as("check heefttrajectClient").isEqualTo(actual.getHeefttrajectClient()));
    }
}