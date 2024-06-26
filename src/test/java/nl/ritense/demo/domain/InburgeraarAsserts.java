package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class InburgeraarAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInburgeraarAllPropertiesEquals(Inburgeraar expected, Inburgeraar actual) {
        assertInburgeraarAutoGeneratedPropertiesEquals(expected, actual);
        assertInburgeraarAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInburgeraarAllUpdatablePropertiesEquals(Inburgeraar expected, Inburgeraar actual) {
        assertInburgeraarUpdatableFieldsEquals(expected, actual);
        assertInburgeraarUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInburgeraarAutoGeneratedPropertiesEquals(Inburgeraar expected, Inburgeraar actual) {
        assertThat(expected)
            .as("Verify Inburgeraar auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInburgeraarUpdatableFieldsEquals(Inburgeraar expected, Inburgeraar actual) {
        assertThat(expected)
            .as("Verify Inburgeraar relevant properties")
            .satisfies(e -> assertThat(e.getDoelgroep()).as("check doelgroep").isEqualTo(actual.getDoelgroep()))
            .satisfies(
                e ->
                    assertThat(e.getGedetailleerdedoelgroep())
                        .as("check gedetailleerdedoelgroep")
                        .isEqualTo(actual.getGedetailleerdedoelgroep())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInburgeraarUpdatableRelationshipsEquals(Inburgeraar expected, Inburgeraar actual) {}
}
