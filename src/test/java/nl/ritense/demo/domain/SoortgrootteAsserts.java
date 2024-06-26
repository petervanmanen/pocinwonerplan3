package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SoortgrootteAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortgrootteAllPropertiesEquals(Soortgrootte expected, Soortgrootte actual) {
        assertSoortgrootteAutoGeneratedPropertiesEquals(expected, actual);
        assertSoortgrootteAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortgrootteAllUpdatablePropertiesEquals(Soortgrootte expected, Soortgrootte actual) {
        assertSoortgrootteUpdatableFieldsEquals(expected, actual);
        assertSoortgrootteUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortgrootteAutoGeneratedPropertiesEquals(Soortgrootte expected, Soortgrootte actual) {
        assertThat(expected)
            .as("Verify Soortgrootte auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortgrootteUpdatableFieldsEquals(Soortgrootte expected, Soortgrootte actual) {
        assertThat(expected)
            .as("Verify Soortgrootte relevant properties")
            .satisfies(e -> assertThat(e.getCodesoortgrootte()).as("check codesoortgrootte").isEqualTo(actual.getCodesoortgrootte()))
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidsoortgrootte())
                        .as("check datumbegingeldigheidsoortgrootte")
                        .isEqualTo(actual.getDatumbegingeldigheidsoortgrootte())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidsoortgrootte())
                        .as("check datumeindegeldigheidsoortgrootte")
                        .isEqualTo(actual.getDatumeindegeldigheidsoortgrootte())
            )
            .satisfies(e -> assertThat(e.getNaamsoortgrootte()).as("check naamsoortgrootte").isEqualTo(actual.getNaamsoortgrootte()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortgrootteUpdatableRelationshipsEquals(Soortgrootte expected, Soortgrootte actual) {}
}
