package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BouwdeelelementAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelelementAllPropertiesEquals(Bouwdeelelement expected, Bouwdeelelement actual) {
        assertBouwdeelelementAutoGeneratedPropertiesEquals(expected, actual);
        assertBouwdeelelementAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelelementAllUpdatablePropertiesEquals(Bouwdeelelement expected, Bouwdeelelement actual) {
        assertBouwdeelelementUpdatableFieldsEquals(expected, actual);
        assertBouwdeelelementUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelelementAutoGeneratedPropertiesEquals(Bouwdeelelement expected, Bouwdeelelement actual) {
        assertThat(expected)
            .as("Verify Bouwdeelelement auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelelementUpdatableFieldsEquals(Bouwdeelelement expected, Bouwdeelelement actual) {
        assertThat(expected)
            .as("Verify Bouwdeelelement relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBouwdeelelementUpdatableRelationshipsEquals(Bouwdeelelement expected, Bouwdeelelement actual) {
        assertThat(expected)
            .as("Verify Bouwdeelelement relationships")
            .satisfies(e -> assertThat(e.getBestaatuitBouwdeel()).as("check bestaatuitBouwdeel").isEqualTo(actual.getBestaatuitBouwdeel()))
            .satisfies(e -> assertThat(e.getBetreftWerkbons()).as("check betreftWerkbons").isEqualTo(actual.getBetreftWerkbons()));
    }
}
