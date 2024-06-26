package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OphaalmomentAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOphaalmomentAllPropertiesEquals(Ophaalmoment expected, Ophaalmoment actual) {
        assertOphaalmomentAutoGeneratedPropertiesEquals(expected, actual);
        assertOphaalmomentAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOphaalmomentAllUpdatablePropertiesEquals(Ophaalmoment expected, Ophaalmoment actual) {
        assertOphaalmomentUpdatableFieldsEquals(expected, actual);
        assertOphaalmomentUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOphaalmomentAutoGeneratedPropertiesEquals(Ophaalmoment expected, Ophaalmoment actual) {
        assertThat(expected)
            .as("Verify Ophaalmoment auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOphaalmomentUpdatableFieldsEquals(Ophaalmoment expected, Ophaalmoment actual) {
        assertThat(expected)
            .as("Verify Ophaalmoment relevant properties")
            .satisfies(e -> assertThat(e.getGewichtstoename()).as("check gewichtstoename").isEqualTo(actual.getGewichtstoename()))
            .satisfies(e -> assertThat(e.getTijdstip()).as("check tijdstip").isEqualTo(actual.getTijdstip()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOphaalmomentUpdatableRelationshipsEquals(Ophaalmoment expected, Ophaalmoment actual) {
        assertThat(expected)
            .as("Verify Ophaalmoment relationships")
            .satisfies(e -> assertThat(e.getGelostContainer()).as("check gelostContainer").isEqualTo(actual.getGelostContainer()))
            .satisfies(e -> assertThat(e.getGestoptopLocatie()).as("check gestoptopLocatie").isEqualTo(actual.getGestoptopLocatie()))
            .satisfies(e -> assertThat(e.getHeeftRit()).as("check heeftRit").isEqualTo(actual.getHeeftRit()));
    }
}
