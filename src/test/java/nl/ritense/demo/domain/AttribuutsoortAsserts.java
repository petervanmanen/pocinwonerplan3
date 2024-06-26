package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AttribuutsoortAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAttribuutsoortAllPropertiesEquals(Attribuutsoort expected, Attribuutsoort actual) {
        assertAttribuutsoortAutoGeneratedPropertiesEquals(expected, actual);
        assertAttribuutsoortAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAttribuutsoortAllUpdatablePropertiesEquals(Attribuutsoort expected, Attribuutsoort actual) {
        assertAttribuutsoortUpdatableFieldsEquals(expected, actual);
        assertAttribuutsoortUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAttribuutsoortAutoGeneratedPropertiesEquals(Attribuutsoort expected, Attribuutsoort actual) {
        assertThat(expected)
            .as("Verify Attribuutsoort auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAttribuutsoortUpdatableFieldsEquals(Attribuutsoort expected, Attribuutsoort actual) {
        assertThat(expected)
            .as("Verify Attribuutsoort relevant properties")
            .satisfies(e -> assertThat(e.getAuthentiek()).as("check authentiek").isEqualTo(actual.getAuthentiek()))
            .satisfies(e -> assertThat(e.getDatumopname()).as("check datumopname").isEqualTo(actual.getDatumopname()))
            .satisfies(e -> assertThat(e.getDefinitie()).as("check definitie").isEqualTo(actual.getDefinitie()))
            .satisfies(e -> assertThat(e.getDomein()).as("check domein").isEqualTo(actual.getDomein()))
            .satisfies(e -> assertThat(e.getEaguid()).as("check eaguid").isEqualTo(actual.getEaguid()))
            .satisfies(e -> assertThat(e.getHerkomst()).as("check herkomst").isEqualTo(actual.getHerkomst()))
            .satisfies(e -> assertThat(e.getHerkomstdefinitie()).as("check herkomstdefinitie").isEqualTo(actual.getHerkomstdefinitie()))
            .satisfies(e -> assertThat(e.getIdentificerend()).as("check identificerend").isEqualTo(actual.getIdentificerend()))
            .satisfies(
                e -> assertThat(e.getIndicatieafleidbaar()).as("check indicatieafleidbaar").isEqualTo(actual.getIndicatieafleidbaar())
            )
            .satisfies(
                e ->
                    assertThat(e.getIndicatiematerielehistorie())
                        .as("check indicatiematerielehistorie")
                        .isEqualTo(actual.getIndicatiematerielehistorie())
            )
            .satisfies(e -> assertThat(e.getKardinaliteit()).as("check kardinaliteit").isEqualTo(actual.getKardinaliteit()))
            .satisfies(e -> assertThat(e.getLengte()).as("check lengte").isEqualTo(actual.getLengte()))
            .satisfies(e -> assertThat(e.getMogelijkgeenwaarde()).as("check mogelijkgeenwaarde").isEqualTo(actual.getMogelijkgeenwaarde()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getPatroon()).as("check patroon").isEqualTo(actual.getPatroon()))
            .satisfies(e -> assertThat(e.getPrecisie()).as("check precisie").isEqualTo(actual.getPrecisie()))
            .satisfies(e -> assertThat(e.getStereotype()).as("check stereotype").isEqualTo(actual.getStereotype()))
            .satisfies(e -> assertThat(e.getToelichting()).as("check toelichting").isEqualTo(actual.getToelichting()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAttribuutsoortUpdatableRelationshipsEquals(Attribuutsoort expected, Attribuutsoort actual) {
        assertThat(expected)
            .as("Verify Attribuutsoort relationships")
            .satisfies(e -> assertThat(e.getHeeftDatatype()).as("check heeftDatatype").isEqualTo(actual.getHeeftDatatype()));
    }
}
