package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class BetaalmomentAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBetaalmomentAllPropertiesEquals(Betaalmoment expected, Betaalmoment actual) {
        assertBetaalmomentAutoGeneratedPropertiesEquals(expected, actual);
        assertBetaalmomentAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBetaalmomentAllUpdatablePropertiesEquals(Betaalmoment expected, Betaalmoment actual) {
        assertBetaalmomentUpdatableFieldsEquals(expected, actual);
        assertBetaalmomentUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBetaalmomentAutoGeneratedPropertiesEquals(Betaalmoment expected, Betaalmoment actual) {
        assertThat(expected)
            .as("Verify Betaalmoment auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBetaalmomentUpdatableFieldsEquals(Betaalmoment expected, Betaalmoment actual) {
        assertThat(expected)
            .as("Verify Betaalmoment relevant properties")
            .satisfies(e -> assertThat(e.getBedrag()).as("check bedrag").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getBedrag()))
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()))
            .satisfies(e -> assertThat(e.getVoorschot()).as("check voorschot").isEqualTo(actual.getVoorschot()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBetaalmomentUpdatableRelationshipsEquals(Betaalmoment expected, Betaalmoment actual) {
        assertThat(expected)
            .as("Verify Betaalmoment relationships")
            .satisfies(
                e ->
                    assertThat(e.getHeeftSubsidiecomponent())
                        .as("check heeftSubsidiecomponent")
                        .isEqualTo(actual.getHeeftSubsidiecomponent())
            );
    }
}