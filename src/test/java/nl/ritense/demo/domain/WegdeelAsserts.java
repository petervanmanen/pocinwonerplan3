package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class WegdeelAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWegdeelAllPropertiesEquals(Wegdeel expected, Wegdeel actual) {
        assertWegdeelAutoGeneratedPropertiesEquals(expected, actual);
        assertWegdeelAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWegdeelAllUpdatablePropertiesEquals(Wegdeel expected, Wegdeel actual) {
        assertWegdeelUpdatableFieldsEquals(expected, actual);
        assertWegdeelUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWegdeelAutoGeneratedPropertiesEquals(Wegdeel expected, Wegdeel actual) {
        assertThat(expected)
            .as("Verify Wegdeel auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWegdeelUpdatableFieldsEquals(Wegdeel expected, Wegdeel actual) {
        assertThat(expected)
            .as("Verify Wegdeel relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidwegdeel())
                        .as("check datumbegingeldigheidwegdeel")
                        .isEqualTo(actual.getDatumbegingeldigheidwegdeel())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidwegdeel())
                        .as("check datumeindegeldigheidwegdeel")
                        .isEqualTo(actual.getDatumeindegeldigheidwegdeel())
            )
            .satisfies(e -> assertThat(e.getFunctiewegdeel()).as("check functiewegdeel").isEqualTo(actual.getFunctiewegdeel()))
            .satisfies(
                e ->
                    assertThat(e.getFysiekvoorkomenwegdeel())
                        .as("check fysiekvoorkomenwegdeel")
                        .isEqualTo(actual.getFysiekvoorkomenwegdeel())
            )
            .satisfies(e -> assertThat(e.getGeometriewegdeel()).as("check geometriewegdeel").isEqualTo(actual.getGeometriewegdeel()))
            .satisfies(
                e -> assertThat(e.getIdentificatiewegdeel()).as("check identificatiewegdeel").isEqualTo(actual.getIdentificatiewegdeel())
            )
            .satisfies(
                e ->
                    assertThat(e.getKruinlijngeometriewegdeel())
                        .as("check kruinlijngeometriewegdeel")
                        .isEqualTo(actual.getKruinlijngeometriewegdeel())
            )
            .satisfies(
                e -> assertThat(e.getLod0geometriewegdeel()).as("check lod0geometriewegdeel").isEqualTo(actual.getLod0geometriewegdeel())
            )
            .satisfies(e -> assertThat(e.getPlusfunctiewegdeel()).as("check plusfunctiewegdeel").isEqualTo(actual.getPlusfunctiewegdeel()))
            .satisfies(
                e ->
                    assertThat(e.getPlusfysiekvoorkomenwegdeel())
                        .as("check plusfysiekvoorkomenwegdeel")
                        .isEqualTo(actual.getPlusfysiekvoorkomenwegdeel())
            )
            .satisfies(
                e ->
                    assertThat(e.getRelatievehoogteliggingwegdeel())
                        .as("check relatievehoogteliggingwegdeel")
                        .isEqualTo(actual.getRelatievehoogteliggingwegdeel())
            )
            .satisfies(e -> assertThat(e.getStatuswegdeel()).as("check statuswegdeel").isEqualTo(actual.getStatuswegdeel()))
            .satisfies(e -> assertThat(e.getWegdeeloptalud()).as("check wegdeeloptalud").isEqualTo(actual.getWegdeeloptalud()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWegdeelUpdatableRelationshipsEquals(Wegdeel expected, Wegdeel actual) {
        assertThat(expected)
            .as("Verify Wegdeel relationships")
            .satisfies(e -> assertThat(e.getBetreftStremmings()).as("check betreftStremmings").isEqualTo(actual.getBetreftStremmings()));
    }
}
