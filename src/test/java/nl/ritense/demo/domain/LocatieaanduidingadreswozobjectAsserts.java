package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LocatieaanduidingadreswozobjectAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingadreswozobjectAllPropertiesEquals(
        Locatieaanduidingadreswozobject expected,
        Locatieaanduidingadreswozobject actual
    ) {
        assertLocatieaanduidingadreswozobjectAutoGeneratedPropertiesEquals(expected, actual);
        assertLocatieaanduidingadreswozobjectAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingadreswozobjectAllUpdatablePropertiesEquals(
        Locatieaanduidingadreswozobject expected,
        Locatieaanduidingadreswozobject actual
    ) {
        assertLocatieaanduidingadreswozobjectUpdatableFieldsEquals(expected, actual);
        assertLocatieaanduidingadreswozobjectUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingadreswozobjectAutoGeneratedPropertiesEquals(
        Locatieaanduidingadreswozobject expected,
        Locatieaanduidingadreswozobject actual
    ) {
        assertThat(expected)
            .as("Verify Locatieaanduidingadreswozobject auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingadreswozobjectUpdatableFieldsEquals(
        Locatieaanduidingadreswozobject expected,
        Locatieaanduidingadreswozobject actual
    ) {
        assertThat(expected)
            .as("Verify Locatieaanduidingadreswozobject relevant properties")
            .satisfies(
                e -> assertThat(e.getLocatieomschrijving()).as("check locatieomschrijving").isEqualTo(actual.getLocatieomschrijving())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingadreswozobjectUpdatableRelationshipsEquals(
        Locatieaanduidingadreswozobject expected,
        Locatieaanduidingadreswozobject actual
    ) {}
}
