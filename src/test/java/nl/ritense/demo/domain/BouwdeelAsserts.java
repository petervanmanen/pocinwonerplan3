package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BouwdeelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelAllPropertiesEquals(Bouwdeel expected, Bouwdeel actual) {
        assertBouwdeelAutoGeneratedPropertiesEquals(expected, actual);
        assertBouwdeelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelAllUpdatablePropertiesEquals(Bouwdeel expected, Bouwdeel actual) {
        assertBouwdeelUpdatableFieldsEquals(expected, actual);
        assertBouwdeelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelAutoGeneratedPropertiesEquals(Bouwdeel expected, Bouwdeel actual) {
        assertThat(expected)
            .as("Verify Bouwdeel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelUpdatableFieldsEquals(Bouwdeel expected, Bouwdeel actual) {
        assertThat(expected)
            .as("Verify Bouwdeel relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelUpdatableRelationshipsEquals(Bouwdeel expected, Bouwdeel actual) {
        assertThat(expected)
            .as("Verify Bouwdeel relationships")
            .satisfies(
                e ->
                    assertThat(e.getBestaatuitVastgoedobject())
                        .as("check bestaatuitVastgoedobject")
                        .isEqualTo(actual.getBestaatuitVastgoedobject())
            )
            .satisfies(e -> assertThat(e.getBetreftWerkbons()).as("check betreftWerkbons").isEqualTo(actual.getBetreftWerkbons()));
    }
}
