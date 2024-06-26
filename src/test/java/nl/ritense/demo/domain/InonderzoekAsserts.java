package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class InonderzoekAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInonderzoekAllPropertiesEquals(Inonderzoek expected, Inonderzoek actual) {
        assertInonderzoekAutoGeneratedPropertiesEquals(expected, actual);
        assertInonderzoekAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInonderzoekAllUpdatablePropertiesEquals(Inonderzoek expected, Inonderzoek actual) {
        assertInonderzoekUpdatableFieldsEquals(expected, actual);
        assertInonderzoekUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInonderzoekAutoGeneratedPropertiesEquals(Inonderzoek expected, Inonderzoek actual) {
        assertThat(expected)
            .as("Verify Inonderzoek auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInonderzoekUpdatableFieldsEquals(Inonderzoek expected, Inonderzoek actual) {
        assertThat(expected)
            .as("Verify Inonderzoek relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getAanduidinggegevensinonderzoek())
                        .as("check aanduidinggegevensinonderzoek")
                        .isEqualTo(actual.getAanduidinggegevensinonderzoek())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInonderzoekUpdatableRelationshipsEquals(Inonderzoek expected, Inonderzoek actual) {}
}
