package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OnderwijssoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijssoortAllPropertiesEquals(Onderwijssoort expected, Onderwijssoort actual) {
        assertOnderwijssoortAutoGeneratedPropertiesEquals(expected, actual);
        assertOnderwijssoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijssoortAllUpdatablePropertiesEquals(Onderwijssoort expected, Onderwijssoort actual) {
        assertOnderwijssoortUpdatableFieldsEquals(expected, actual);
        assertOnderwijssoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijssoortAutoGeneratedPropertiesEquals(Onderwijssoort expected, Onderwijssoort actual) {
        assertThat(expected)
            .as("Verify Onderwijssoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijssoortUpdatableFieldsEquals(Onderwijssoort expected, Onderwijssoort actual) {
        assertThat(expected)
            .as("Verify Onderwijssoort relevant properties")
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getOnderwijstype()).as("check onderwijstype").isEqualTo(actual.getOnderwijstype()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOnderwijssoortUpdatableRelationshipsEquals(Onderwijssoort expected, Onderwijssoort actual) {
        assertThat(expected)
            .as("Verify Onderwijssoort relationships")
            .satisfies(e -> assertThat(e.getHeeftSchools()).as("check heeftSchools").isEqualTo(actual.getHeeftSchools()));
    }
}
