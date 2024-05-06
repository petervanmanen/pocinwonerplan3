package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class WaterobjectAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterobjectAllPropertiesEquals(Waterobject expected, Waterobject actual) {
        assertWaterobjectAutoGeneratedPropertiesEquals(expected, actual);
        assertWaterobjectAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterobjectAllUpdatablePropertiesEquals(Waterobject expected, Waterobject actual) {
        assertWaterobjectUpdatableFieldsEquals(expected, actual);
        assertWaterobjectUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterobjectAutoGeneratedPropertiesEquals(Waterobject expected, Waterobject actual) {
        assertThat(expected)
            .as("Verify Waterobject auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterobjectUpdatableFieldsEquals(Waterobject expected, Waterobject actual) {
        assertThat(expected)
            .as("Verify Waterobject relevant properties")
            .satisfies(e -> assertThat(e.getBreedte()).as("check breedte").isEqualTo(actual.getBreedte()))
            .satisfies(e -> assertThat(e.getFolie()).as("check folie").isEqualTo(actual.getFolie()))
            .satisfies(e -> assertThat(e.getHoogte()).as("check hoogte").isEqualTo(actual.getHoogte()))
            .satisfies(
                e -> assertThat(e.getInfiltrerendoppervlak()).as("check infiltrerendoppervlak").isEqualTo(actual.getInfiltrerendoppervlak())
            )
            .satisfies(
                e -> assertThat(e.getInfiltrerendvermogen()).as("check infiltrerendvermogen").isEqualTo(actual.getInfiltrerendvermogen())
            )
            .satisfies(e -> assertThat(e.getLengte()).as("check lengte").isEqualTo(actual.getLengte()))
            .satisfies(e -> assertThat(e.getLozingspunt()).as("check lozingspunt").isEqualTo(actual.getLozingspunt()))
            .satisfies(e -> assertThat(e.getOppervlakte()).as("check oppervlakte").isEqualTo(actual.getOppervlakte()))
            .satisfies(e -> assertThat(e.getPorositeit()).as("check porositeit").isEqualTo(actual.getPorositeit()))
            .satisfies(e -> assertThat(e.getStreefdiepte()).as("check streefdiepte").isEqualTo(actual.getStreefdiepte()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()))
            .satisfies(e -> assertThat(e.getTypeplus()).as("check typeplus").isEqualTo(actual.getTypeplus()))
            .satisfies(e -> assertThat(e.getTypeplus2()).as("check typeplus2").isEqualTo(actual.getTypeplus2()))
            .satisfies(e -> assertThat(e.getTypevaarwater()).as("check typevaarwater").isEqualTo(actual.getTypevaarwater()))
            .satisfies(e -> assertThat(e.getTypewaterplant()).as("check typewaterplant").isEqualTo(actual.getTypewaterplant()))
            .satisfies(e -> assertThat(e.getUitstroomniveau()).as("check uitstroomniveau").isEqualTo(actual.getUitstroomniveau()))
            .satisfies(e -> assertThat(e.getVaarwegtraject()).as("check vaarwegtraject").isEqualTo(actual.getVaarwegtraject()))
            .satisfies(e -> assertThat(e.getVorm()).as("check vorm").isEqualTo(actual.getVorm()))
            .satisfies(e -> assertThat(e.getWaternaam()).as("check waternaam").isEqualTo(actual.getWaternaam()))
            .satisfies(e -> assertThat(e.getWaterpeil()).as("check waterpeil").isEqualTo(actual.getWaterpeil()))
            .satisfies(e -> assertThat(e.getWaterpeilwinter()).as("check waterpeilwinter").isEqualTo(actual.getWaterpeilwinter()))
            .satisfies(e -> assertThat(e.getWaterpeilzomer()).as("check waterpeilzomer").isEqualTo(actual.getWaterpeilzomer()))
            .satisfies(e -> assertThat(e.getWaterplanten()).as("check waterplanten").isEqualTo(actual.getWaterplanten()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterobjectUpdatableRelationshipsEquals(Waterobject expected, Waterobject actual) {}
}
