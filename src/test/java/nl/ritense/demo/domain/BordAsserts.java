package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BordAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBordAllPropertiesEquals(Bord expected, Bord actual) {
        assertBordAutoGeneratedPropertiesEquals(expected, actual);
        assertBordAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBordAllUpdatablePropertiesEquals(Bord expected, Bord actual) {
        assertBordUpdatableFieldsEquals(expected, actual);
        assertBordUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBordAutoGeneratedPropertiesEquals(Bord expected, Bord actual) {
        assertThat(expected)
            .as("Verify Bord auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBordUpdatableFieldsEquals(Bord expected, Bord actual) {
        assertThat(expected)
            .as("Verify Bord relevant properties")
            .satisfies(e -> assertThat(e.getBreedte()).as("check breedte").isEqualTo(actual.getBreedte()))
            .satisfies(e -> assertThat(e.getDiameter()).as("check diameter").isEqualTo(actual.getDiameter()))
            .satisfies(e -> assertThat(e.getDrager()).as("check drager").isEqualTo(actual.getDrager()))
            .satisfies(e -> assertThat(e.getHoogte()).as("check hoogte").isEqualTo(actual.getHoogte()))
            .satisfies(
                e ->
                    assertThat(e.getJaaronderhouduitgevoerd())
                        .as("check jaaronderhouduitgevoerd")
                        .isEqualTo(actual.getJaaronderhouduitgevoerd())
            )
            .satisfies(e -> assertThat(e.getLengte()).as("check lengte").isEqualTo(actual.getLengte()))
            .satisfies(e -> assertThat(e.getLeverancier()).as("check leverancier").isEqualTo(actual.getLeverancier()))
            .satisfies(e -> assertThat(e.getMateriaal()).as("check materiaal").isEqualTo(actual.getMateriaal()))
            .satisfies(e -> assertThat(e.getVorm()).as("check vorm").isEqualTo(actual.getVorm()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBordUpdatableRelationshipsEquals(Bord expected, Bord actual) {}
}
