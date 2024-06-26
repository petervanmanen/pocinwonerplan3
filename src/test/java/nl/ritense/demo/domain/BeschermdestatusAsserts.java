package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BeschermdestatusAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeschermdestatusAllPropertiesEquals(Beschermdestatus expected, Beschermdestatus actual) {
        assertBeschermdestatusAutoGeneratedPropertiesEquals(expected, actual);
        assertBeschermdestatusAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeschermdestatusAllUpdatablePropertiesEquals(Beschermdestatus expected, Beschermdestatus actual) {
        assertBeschermdestatusUpdatableFieldsEquals(expected, actual);
        assertBeschermdestatusUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeschermdestatusAutoGeneratedPropertiesEquals(Beschermdestatus expected, Beschermdestatus actual) {
        assertThat(expected)
            .as("Verify Beschermdestatus auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeschermdestatusUpdatableFieldsEquals(Beschermdestatus expected, Beschermdestatus actual) {
        assertThat(expected)
            .as("Verify Beschermdestatus relevant properties")
            .satisfies(e -> assertThat(e.getBronnen()).as("check bronnen").isEqualTo(actual.getBronnen()))
            .satisfies(e -> assertThat(e.getComplex()).as("check complex").isEqualTo(actual.getComplex()))
            .satisfies(
                e ->
                    assertThat(e.getDatuminschrijvingregister())
                        .as("check datuminschrijvingregister")
                        .isEqualTo(actual.getDatuminschrijvingregister())
            )
            .satisfies(
                e ->
                    assertThat(e.getGemeentelijkmonumentcode())
                        .as("check gemeentelijkmonumentcode")
                        .isEqualTo(actual.getGemeentelijkmonumentcode())
            )
            .satisfies(e -> assertThat(e.getGezichtscode()).as("check gezichtscode").isEqualTo(actual.getGezichtscode()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getOpmerkingen()).as("check opmerkingen").isEqualTo(actual.getOpmerkingen()))
            .satisfies(e -> assertThat(e.getRijksmonumentcode()).as("check rijksmonumentcode").isEqualTo(actual.getRijksmonumentcode()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBeschermdestatusUpdatableRelationshipsEquals(Beschermdestatus expected, Beschermdestatus actual) {}
}
