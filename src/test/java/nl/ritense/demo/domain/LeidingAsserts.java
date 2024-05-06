package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LeidingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingAllPropertiesEquals(Leiding expected, Leiding actual) {
        assertLeidingAutoGeneratedPropertiesEquals(expected, actual);
        assertLeidingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingAllUpdatablePropertiesEquals(Leiding expected, Leiding actual) {
        assertLeidingUpdatableFieldsEquals(expected, actual);
        assertLeidingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingAutoGeneratedPropertiesEquals(Leiding expected, Leiding actual) {
        assertThat(expected)
            .as("Verify Leiding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingUpdatableFieldsEquals(Leiding expected, Leiding actual) {
        assertThat(expected)
            .as("Verify Leiding relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getAfwijkendedieptelegging())
                        .as("check afwijkendedieptelegging")
                        .isEqualTo(actual.getAfwijkendedieptelegging())
            )
            .satisfies(e -> assertThat(e.getBreedte()).as("check breedte").isEqualTo(actual.getBreedte()))
            .satisfies(e -> assertThat(e.getDiameter()).as("check diameter").isEqualTo(actual.getDiameter()))
            .satisfies(e -> assertThat(e.getDiepte()).as("check diepte").isEqualTo(actual.getDiepte()))
            .satisfies(
                e -> assertThat(e.getEisvoorzorgsmaatregel()).as("check eisvoorzorgsmaatregel").isEqualTo(actual.getEisvoorzorgsmaatregel())
            )
            .satisfies(
                e -> assertThat(e.getGeonauwkeurigheidxy()).as("check geonauwkeurigheidxy").isEqualTo(actual.getGeonauwkeurigheidxy())
            )
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
            .satisfies(e -> assertThat(e.getThemaimkl()).as("check themaimkl").isEqualTo(actual.getThemaimkl()))
            .satisfies(e -> assertThat(e.getVerhoogdrisico()).as("check verhoogdrisico").isEqualTo(actual.getVerhoogdrisico()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingUpdatableRelationshipsEquals(Leiding expected, Leiding actual) {}
}
