package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VerblijfadresingeschrevenpersoonAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfadresingeschrevenpersoonAllPropertiesEquals(
        Verblijfadresingeschrevenpersoon expected,
        Verblijfadresingeschrevenpersoon actual
    ) {
        assertVerblijfadresingeschrevenpersoonAutoGeneratedPropertiesEquals(expected, actual);
        assertVerblijfadresingeschrevenpersoonAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfadresingeschrevenpersoonAllUpdatablePropertiesEquals(
        Verblijfadresingeschrevenpersoon expected,
        Verblijfadresingeschrevenpersoon actual
    ) {
        assertVerblijfadresingeschrevenpersoonUpdatableFieldsEquals(expected, actual);
        assertVerblijfadresingeschrevenpersoonUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfadresingeschrevenpersoonAutoGeneratedPropertiesEquals(
        Verblijfadresingeschrevenpersoon expected,
        Verblijfadresingeschrevenpersoon actual
    ) {
        assertThat(expected)
            .as("Verify Verblijfadresingeschrevenpersoon auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfadresingeschrevenpersoonUpdatableFieldsEquals(
        Verblijfadresingeschrevenpersoon expected,
        Verblijfadresingeschrevenpersoon actual
    ) {
        assertThat(expected)
            .as("Verify Verblijfadresingeschrevenpersoon relevant properties")
            .satisfies(e -> assertThat(e.getAdresherkomst()).as("check adresherkomst").isEqualTo(actual.getAdresherkomst()))
            .satisfies(
                e -> assertThat(e.getBeschrijvinglocatie()).as("check beschrijvinglocatie").isEqualTo(actual.getBeschrijvinglocatie())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerblijfadresingeschrevenpersoonUpdatableRelationshipsEquals(
        Verblijfadresingeschrevenpersoon expected,
        Verblijfadresingeschrevenpersoon actual
    ) {}
}
