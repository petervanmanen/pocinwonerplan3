package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class WaterdeelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterdeelAllPropertiesEquals(Waterdeel expected, Waterdeel actual) {
        assertWaterdeelAutoGeneratedPropertiesEquals(expected, actual);
        assertWaterdeelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterdeelAllUpdatablePropertiesEquals(Waterdeel expected, Waterdeel actual) {
        assertWaterdeelUpdatableFieldsEquals(expected, actual);
        assertWaterdeelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterdeelAutoGeneratedPropertiesEquals(Waterdeel expected, Waterdeel actual) {
        assertThat(expected)
            .as("Verify Waterdeel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterdeelUpdatableFieldsEquals(Waterdeel expected, Waterdeel actual) {
        assertThat(expected)
            .as("Verify Waterdeel relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidwaterdeel())
                        .as("check datumbegingeldigheidwaterdeel")
                        .isEqualTo(actual.getDatumbegingeldigheidwaterdeel())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidwaterdeel())
                        .as("check datumeindegeldigheidwaterdeel")
                        .isEqualTo(actual.getDatumeindegeldigheidwaterdeel())
            )
            .satisfies(e -> assertThat(e.getGeometriewaterdeel()).as("check geometriewaterdeel").isEqualTo(actual.getGeometriewaterdeel()))
            .satisfies(
                e ->
                    assertThat(e.getIdentificatiewaterdeel())
                        .as("check identificatiewaterdeel")
                        .isEqualTo(actual.getIdentificatiewaterdeel())
            )
            .satisfies(e -> assertThat(e.getPlustypewaterdeel()).as("check plustypewaterdeel").isEqualTo(actual.getPlustypewaterdeel()))
            .satisfies(
                e ->
                    assertThat(e.getRelatievehoogteliggingwaterdeel())
                        .as("check relatievehoogteliggingwaterdeel")
                        .isEqualTo(actual.getRelatievehoogteliggingwaterdeel())
            )
            .satisfies(e -> assertThat(e.getStatuswaterdeel()).as("check statuswaterdeel").isEqualTo(actual.getStatuswaterdeel()))
            .satisfies(e -> assertThat(e.getTypewaterdeel()).as("check typewaterdeel").isEqualTo(actual.getTypewaterdeel()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWaterdeelUpdatableRelationshipsEquals(Waterdeel expected, Waterdeel actual) {}
}