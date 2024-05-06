package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CultuuronbebouwdAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuuronbebouwdAllPropertiesEquals(Cultuuronbebouwd expected, Cultuuronbebouwd actual) {
        assertCultuuronbebouwdAutoGeneratedPropertiesEquals(expected, actual);
        assertCultuuronbebouwdAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuuronbebouwdAllUpdatablePropertiesEquals(Cultuuronbebouwd expected, Cultuuronbebouwd actual) {
        assertCultuuronbebouwdUpdatableFieldsEquals(expected, actual);
        assertCultuuronbebouwdUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuuronbebouwdAutoGeneratedPropertiesEquals(Cultuuronbebouwd expected, Cultuuronbebouwd actual) {
        assertThat(expected)
            .as("Verify Cultuuronbebouwd auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuuronbebouwdUpdatableFieldsEquals(Cultuuronbebouwd expected, Cultuuronbebouwd actual) {
        assertThat(expected)
            .as("Verify Cultuuronbebouwd relevant properties")
            .satisfies(
                e -> assertThat(e.getCultuurcodeonbebouwd()).as("check cultuurcodeonbebouwd").isEqualTo(actual.getCultuurcodeonbebouwd())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuuronbebouwdUpdatableRelationshipsEquals(Cultuuronbebouwd expected, Cultuuronbebouwd actual) {}
}
