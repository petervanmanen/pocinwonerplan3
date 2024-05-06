package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class UitschrijvingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitschrijvingAllPropertiesEquals(Uitschrijving expected, Uitschrijving actual) {
        assertUitschrijvingAutoGeneratedPropertiesEquals(expected, actual);
        assertUitschrijvingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitschrijvingAllUpdatablePropertiesEquals(Uitschrijving expected, Uitschrijving actual) {
        assertUitschrijvingUpdatableFieldsEquals(expected, actual);
        assertUitschrijvingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitschrijvingAutoGeneratedPropertiesEquals(Uitschrijving expected, Uitschrijving actual) {
        assertThat(expected)
            .as("Verify Uitschrijving auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitschrijvingUpdatableFieldsEquals(Uitschrijving expected, Uitschrijving actual) {
        assertThat(expected)
            .as("Verify Uitschrijving relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()))
            .satisfies(e -> assertThat(e.getDiplomabehaald()).as("check diplomabehaald").isEqualTo(actual.getDiplomabehaald()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUitschrijvingUpdatableRelationshipsEquals(Uitschrijving expected, Uitschrijving actual) {
        assertThat(expected)
            .as("Verify Uitschrijving relationships")
            .satisfies(e -> assertThat(e.getHeeftLeerling()).as("check heeftLeerling").isEqualTo(actual.getHeeftLeerling()))
            .satisfies(e -> assertThat(e.getHeeftSchool()).as("check heeftSchool").isEqualTo(actual.getHeeftSchool()));
    }
}