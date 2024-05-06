package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class UitvoerdergraafwerkzaamhedenAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerdergraafwerkzaamhedenAllPropertiesEquals(
        Uitvoerdergraafwerkzaamheden expected,
        Uitvoerdergraafwerkzaamheden actual
    ) {
        assertUitvoerdergraafwerkzaamhedenAutoGeneratedPropertiesEquals(expected, actual);
        assertUitvoerdergraafwerkzaamhedenAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerdergraafwerkzaamhedenAllUpdatablePropertiesEquals(
        Uitvoerdergraafwerkzaamheden expected,
        Uitvoerdergraafwerkzaamheden actual
    ) {
        assertUitvoerdergraafwerkzaamhedenUpdatableFieldsEquals(expected, actual);
        assertUitvoerdergraafwerkzaamhedenUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerdergraafwerkzaamhedenAutoGeneratedPropertiesEquals(
        Uitvoerdergraafwerkzaamheden expected,
        Uitvoerdergraafwerkzaamheden actual
    ) {
        assertThat(expected)
            .as("Verify Uitvoerdergraafwerkzaamheden auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerdergraafwerkzaamhedenUpdatableFieldsEquals(
        Uitvoerdergraafwerkzaamheden expected,
        Uitvoerdergraafwerkzaamheden actual
    ) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitvoerdergraafwerkzaamhedenUpdatableRelationshipsEquals(
        Uitvoerdergraafwerkzaamheden expected,
        Uitvoerdergraafwerkzaamheden actual
    ) {}
}