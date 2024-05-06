package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CultuurcodeonbebouwdAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuurcodeonbebouwdAllPropertiesEquals(Cultuurcodeonbebouwd expected, Cultuurcodeonbebouwd actual) {
        assertCultuurcodeonbebouwdAutoGeneratedPropertiesEquals(expected, actual);
        assertCultuurcodeonbebouwdAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuurcodeonbebouwdAllUpdatablePropertiesEquals(Cultuurcodeonbebouwd expected, Cultuurcodeonbebouwd actual) {
        assertCultuurcodeonbebouwdUpdatableFieldsEquals(expected, actual);
        assertCultuurcodeonbebouwdUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuurcodeonbebouwdAutoGeneratedPropertiesEquals(Cultuurcodeonbebouwd expected, Cultuurcodeonbebouwd actual) {
        assertThat(expected)
            .as("Verify Cultuurcodeonbebouwd auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuurcodeonbebouwdUpdatableFieldsEquals(Cultuurcodeonbebouwd expected, Cultuurcodeonbebouwd actual) {
        assertThat(expected)
            .as("Verify Cultuurcodeonbebouwd relevant properties")
            .satisfies(
                e -> assertThat(e.getCultuurcodeonbebouwd()).as("check cultuurcodeonbebouwd").isEqualTo(actual.getCultuurcodeonbebouwd())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidcultuurcodeonbebouwd())
                        .as("check datumbegingeldigheidcultuurcodeonbebouwd")
                        .isEqualTo(actual.getDatumbegingeldigheidcultuurcodeonbebouwd())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidcultuurcodeonbebouwd())
                        .as("check datumeindegeldigheidcultuurcodeonbebouwd")
                        .isEqualTo(actual.getDatumeindegeldigheidcultuurcodeonbebouwd())
            )
            .satisfies(
                e ->
                    assertThat(e.getNaamcultuurcodeonbebouwd())
                        .as("check naamcultuurcodeonbebouwd")
                        .isEqualTo(actual.getNaamcultuurcodeonbebouwd())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCultuurcodeonbebouwdUpdatableRelationshipsEquals(Cultuurcodeonbebouwd expected, Cultuurcodeonbebouwd actual) {}
}