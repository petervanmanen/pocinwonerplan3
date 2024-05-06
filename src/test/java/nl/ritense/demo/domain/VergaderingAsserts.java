package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VergaderingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVergaderingAllPropertiesEquals(Vergadering expected, Vergadering actual) {
        assertVergaderingAutoGeneratedPropertiesEquals(expected, actual);
        assertVergaderingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVergaderingAllUpdatablePropertiesEquals(Vergadering expected, Vergadering actual) {
        assertVergaderingUpdatableFieldsEquals(expected, actual);
        assertVergaderingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVergaderingAutoGeneratedPropertiesEquals(Vergadering expected, Vergadering actual) {
        assertThat(expected)
            .as("Verify Vergadering auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVergaderingUpdatableFieldsEquals(Vergadering expected, Vergadering actual) {
        assertThat(expected)
            .as("Verify Vergadering relevant properties")
            .satisfies(e -> assertThat(e.getEindtijd()).as("check eindtijd").isEqualTo(actual.getEindtijd()))
            .satisfies(e -> assertThat(e.getLocatie()).as("check locatie").isEqualTo(actual.getLocatie()))
            .satisfies(e -> assertThat(e.getStarttijd()).as("check starttijd").isEqualTo(actual.getStarttijd()))
            .satisfies(e -> assertThat(e.getTitel()).as("check titel").isEqualTo(actual.getTitel()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVergaderingUpdatableRelationshipsEquals(Vergadering expected, Vergadering actual) {
        assertThat(expected)
            .as("Verify Vergadering relationships")
            .satisfies(
                e -> assertThat(e.getHeeftverslagRaadsstuk()).as("check heeftverslagRaadsstuk").isEqualTo(actual.getHeeftverslagRaadsstuk())
            )
            .satisfies(
                e -> assertThat(e.getHeeftRaadscommissie()).as("check heeftRaadscommissie").isEqualTo(actual.getHeeftRaadscommissie())
            )
            .satisfies(
                e ->
                    assertThat(e.getWordtbehandeldinRaadsstuks())
                        .as("check wordtbehandeldinRaadsstuks")
                        .isEqualTo(actual.getWordtbehandeldinRaadsstuks())
            );
    }
}
