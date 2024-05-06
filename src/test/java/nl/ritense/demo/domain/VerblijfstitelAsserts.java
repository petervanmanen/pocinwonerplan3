package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VerblijfstitelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfstitelAllPropertiesEquals(Verblijfstitel expected, Verblijfstitel actual) {
        assertVerblijfstitelAutoGeneratedPropertiesEquals(expected, actual);
        assertVerblijfstitelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfstitelAllUpdatablePropertiesEquals(Verblijfstitel expected, Verblijfstitel actual) {
        assertVerblijfstitelUpdatableFieldsEquals(expected, actual);
        assertVerblijfstitelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfstitelAutoGeneratedPropertiesEquals(Verblijfstitel expected, Verblijfstitel actual) {
        assertThat(expected)
            .as("Verify Verblijfstitel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfstitelUpdatableFieldsEquals(Verblijfstitel expected, Verblijfstitel actual) {
        assertThat(expected)
            .as("Verify Verblijfstitel relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getAanduidingverblijfstitel())
                        .as("check aanduidingverblijfstitel")
                        .isEqualTo(actual.getAanduidingverblijfstitel())
            )
            .satisfies(e -> assertThat(e.getDatumbegin()).as("check datumbegin").isEqualTo(actual.getDatumbegin()))
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(e -> assertThat(e.getDatumopname()).as("check datumopname").isEqualTo(actual.getDatumopname()))
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidverblijfstitel())
                        .as("check datumbegingeldigheidverblijfstitel")
                        .isEqualTo(actual.getDatumbegingeldigheidverblijfstitel())
            )
            .satisfies(e -> assertThat(e.getVerblijfstitelcode()).as("check verblijfstitelcode").isEqualTo(actual.getVerblijfstitelcode()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfstitelUpdatableRelationshipsEquals(Verblijfstitel expected, Verblijfstitel actual) {}
}