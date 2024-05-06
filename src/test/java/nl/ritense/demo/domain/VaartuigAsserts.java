package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VaartuigAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVaartuigAllPropertiesEquals(Vaartuig expected, Vaartuig actual) {
        assertVaartuigAutoGeneratedPropertiesEquals(expected, actual);
        assertVaartuigAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVaartuigAllUpdatablePropertiesEquals(Vaartuig expected, Vaartuig actual) {
        assertVaartuigUpdatableFieldsEquals(expected, actual);
        assertVaartuigUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVaartuigAutoGeneratedPropertiesEquals(Vaartuig expected, Vaartuig actual) {
        assertThat(expected)
            .as("Verify Vaartuig auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVaartuigUpdatableFieldsEquals(Vaartuig expected, Vaartuig actual) {
        assertThat(expected)
            .as("Verify Vaartuig relevant properties")
            .satisfies(e -> assertThat(e.getBreedte()).as("check breedte").isEqualTo(actual.getBreedte()))
            .satisfies(e -> assertThat(e.getHoogte()).as("check hoogte").isEqualTo(actual.getHoogte()))
            .satisfies(e -> assertThat(e.getKleur()).as("check kleur").isEqualTo(actual.getKleur()))
            .satisfies(e -> assertThat(e.getLengte()).as("check lengte").isEqualTo(actual.getLengte()))
            .satisfies(e -> assertThat(e.getNaamvaartuig()).as("check naamvaartuig").isEqualTo(actual.getNaamvaartuig()))
            .satisfies(e -> assertThat(e.getRegistratienummer()).as("check registratienummer").isEqualTo(actual.getRegistratienummer()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVaartuigUpdatableRelationshipsEquals(Vaartuig expected, Vaartuig actual) {}
}
