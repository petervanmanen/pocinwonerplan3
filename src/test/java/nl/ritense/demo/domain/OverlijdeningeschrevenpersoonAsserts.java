package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OverlijdeningeschrevenpersoonAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverlijdeningeschrevenpersoonAllPropertiesEquals(
        Overlijdeningeschrevenpersoon expected,
        Overlijdeningeschrevenpersoon actual
    ) {
        assertOverlijdeningeschrevenpersoonAutoGeneratedPropertiesEquals(expected, actual);
        assertOverlijdeningeschrevenpersoonAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverlijdeningeschrevenpersoonAllUpdatablePropertiesEquals(
        Overlijdeningeschrevenpersoon expected,
        Overlijdeningeschrevenpersoon actual
    ) {
        assertOverlijdeningeschrevenpersoonUpdatableFieldsEquals(expected, actual);
        assertOverlijdeningeschrevenpersoonUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverlijdeningeschrevenpersoonAutoGeneratedPropertiesEquals(
        Overlijdeningeschrevenpersoon expected,
        Overlijdeningeschrevenpersoon actual
    ) {
        assertThat(expected)
            .as("Verify Overlijdeningeschrevenpersoon auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverlijdeningeschrevenpersoonUpdatableFieldsEquals(
        Overlijdeningeschrevenpersoon expected,
        Overlijdeningeschrevenpersoon actual
    ) {
        assertThat(expected)
            .as("Verify Overlijdeningeschrevenpersoon relevant properties")
            .satisfies(e -> assertThat(e.getDatumoverlijden()).as("check datumoverlijden").isEqualTo(actual.getDatumoverlijden()))
            .satisfies(e -> assertThat(e.getLandoverlijden()).as("check landoverlijden").isEqualTo(actual.getLandoverlijden()))
            .satisfies(e -> assertThat(e.getOverlijdensplaats()).as("check overlijdensplaats").isEqualTo(actual.getOverlijdensplaats()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverlijdeningeschrevenpersoonUpdatableRelationshipsEquals(
        Overlijdeningeschrevenpersoon expected,
        Overlijdeningeschrevenpersoon actual
    ) {}
}
