package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SollicitantAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSollicitantAllPropertiesEquals(Sollicitant expected, Sollicitant actual) {
        assertSollicitantAutoGeneratedPropertiesEquals(expected, actual);
        assertSollicitantAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSollicitantAllUpdatablePropertiesEquals(Sollicitant expected, Sollicitant actual) {
        assertSollicitantUpdatableFieldsEquals(expected, actual);
        assertSollicitantUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSollicitantAutoGeneratedPropertiesEquals(Sollicitant expected, Sollicitant actual) {
        assertThat(expected)
            .as("Verify Sollicitant auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSollicitantUpdatableFieldsEquals(Sollicitant expected, Sollicitant actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSollicitantUpdatableRelationshipsEquals(Sollicitant expected, Sollicitant actual) {
        assertThat(expected)
            .as("Verify Sollicitant relationships")
            .satisfies(
                e ->
                    assertThat(e.getKandidaatSollicitatiegespreks())
                        .as("check kandidaatSollicitatiegespreks")
                        .isEqualTo(actual.getKandidaatSollicitatiegespreks())
            );
    }
}
