package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OverigescheidingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigescheidingAllPropertiesEquals(Overigescheiding expected, Overigescheiding actual) {
        assertOverigescheidingAutoGeneratedPropertiesEquals(expected, actual);
        assertOverigescheidingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigescheidingAllUpdatablePropertiesEquals(Overigescheiding expected, Overigescheiding actual) {
        assertOverigescheidingUpdatableFieldsEquals(expected, actual);
        assertOverigescheidingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigescheidingAutoGeneratedPropertiesEquals(Overigescheiding expected, Overigescheiding actual) {
        assertThat(expected)
            .as("Verify Overigescheiding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigescheidingUpdatableFieldsEquals(Overigescheiding expected, Overigescheiding actual) {
        assertThat(expected)
            .as("Verify Overigescheiding relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidoverigescheiding())
                        .as("check datumbegingeldigheidoverigescheiding")
                        .isEqualTo(actual.getDatumbegingeldigheidoverigescheiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidoverigescheiding())
                        .as("check datumeindegeldigheidoverigescheiding")
                        .isEqualTo(actual.getDatumeindegeldigheidoverigescheiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getGeometrieoverigescheiding())
                        .as("check geometrieoverigescheiding")
                        .isEqualTo(actual.getGeometrieoverigescheiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getIdentificatieoverigescheiding())
                        .as("check identificatieoverigescheiding")
                        .isEqualTo(actual.getIdentificatieoverigescheiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getLod0geometrieoverigescheiding())
                        .as("check lod0geometrieoverigescheiding")
                        .isEqualTo(actual.getLod0geometrieoverigescheiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getLod1geometrieoverigescheiding())
                        .as("check lod1geometrieoverigescheiding")
                        .isEqualTo(actual.getLod1geometrieoverigescheiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getLod2geometrieoverigescheiding())
                        .as("check lod2geometrieoverigescheiding")
                        .isEqualTo(actual.getLod2geometrieoverigescheiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getLod3geometrieoverigescheiding())
                        .as("check lod3geometrieoverigescheiding")
                        .isEqualTo(actual.getLod3geometrieoverigescheiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getRelatievehoogteliggingoverigescheiding())
                        .as("check relatievehoogteliggingoverigescheiding")
                        .isEqualTo(actual.getRelatievehoogteliggingoverigescheiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getStatusoverigescheiding())
                        .as("check statusoverigescheiding")
                        .isEqualTo(actual.getStatusoverigescheiding())
            )
            .satisfies(
                e -> assertThat(e.getTypeoverigescheiding()).as("check typeoverigescheiding").isEqualTo(actual.getTypeoverigescheiding())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigescheidingUpdatableRelationshipsEquals(Overigescheiding expected, Overigescheiding actual) {}
}