package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ValutasoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValutasoortAllPropertiesEquals(Valutasoort expected, Valutasoort actual) {
        assertValutasoortAutoGeneratedPropertiesEquals(expected, actual);
        assertValutasoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValutasoortAllUpdatablePropertiesEquals(Valutasoort expected, Valutasoort actual) {
        assertValutasoortUpdatableFieldsEquals(expected, actual);
        assertValutasoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValutasoortAutoGeneratedPropertiesEquals(Valutasoort expected, Valutasoort actual) {
        assertThat(expected)
            .as("Verify Valutasoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValutasoortUpdatableFieldsEquals(Valutasoort expected, Valutasoort actual) {
        assertThat(expected)
            .as("Verify Valutasoort relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidvalutasoort())
                        .as("check datumbegingeldigheidvalutasoort")
                        .isEqualTo(actual.getDatumbegingeldigheidvalutasoort())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidvalutasoort())
                        .as("check datumeindegeldigheidvalutasoort")
                        .isEqualTo(actual.getDatumeindegeldigheidvalutasoort())
            )
            .satisfies(e -> assertThat(e.getNaamvaluta()).as("check naamvaluta").isEqualTo(actual.getNaamvaluta()))
            .satisfies(e -> assertThat(e.getValutacode()).as("check valutacode").isEqualTo(actual.getValutacode()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertValutasoortUpdatableRelationshipsEquals(Valutasoort expected, Valutasoort actual) {}
}
