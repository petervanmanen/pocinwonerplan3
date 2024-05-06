package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OverigbenoemdterreinAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigbenoemdterreinAllPropertiesEquals(Overigbenoemdterrein expected, Overigbenoemdterrein actual) {
        assertOverigbenoemdterreinAutoGeneratedPropertiesEquals(expected, actual);
        assertOverigbenoemdterreinAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigbenoemdterreinAllUpdatablePropertiesEquals(Overigbenoemdterrein expected, Overigbenoemdterrein actual) {
        assertOverigbenoemdterreinUpdatableFieldsEquals(expected, actual);
        assertOverigbenoemdterreinUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigbenoemdterreinAutoGeneratedPropertiesEquals(Overigbenoemdterrein expected, Overigbenoemdterrein actual) {
        assertThat(expected)
            .as("Verify Overigbenoemdterrein auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigbenoemdterreinUpdatableFieldsEquals(Overigbenoemdterrein expected, Overigbenoemdterrein actual) {
        assertThat(expected)
            .as("Verify Overigbenoemdterrein relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getGebruiksdoeloverigbenoemdterrein())
                        .as("check gebruiksdoeloverigbenoemdterrein")
                        .isEqualTo(actual.getGebruiksdoeloverigbenoemdterrein())
            )
            .satisfies(
                e ->
                    assertThat(e.getOverigbenoemdterreinidentificatie())
                        .as("check overigbenoemdterreinidentificatie")
                        .isEqualTo(actual.getOverigbenoemdterreinidentificatie())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOverigbenoemdterreinUpdatableRelationshipsEquals(Overigbenoemdterrein expected, Overigbenoemdterrein actual) {}
}
