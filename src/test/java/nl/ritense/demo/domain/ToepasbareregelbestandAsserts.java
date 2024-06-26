package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ToepasbareregelbestandAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertToepasbareregelbestandAllPropertiesEquals(Toepasbareregelbestand expected, Toepasbareregelbestand actual) {
        assertToepasbareregelbestandAutoGeneratedPropertiesEquals(expected, actual);
        assertToepasbareregelbestandAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertToepasbareregelbestandAllUpdatablePropertiesEquals(
        Toepasbareregelbestand expected,
        Toepasbareregelbestand actual
    ) {
        assertToepasbareregelbestandUpdatableFieldsEquals(expected, actual);
        assertToepasbareregelbestandUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertToepasbareregelbestandAutoGeneratedPropertiesEquals(
        Toepasbareregelbestand expected,
        Toepasbareregelbestand actual
    ) {
        assertThat(expected)
            .as("Verify Toepasbareregelbestand auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertToepasbareregelbestandUpdatableFieldsEquals(Toepasbareregelbestand expected, Toepasbareregelbestand actual) {
        assertThat(expected)
            .as("Verify Toepasbareregelbestand relevant properties")
            .satisfies(
                e -> assertThat(e.getDatumeindegeldigheid()).as("check datumeindegeldigheid").isEqualTo(actual.getDatumeindegeldigheid())
            )
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertToepasbareregelbestandUpdatableRelationshipsEquals(
        Toepasbareregelbestand expected,
        Toepasbareregelbestand actual
    ) {}
}
