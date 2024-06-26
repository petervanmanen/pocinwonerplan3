package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LeidingelementAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingelementAllPropertiesEquals(Leidingelement expected, Leidingelement actual) {
        assertLeidingelementAutoGeneratedPropertiesEquals(expected, actual);
        assertLeidingelementAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingelementAllUpdatablePropertiesEquals(Leidingelement expected, Leidingelement actual) {
        assertLeidingelementUpdatableFieldsEquals(expected, actual);
        assertLeidingelementUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingelementAutoGeneratedPropertiesEquals(Leidingelement expected, Leidingelement actual) {
        assertThat(expected)
            .as("Verify Leidingelement auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingelementUpdatableFieldsEquals(Leidingelement expected, Leidingelement actual) {
        assertThat(expected)
            .as("Verify Leidingelement relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getAfwijkendedieptelegging())
                        .as("check afwijkendedieptelegging")
                        .isEqualTo(actual.getAfwijkendedieptelegging())
            )
            .satisfies(e -> assertThat(e.getDiepte()).as("check diepte").isEqualTo(actual.getDiepte()))
            .satisfies(
                e -> assertThat(e.getGeonauwkeurigheidxy()).as("check geonauwkeurigheidxy").isEqualTo(actual.getGeonauwkeurigheidxy())
            )
            .satisfies(
                e ->
                    assertThat(e.getJaaronderhouduitgevoerd())
                        .as("check jaaronderhouduitgevoerd")
                        .isEqualTo(actual.getJaaronderhouduitgevoerd())
            )
            .satisfies(e -> assertThat(e.getLeverancier()).as("check leverancier").isEqualTo(actual.getLeverancier()))
            .satisfies(e -> assertThat(e.getThemaimkl()).as("check themaimkl").isEqualTo(actual.getThemaimkl()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLeidingelementUpdatableRelationshipsEquals(Leidingelement expected, Leidingelement actual) {}
}
