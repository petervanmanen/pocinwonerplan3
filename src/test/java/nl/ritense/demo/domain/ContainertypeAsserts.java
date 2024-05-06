package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ContainertypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainertypeAllPropertiesEquals(Containertype expected, Containertype actual) {
        assertContainertypeAutoGeneratedPropertiesEquals(expected, actual);
        assertContainertypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainertypeAllUpdatablePropertiesEquals(Containertype expected, Containertype actual) {
        assertContainertypeUpdatableFieldsEquals(expected, actual);
        assertContainertypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainertypeAutoGeneratedPropertiesEquals(Containertype expected, Containertype actual) {
        assertThat(expected)
            .as("Verify Containertype auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainertypeUpdatableFieldsEquals(Containertype expected, Containertype actual) {
        assertThat(expected)
            .as("Verify Containertype relevant properties")
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainertypeUpdatableRelationshipsEquals(Containertype expected, Containertype actual) {
        assertThat(expected)
            .as("Verify Containertype relationships")
            .satisfies(
                e ->
                    assertThat(e.getGeschiktvoorVuilniswagens())
                        .as("check geschiktvoorVuilniswagens")
                        .isEqualTo(actual.getGeschiktvoorVuilniswagens())
            );
    }
}
