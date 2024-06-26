package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LocatieaanduidingwozobjectAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingwozobjectAllPropertiesEquals(
        Locatieaanduidingwozobject expected,
        Locatieaanduidingwozobject actual
    ) {
        assertLocatieaanduidingwozobjectAutoGeneratedPropertiesEquals(expected, actual);
        assertLocatieaanduidingwozobjectAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingwozobjectAllUpdatablePropertiesEquals(
        Locatieaanduidingwozobject expected,
        Locatieaanduidingwozobject actual
    ) {
        assertLocatieaanduidingwozobjectUpdatableFieldsEquals(expected, actual);
        assertLocatieaanduidingwozobjectUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingwozobjectAutoGeneratedPropertiesEquals(
        Locatieaanduidingwozobject expected,
        Locatieaanduidingwozobject actual
    ) {
        assertThat(expected)
            .as("Verify Locatieaanduidingwozobject auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingwozobjectUpdatableFieldsEquals(
        Locatieaanduidingwozobject expected,
        Locatieaanduidingwozobject actual
    ) {
        assertThat(expected)
            .as("Verify Locatieaanduidingwozobject relevant properties")
            .satisfies(
                e -> assertThat(e.getDatumbegingeldigheid()).as("check datumbegingeldigheid").isEqualTo(actual.getDatumbegingeldigheid())
            )
            .satisfies(
                e -> assertThat(e.getDatumeindegeldigheid()).as("check datumeindegeldigheid").isEqualTo(actual.getDatumeindegeldigheid())
            )
            .satisfies(
                e -> assertThat(e.getLocatieomschrijving()).as("check locatieomschrijving").isEqualTo(actual.getLocatieomschrijving())
            )
            .satisfies(e -> assertThat(e.getPrimair()).as("check primair").isEqualTo(actual.getPrimair()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocatieaanduidingwozobjectUpdatableRelationshipsEquals(
        Locatieaanduidingwozobject expected,
        Locatieaanduidingwozobject actual
    ) {}
}
