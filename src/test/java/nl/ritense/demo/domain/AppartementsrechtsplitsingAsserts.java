package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AppartementsrechtsplitsingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppartementsrechtsplitsingAllPropertiesEquals(
        Appartementsrechtsplitsing expected,
        Appartementsrechtsplitsing actual
    ) {
        assertAppartementsrechtsplitsingAutoGeneratedPropertiesEquals(expected, actual);
        assertAppartementsrechtsplitsingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppartementsrechtsplitsingAllUpdatablePropertiesEquals(
        Appartementsrechtsplitsing expected,
        Appartementsrechtsplitsing actual
    ) {
        assertAppartementsrechtsplitsingUpdatableFieldsEquals(expected, actual);
        assertAppartementsrechtsplitsingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppartementsrechtsplitsingAutoGeneratedPropertiesEquals(
        Appartementsrechtsplitsing expected,
        Appartementsrechtsplitsing actual
    ) {
        assertThat(expected)
            .as("Verify Appartementsrechtsplitsing auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppartementsrechtsplitsingUpdatableFieldsEquals(
        Appartementsrechtsplitsing expected,
        Appartementsrechtsplitsing actual
    ) {
        assertThat(expected)
            .as("Verify Appartementsrechtsplitsing relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getDdentificatieappartementsrechtsplitsing())
                        .as("check ddentificatieappartementsrechtsplitsing")
                        .isEqualTo(actual.getDdentificatieappartementsrechtsplitsing())
            )
            .satisfies(e -> assertThat(e.getTypesplitsing()).as("check typesplitsing").isEqualTo(actual.getTypesplitsing()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppartementsrechtsplitsingUpdatableRelationshipsEquals(
        Appartementsrechtsplitsing expected,
        Appartementsrechtsplitsing actual
    ) {}
}
