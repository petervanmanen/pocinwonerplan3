package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BakAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBakAllPropertiesEquals(Bak expected, Bak actual) {
        assertBakAutoGeneratedPropertiesEquals(expected, actual);
        assertBakAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBakAllUpdatablePropertiesEquals(Bak expected, Bak actual) {
        assertBakUpdatableFieldsEquals(expected, actual);
        assertBakUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBakAutoGeneratedPropertiesEquals(Bak expected, Bak actual) {
        assertThat(expected)
            .as("Verify Bak auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBakUpdatableFieldsEquals(Bak expected, Bak actual) {
        assertThat(expected)
            .as("Verify Bak relevant properties")
            .satisfies(e -> assertThat(e.getBreedte()).as("check breedte").isEqualTo(actual.getBreedte()))
            .satisfies(e -> assertThat(e.getDiameter()).as("check diameter").isEqualTo(actual.getDiameter()))
            .satisfies(e -> assertThat(e.getGewichtleeg()).as("check gewichtleeg").isEqualTo(actual.getGewichtleeg()))
            .satisfies(e -> assertThat(e.getGewichtvol()).as("check gewichtvol").isEqualTo(actual.getGewichtvol()))
            .satisfies(e -> assertThat(e.getHoogte()).as("check hoogte").isEqualTo(actual.getHoogte()))
            .satisfies(e -> assertThat(e.getInhoud()).as("check inhoud").isEqualTo(actual.getInhoud()))
            .satisfies(
                e ->
                    assertThat(e.getJaaronderhouduitgevoerd())
                        .as("check jaaronderhouduitgevoerd")
                        .isEqualTo(actual.getJaaronderhouduitgevoerd())
            )
            .satisfies(
                e ->
                    assertThat(e.getKwaliteitsniveauactueel())
                        .as("check kwaliteitsniveauactueel")
                        .isEqualTo(actual.getKwaliteitsniveauactueel())
            )
            .satisfies(
                e ->
                    assertThat(e.getKwaliteitsniveaugewenst())
                        .as("check kwaliteitsniveaugewenst")
                        .isEqualTo(actual.getKwaliteitsniveaugewenst())
            )
            .satisfies(e -> assertThat(e.getLengte()).as("check lengte").isEqualTo(actual.getLengte()))
            .satisfies(e -> assertThat(e.getMateriaal()).as("check materiaal").isEqualTo(actual.getMateriaal()))
            .satisfies(e -> assertThat(e.getVerplaatsbaar()).as("check verplaatsbaar").isEqualTo(actual.getVerplaatsbaar()))
            .satisfies(e -> assertThat(e.getVorm()).as("check vorm").isEqualTo(actual.getVorm()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBakUpdatableRelationshipsEquals(Bak expected, Bak actual) {}
}
