package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KademuurAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKademuurAllPropertiesEquals(Kademuur expected, Kademuur actual) {
        assertKademuurAutoGeneratedPropertiesEquals(expected, actual);
        assertKademuurAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKademuurAllUpdatablePropertiesEquals(Kademuur expected, Kademuur actual) {
        assertKademuurUpdatableFieldsEquals(expected, actual);
        assertKademuurUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKademuurAutoGeneratedPropertiesEquals(Kademuur expected, Kademuur actual) {
        assertThat(expected)
            .as("Verify Kademuur auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKademuurUpdatableFieldsEquals(Kademuur expected, Kademuur actual) {
        assertThat(expected)
            .as("Verify Kademuur relevant properties")
            .satisfies(
                e -> assertThat(e.getBelastingklassenieuw()).as("check belastingklassenieuw").isEqualTo(actual.getBelastingklassenieuw())
            )
            .satisfies(e -> assertThat(e.getBelastingklasseoud()).as("check belastingklasseoud").isEqualTo(actual.getBelastingklasseoud()))
            .satisfies(e -> assertThat(e.getGrijpstenen()).as("check grijpstenen").isEqualTo(actual.getGrijpstenen()))
            .satisfies(
                e ->
                    assertThat(e.getHoogtebovenkantkademuur())
                        .as("check hoogtebovenkantkademuur")
                        .isEqualTo(actual.getHoogtebovenkantkademuur())
            )
            .satisfies(
                e ->
                    assertThat(e.getMateriaalbovenkantkademuur())
                        .as("check materiaalbovenkantkademuur")
                        .isEqualTo(actual.getMateriaalbovenkantkademuur())
            )
            .satisfies(
                e ->
                    assertThat(e.getOppervlaktebovenkantkademuur())
                        .as("check oppervlaktebovenkantkademuur")
                        .isEqualTo(actual.getOppervlaktebovenkantkademuur())
            )
            .satisfies(e -> assertThat(e.getReddingslijn()).as("check reddingslijn").isEqualTo(actual.getReddingslijn()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()))
            .satisfies(
                e -> assertThat(e.getTypebovenkantkademuur()).as("check typebovenkantkademuur").isEqualTo(actual.getTypebovenkantkademuur())
            )
            .satisfies(e -> assertThat(e.getTypefundering()).as("check typefundering").isEqualTo(actual.getTypefundering()))
            .satisfies(e -> assertThat(e.getTypeverankering()).as("check typeverankering").isEqualTo(actual.getTypeverankering()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKademuurUpdatableRelationshipsEquals(Kademuur expected, Kademuur actual) {}
}
