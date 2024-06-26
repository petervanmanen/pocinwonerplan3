package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class InschrijvingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInschrijvingAllPropertiesEquals(Inschrijving expected, Inschrijving actual) {
        assertInschrijvingAutoGeneratedPropertiesEquals(expected, actual);
        assertInschrijvingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInschrijvingAllUpdatablePropertiesEquals(Inschrijving expected, Inschrijving actual) {
        assertInschrijvingUpdatableFieldsEquals(expected, actual);
        assertInschrijvingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInschrijvingAutoGeneratedPropertiesEquals(Inschrijving expected, Inschrijving actual) {
        assertThat(expected)
            .as("Verify Inschrijving auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInschrijvingUpdatableFieldsEquals(Inschrijving expected, Inschrijving actual) {
        assertThat(expected)
            .as("Verify Inschrijving relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInschrijvingUpdatableRelationshipsEquals(Inschrijving expected, Inschrijving actual) {
        assertThat(expected)
            .as("Verify Inschrijving relationships")
            .satisfies(e -> assertThat(e.getHeeftSchool()).as("check heeftSchool").isEqualTo(actual.getHeeftSchool()))
            .satisfies(
                e -> assertThat(e.getBetreftAanbesteding()).as("check betreftAanbesteding").isEqualTo(actual.getBetreftAanbesteding())
            )
            .satisfies(e -> assertThat(e.getHeeftLeerling()).as("check heeftLeerling").isEqualTo(actual.getHeeftLeerling()))
            .satisfies(e -> assertThat(e.getHeeftLeverancier()).as("check heeftLeverancier").isEqualTo(actual.getHeeftLeverancier()));
    }
}
