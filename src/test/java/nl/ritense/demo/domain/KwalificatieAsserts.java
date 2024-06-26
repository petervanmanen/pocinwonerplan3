package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KwalificatieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKwalificatieAllPropertiesEquals(Kwalificatie expected, Kwalificatie actual) {
        assertKwalificatieAutoGeneratedPropertiesEquals(expected, actual);
        assertKwalificatieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKwalificatieAllUpdatablePropertiesEquals(Kwalificatie expected, Kwalificatie actual) {
        assertKwalificatieUpdatableFieldsEquals(expected, actual);
        assertKwalificatieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKwalificatieAutoGeneratedPropertiesEquals(Kwalificatie expected, Kwalificatie actual) {
        assertThat(expected)
            .as("Verify Kwalificatie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKwalificatieUpdatableFieldsEquals(Kwalificatie expected, Kwalificatie actual) {
        assertThat(expected)
            .as("Verify Kwalificatie relevant properties")
            .satisfies(e -> assertThat(e.getEindegeldigheid()).as("check eindegeldigheid").isEqualTo(actual.getEindegeldigheid()))
            .satisfies(e -> assertThat(e.getStartgeldigheid()).as("check startgeldigheid").isEqualTo(actual.getStartgeldigheid()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKwalificatieUpdatableRelationshipsEquals(Kwalificatie expected, Kwalificatie actual) {
        assertThat(expected)
            .as("Verify Kwalificatie relationships")
            .satisfies(
                e -> assertThat(e.getBetreftAanbesteding()).as("check betreftAanbesteding").isEqualTo(actual.getBetreftAanbesteding())
            )
            .satisfies(e -> assertThat(e.getHeeftLeverancier()).as("check heeftLeverancier").isEqualTo(actual.getHeeftLeverancier()));
    }
}
