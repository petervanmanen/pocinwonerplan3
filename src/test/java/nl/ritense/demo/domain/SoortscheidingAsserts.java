package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SoortscheidingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortscheidingAllPropertiesEquals(Soortscheiding expected, Soortscheiding actual) {
        assertSoortscheidingAutoGeneratedPropertiesEquals(expected, actual);
        assertSoortscheidingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortscheidingAllUpdatablePropertiesEquals(Soortscheiding expected, Soortscheiding actual) {
        assertSoortscheidingUpdatableFieldsEquals(expected, actual);
        assertSoortscheidingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortscheidingAutoGeneratedPropertiesEquals(Soortscheiding expected, Soortscheiding actual) {
        assertThat(expected)
            .as("Verify Soortscheiding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortscheidingUpdatableFieldsEquals(Soortscheiding expected, Soortscheiding actual) {
        assertThat(expected)
            .as("Verify Soortscheiding relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getIndicatieplusbrpopulatie())
                        .as("check indicatieplusbrpopulatie")
                        .isEqualTo(actual.getIndicatieplusbrpopulatie())
            )
            .satisfies(e -> assertThat(e.getTypescheiding()).as("check typescheiding").isEqualTo(actual.getTypescheiding()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSoortscheidingUpdatableRelationshipsEquals(Soortscheiding expected, Soortscheiding actual) {}
}