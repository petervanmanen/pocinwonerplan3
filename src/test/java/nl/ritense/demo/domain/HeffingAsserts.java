package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HeffingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHeffingAllPropertiesEquals(Heffing expected, Heffing actual) {
        assertHeffingAutoGeneratedPropertiesEquals(expected, actual);
        assertHeffingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHeffingAllUpdatablePropertiesEquals(Heffing expected, Heffing actual) {
        assertHeffingUpdatableFieldsEquals(expected, actual);
        assertHeffingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHeffingAutoGeneratedPropertiesEquals(Heffing expected, Heffing actual) {
        assertThat(expected)
            .as("Verify Heffing auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHeffingUpdatableFieldsEquals(Heffing expected, Heffing actual) {
        assertThat(expected)
            .as("Verify Heffing relevant properties")
            .satisfies(e -> assertThat(e.getBedrag()).as("check bedrag").isEqualTo(actual.getBedrag()))
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getDatumindiening()).as("check datumindiening").isEqualTo(actual.getDatumindiening()))
            .satisfies(e -> assertThat(e.getGefactureerd()).as("check gefactureerd").isEqualTo(actual.getGefactureerd()))
            .satisfies(e -> assertThat(e.getInrekening()).as("check inrekening").isEqualTo(actual.getInrekening()))
            .satisfies(e -> assertThat(e.getNummer()).as("check nummer").isEqualTo(actual.getNummer()))
            .satisfies(e -> assertThat(e.getRunnummer()).as("check runnummer").isEqualTo(actual.getRunnummer()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHeffingUpdatableRelationshipsEquals(Heffing expected, Heffing actual) {
        assertThat(expected)
            .as("Verify Heffing relationships")
            .satisfies(
                e ->
                    assertThat(e.getHeeftgrondslagHeffinggrondslag())
                        .as("check heeftgrondslagHeffinggrondslag")
                        .isEqualTo(actual.getHeeftgrondslagHeffinggrondslag())
            );
    }
}