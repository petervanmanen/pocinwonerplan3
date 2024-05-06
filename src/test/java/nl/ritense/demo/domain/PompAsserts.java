package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PompAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPompAllPropertiesEquals(Pomp expected, Pomp actual) {
        assertPompAutoGeneratedPropertiesEquals(expected, actual);
        assertPompAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPompAllUpdatablePropertiesEquals(Pomp expected, Pomp actual) {
        assertPompUpdatableFieldsEquals(expected, actual);
        assertPompUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPompAutoGeneratedPropertiesEquals(Pomp expected, Pomp actual) {
        assertThat(expected)
            .as("Verify Pomp auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPompUpdatableFieldsEquals(Pomp expected, Pomp actual) {
        assertThat(expected)
            .as("Verify Pomp relevant properties")
            .satisfies(e -> assertThat(e.getAanslagniveau()).as("check aanslagniveau").isEqualTo(actual.getAanslagniveau()))
            .satisfies(
                e ->
                    assertThat(e.getBeginstanddraaiurenteller())
                        .as("check beginstanddraaiurenteller")
                        .isEqualTo(actual.getBeginstanddraaiurenteller())
            )
            .satisfies(e -> assertThat(e.getBesturingskast()).as("check besturingskast").isEqualTo(actual.getBesturingskast()))
            .satisfies(
                e ->
                    assertThat(e.getLaatstestanddraaiurenteller())
                        .as("check laatstestanddraaiurenteller")
                        .isEqualTo(actual.getLaatstestanddraaiurenteller())
            )
            .satisfies(
                e -> assertThat(e.getLaatstestandkwhteller()).as("check laatstestandkwhteller").isEqualTo(actual.getLaatstestandkwhteller())
            )
            .satisfies(e -> assertThat(e.getLevensduur()).as("check levensduur").isEqualTo(actual.getLevensduur()))
            .satisfies(e -> assertThat(e.getModel()).as("check model").isEqualTo(actual.getModel()))
            .satisfies(e -> assertThat(e.getMotorvermogen()).as("check motorvermogen").isEqualTo(actual.getMotorvermogen()))
            .satisfies(e -> assertThat(e.getOnderdeelmetpomp()).as("check onderdeelmetpomp").isEqualTo(actual.getOnderdeelmetpomp()))
            .satisfies(e -> assertThat(e.getOntwerpcapaciteit()).as("check ontwerpcapaciteit").isEqualTo(actual.getOntwerpcapaciteit()))
            .satisfies(e -> assertThat(e.getPompcapaciteit()).as("check pompcapaciteit").isEqualTo(actual.getPompcapaciteit()))
            .satisfies(e -> assertThat(e.getSerienummer()).as("check serienummer").isEqualTo(actual.getSerienummer()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()))
            .satisfies(
                e -> assertThat(e.getTypeonderdeelmetpomp()).as("check typeonderdeelmetpomp").isEqualTo(actual.getTypeonderdeelmetpomp())
            )
            .satisfies(e -> assertThat(e.getTypeplus()).as("check typeplus").isEqualTo(actual.getTypeplus()))
            .satisfies(e -> assertThat(e.getTypewaaier()).as("check typewaaier").isEqualTo(actual.getTypewaaier()))
            .satisfies(e -> assertThat(e.getUitslagpeil()).as("check uitslagpeil").isEqualTo(actual.getUitslagpeil()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPompUpdatableRelationshipsEquals(Pomp expected, Pomp actual) {}
}
