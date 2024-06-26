package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class NotitieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotitieAllPropertiesEquals(Notitie expected, Notitie actual) {
        assertNotitieAutoGeneratedPropertiesEquals(expected, actual);
        assertNotitieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotitieAllUpdatablePropertiesEquals(Notitie expected, Notitie actual) {
        assertNotitieUpdatableFieldsEquals(expected, actual);
        assertNotitieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotitieAutoGeneratedPropertiesEquals(Notitie expected, Notitie actual) {
        assertThat(expected)
            .as("Verify Notitie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotitieUpdatableFieldsEquals(Notitie expected, Notitie actual) {
        assertThat(expected)
            .as("Verify Notitie relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()))
            .satisfies(e -> assertThat(e.getInhoud()).as("check inhoud").isEqualTo(actual.getInhoud()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotitieUpdatableRelationshipsEquals(Notitie expected, Notitie actual) {
        assertThat(expected)
            .as("Verify Notitie relationships")
            .satisfies(e -> assertThat(e.getAuteurMedewerker()).as("check auteurMedewerker").isEqualTo(actual.getAuteurMedewerker()))
            .satisfies(
                e ->
                    assertThat(e.getHeeftnotitiesApplicatie())
                        .as("check heeftnotitiesApplicatie")
                        .isEqualTo(actual.getHeeftnotitiesApplicatie())
            );
    }
}
