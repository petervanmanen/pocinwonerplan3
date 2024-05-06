package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class GebouwinstallatieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGebouwinstallatieAllPropertiesEquals(Gebouwinstallatie expected, Gebouwinstallatie actual) {
        assertGebouwinstallatieAutoGeneratedPropertiesEquals(expected, actual);
        assertGebouwinstallatieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGebouwinstallatieAllUpdatablePropertiesEquals(Gebouwinstallatie expected, Gebouwinstallatie actual) {
        assertGebouwinstallatieUpdatableFieldsEquals(expected, actual);
        assertGebouwinstallatieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGebouwinstallatieAutoGeneratedPropertiesEquals(Gebouwinstallatie expected, Gebouwinstallatie actual) {
        assertThat(expected)
            .as("Verify Gebouwinstallatie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGebouwinstallatieUpdatableFieldsEquals(Gebouwinstallatie expected, Gebouwinstallatie actual) {
        assertThat(expected)
            .as("Verify Gebouwinstallatie relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidgebouwinstallatie())
                        .as("check datumbegingeldigheidgebouwinstallatie")
                        .isEqualTo(actual.getDatumbegingeldigheidgebouwinstallatie())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidgebouwinstallatie())
                        .as("check datumeindegeldigheidgebouwinstallatie")
                        .isEqualTo(actual.getDatumeindegeldigheidgebouwinstallatie())
            )
            .satisfies(
                e ->
                    assertThat(e.getGeometriegebouwinstallatie())
                        .as("check geometriegebouwinstallatie")
                        .isEqualTo(actual.getGeometriegebouwinstallatie())
            )
            .satisfies(
                e ->
                    assertThat(e.getIdentificatiegebouwinstallatie())
                        .as("check identificatiegebouwinstallatie")
                        .isEqualTo(actual.getIdentificatiegebouwinstallatie())
            )
            .satisfies(
                e ->
                    assertThat(e.getLod0geometriegebouwinstallatie())
                        .as("check lod0geometriegebouwinstallatie")
                        .isEqualTo(actual.getLod0geometriegebouwinstallatie())
            )
            .satisfies(
                e ->
                    assertThat(e.getRelatievehoogteligginggebouwinstallatie())
                        .as("check relatievehoogteligginggebouwinstallatie")
                        .isEqualTo(actual.getRelatievehoogteligginggebouwinstallatie())
            )
            .satisfies(
                e ->
                    assertThat(e.getStatusgebouwinstallatie())
                        .as("check statusgebouwinstallatie")
                        .isEqualTo(actual.getStatusgebouwinstallatie())
            )
            .satisfies(
                e -> assertThat(e.getTypegebouwinstallatie()).as("check typegebouwinstallatie").isEqualTo(actual.getTypegebouwinstallatie())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGebouwinstallatieUpdatableRelationshipsEquals(Gebouwinstallatie expected, Gebouwinstallatie actual) {}
}
