package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ParticipatiedossierAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParticipatiedossierAllPropertiesEquals(Participatiedossier expected, Participatiedossier actual) {
        assertParticipatiedossierAutoGeneratedPropertiesEquals(expected, actual);
        assertParticipatiedossierAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParticipatiedossierAllUpdatablePropertiesEquals(Participatiedossier expected, Participatiedossier actual) {
        assertParticipatiedossierUpdatableFieldsEquals(expected, actual);
        assertParticipatiedossierUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParticipatiedossierAutoGeneratedPropertiesEquals(Participatiedossier expected, Participatiedossier actual) {
        assertThat(expected)
            .as("Verify Participatiedossier auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParticipatiedossierUpdatableFieldsEquals(Participatiedossier expected, Participatiedossier actual) {
        assertThat(expected)
            .as("Verify Participatiedossier relevant properties")
            .satisfies(e -> assertThat(e.getArbeidsvermogen()).as("check arbeidsvermogen").isEqualTo(actual.getArbeidsvermogen()))
            .satisfies(e -> assertThat(e.getBegeleiderscode()).as("check begeleiderscode").isEqualTo(actual.getBegeleiderscode()))
            .satisfies(e -> assertThat(e.getBeschutwerk()).as("check beschutwerk").isEqualTo(actual.getBeschutwerk()))
            .satisfies(e -> assertThat(e.getClientbegeleiderid()).as("check clientbegeleiderid").isEqualTo(actual.getClientbegeleiderid()))
            .satisfies(
                e -> assertThat(e.getDatumeindebegeleiding()).as("check datumeindebegeleiding").isEqualTo(actual.getDatumeindebegeleiding())
            )
            .satisfies(
                e -> assertThat(e.getDatumstartbegeleiding()).as("check datumstartbegeleiding").isEqualTo(actual.getDatumstartbegeleiding())
            )
            .satisfies(
                e ->
                    assertThat(e.getIndicatiedoelgroepregister())
                        .as("check indicatiedoelgroepregister")
                        .isEqualTo(actual.getIndicatiedoelgroepregister())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParticipatiedossierUpdatableRelationshipsEquals(Participatiedossier expected, Participatiedossier actual) {
        assertThat(expected)
            .as("Verify Participatiedossier relationships")
            .satisfies(
                e -> assertThat(e.getEmptyClientbegeleider()).as("check emptyClientbegeleider").isEqualTo(actual.getEmptyClientbegeleider())
            );
    }
}
