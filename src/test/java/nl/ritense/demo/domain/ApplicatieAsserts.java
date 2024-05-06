package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicatieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertApplicatieAllPropertiesEquals(Applicatie expected, Applicatie actual) {
        assertApplicatieAutoGeneratedPropertiesEquals(expected, actual);
        assertApplicatieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertApplicatieAllUpdatablePropertiesEquals(Applicatie expected, Applicatie actual) {
        assertApplicatieUpdatableFieldsEquals(expected, actual);
        assertApplicatieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertApplicatieAutoGeneratedPropertiesEquals(Applicatie expected, Applicatie actual) {
        assertThat(expected)
            .as("Verify Applicatie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertApplicatieUpdatableFieldsEquals(Applicatie expected, Applicatie actual) {
        assertThat(expected)
            .as("Verify Applicatie relevant properties")
            .satisfies(e -> assertThat(e.getApplicatieurl()).as("check applicatieurl").isEqualTo(actual.getApplicatieurl()))
            .satisfies(e -> assertThat(e.getBeheerstatus()).as("check beheerstatus").isEqualTo(actual.getBeheerstatus()))
            .satisfies(e -> assertThat(e.getBeleidsdomein()).as("check beleidsdomein").isEqualTo(actual.getBeleidsdomein()))
            .satisfies(e -> assertThat(e.getCategorie()).as("check categorie").isEqualTo(actual.getCategorie()))
            .satisfies(e -> assertThat(e.getGuid()).as("check guid").isEqualTo(actual.getGuid()))
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(e -> assertThat(e.getPackagingstatus()).as("check packagingstatus").isEqualTo(actual.getPackagingstatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertApplicatieUpdatableRelationshipsEquals(Applicatie expected, Applicatie actual) {
        assertThat(expected)
            .as("Verify Applicatie relationships")
            .satisfies(
                e ->
                    assertThat(e.getHeeftleverancierLeverancier())
                        .as("check heeftleverancierLeverancier")
                        .isEqualTo(actual.getHeeftleverancierLeverancier())
            )
            .satisfies(
                e ->
                    assertThat(e.getHeeftdocumentenDocuments())
                        .as("check heeftdocumentenDocuments")
                        .isEqualTo(actual.getHeeftdocumentenDocuments())
            )
            .satisfies(e -> assertThat(e.getRollenMedewerkers()).as("check rollenMedewerkers").isEqualTo(actual.getRollenMedewerkers()));
    }
}
