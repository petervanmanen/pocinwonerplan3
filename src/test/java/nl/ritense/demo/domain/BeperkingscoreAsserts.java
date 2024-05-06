package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BeperkingscoreAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoreAllPropertiesEquals(Beperkingscore expected, Beperkingscore actual) {
        assertBeperkingscoreAutoGeneratedPropertiesEquals(expected, actual);
        assertBeperkingscoreAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoreAllUpdatablePropertiesEquals(Beperkingscore expected, Beperkingscore actual) {
        assertBeperkingscoreUpdatableFieldsEquals(expected, actual);
        assertBeperkingscoreUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoreAutoGeneratedPropertiesEquals(Beperkingscore expected, Beperkingscore actual) {
        assertThat(expected)
            .as("Verify Beperkingscore auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoreUpdatableFieldsEquals(Beperkingscore expected, Beperkingscore actual) {
        assertThat(expected)
            .as("Verify Beperkingscore relevant properties")
            .satisfies(e -> assertThat(e.getCommentaar()).as("check commentaar").isEqualTo(actual.getCommentaar()))
            .satisfies(e -> assertThat(e.getScore()).as("check score").isEqualTo(actual.getScore()))
            .satisfies(e -> assertThat(e.getWet()).as("check wet").isEqualTo(actual.getWet()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeperkingscoreUpdatableRelationshipsEquals(Beperkingscore expected, Beperkingscore actual) {
        assertThat(expected)
            .as("Verify Beperkingscore relationships")
            .satisfies(
                e ->
                    assertThat(e.getIseenBeperkingscoresoort())
                        .as("check iseenBeperkingscoresoort")
                        .isEqualTo(actual.getIseenBeperkingscoresoort())
            )
            .satisfies(e -> assertThat(e.getEmptyBeperking()).as("check emptyBeperking").isEqualTo(actual.getEmptyBeperking()));
    }
}