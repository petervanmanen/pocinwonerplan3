package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class StrijdigheidofnietigheidAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrijdigheidofnietigheidAllPropertiesEquals(
        Strijdigheidofnietigheid expected,
        Strijdigheidofnietigheid actual
    ) {
        assertStrijdigheidofnietigheidAutoGeneratedPropertiesEquals(expected, actual);
        assertStrijdigheidofnietigheidAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrijdigheidofnietigheidAllUpdatablePropertiesEquals(
        Strijdigheidofnietigheid expected,
        Strijdigheidofnietigheid actual
    ) {
        assertStrijdigheidofnietigheidUpdatableFieldsEquals(expected, actual);
        assertStrijdigheidofnietigheidUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrijdigheidofnietigheidAutoGeneratedPropertiesEquals(
        Strijdigheidofnietigheid expected,
        Strijdigheidofnietigheid actual
    ) {
        assertThat(expected)
            .as("Verify Strijdigheidofnietigheid auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrijdigheidofnietigheidUpdatableFieldsEquals(
        Strijdigheidofnietigheid expected,
        Strijdigheidofnietigheid actual
    ) {
        assertThat(expected)
            .as("Verify Strijdigheidofnietigheid relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getAanduidingstrijdigheidnietigheid())
                        .as("check aanduidingstrijdigheidnietigheid")
                        .isEqualTo(actual.getAanduidingstrijdigheidnietigheid())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStrijdigheidofnietigheidUpdatableRelationshipsEquals(
        Strijdigheidofnietigheid expected,
        Strijdigheidofnietigheid actual
    ) {}
}
