package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BenoemdobjectAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBenoemdobjectAllPropertiesEquals(Benoemdobject expected, Benoemdobject actual) {
        assertBenoemdobjectAutoGeneratedPropertiesEquals(expected, actual);
        assertBenoemdobjectAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBenoemdobjectAllUpdatablePropertiesEquals(Benoemdobject expected, Benoemdobject actual) {
        assertBenoemdobjectUpdatableFieldsEquals(expected, actual);
        assertBenoemdobjectUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBenoemdobjectAutoGeneratedPropertiesEquals(Benoemdobject expected, Benoemdobject actual) {
        assertThat(expected)
            .as("Verify Benoemdobject auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBenoemdobjectUpdatableFieldsEquals(Benoemdobject expected, Benoemdobject actual) {
        assertThat(expected)
            .as("Verify Benoemdobject relevant properties")
            .satisfies(
                e -> assertThat(e.getDatumbegingeldigheid()).as("check datumbegingeldigheid").isEqualTo(actual.getDatumbegingeldigheid())
            )
            .satisfies(
                e -> assertThat(e.getDatumeindegeldigheid()).as("check datumeindegeldigheid").isEqualTo(actual.getDatumeindegeldigheid())
            )
            .satisfies(e -> assertThat(e.getGeometriepunt()).as("check geometriepunt").isEqualTo(actual.getGeometriepunt()))
            .satisfies(e -> assertThat(e.getGeometrievlak()).as("check geometrievlak").isEqualTo(actual.getGeometrievlak()))
            .satisfies(e -> assertThat(e.getIdentificatie()).as("check identificatie").isEqualTo(actual.getIdentificatie()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBenoemdobjectUpdatableRelationshipsEquals(Benoemdobject expected, Benoemdobject actual) {}
}