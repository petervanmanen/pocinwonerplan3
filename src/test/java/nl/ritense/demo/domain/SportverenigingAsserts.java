package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SportverenigingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportverenigingAllPropertiesEquals(Sportvereniging expected, Sportvereniging actual) {
        assertSportverenigingAutoGeneratedPropertiesEquals(expected, actual);
        assertSportverenigingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportverenigingAllUpdatablePropertiesEquals(Sportvereniging expected, Sportvereniging actual) {
        assertSportverenigingUpdatableFieldsEquals(expected, actual);
        assertSportverenigingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportverenigingAutoGeneratedPropertiesEquals(Sportvereniging expected, Sportvereniging actual) {
        assertThat(expected)
            .as("Verify Sportvereniging auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportverenigingUpdatableFieldsEquals(Sportvereniging expected, Sportvereniging actual) {
        assertThat(expected)
            .as("Verify Sportvereniging relevant properties")
            .satisfies(e -> assertThat(e.getAantalnormteams()).as("check aantalnormteams").isEqualTo(actual.getAantalnormteams()))
            .satisfies(e -> assertThat(e.getAdres()).as("check adres").isEqualTo(actual.getAdres()))
            .satisfies(e -> assertThat(e.getBinnensport()).as("check binnensport").isEqualTo(actual.getBinnensport()))
            .satisfies(e -> assertThat(e.getBuitensport()).as("check buitensport").isEqualTo(actual.getBuitensport()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getLedenaantal()).as("check ledenaantal").isEqualTo(actual.getLedenaantal()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getTypesport()).as("check typesport").isEqualTo(actual.getTypesport()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSportverenigingUpdatableRelationshipsEquals(Sportvereniging expected, Sportvereniging actual) {
        assertThat(expected)
            .as("Verify Sportvereniging relationships")
            .satisfies(e -> assertThat(e.getOefentuitSports()).as("check oefentuitSports").isEqualTo(actual.getOefentuitSports()))
            .satisfies(
                e -> assertThat(e.getGebruiktSportlocaties()).as("check gebruiktSportlocaties").isEqualTo(actual.getGebruiktSportlocaties())
            );
    }
}
