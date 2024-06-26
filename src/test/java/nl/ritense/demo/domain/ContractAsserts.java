package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContractAllPropertiesEquals(Contract expected, Contract actual) {
        assertContractAutoGeneratedPropertiesEquals(expected, actual);
        assertContractAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContractAllUpdatablePropertiesEquals(Contract expected, Contract actual) {
        assertContractUpdatableFieldsEquals(expected, actual);
        assertContractUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContractAutoGeneratedPropertiesEquals(Contract expected, Contract actual) {
        assertThat(expected)
            .as("Verify Contract auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContractUpdatableFieldsEquals(Contract expected, Contract actual) {
        assertThat(expected)
            .as("Verify Contract relevant properties")
            .satisfies(e -> assertThat(e.getAutorisatiegroep()).as("check autorisatiegroep").isEqualTo(actual.getAutorisatiegroep()))
            .satisfies(e -> assertThat(e.getBeschrijving()).as("check beschrijving").isEqualTo(actual.getBeschrijving()))
            .satisfies(e -> assertThat(e.getCategorie()).as("check categorie").isEqualTo(actual.getCategorie()))
            .satisfies(e -> assertThat(e.getClassificatie()).as("check classificatie").isEqualTo(actual.getClassificatie()))
            .satisfies(e -> assertThat(e.getContractrevisie()).as("check contractrevisie").isEqualTo(actual.getContractrevisie()))
            .satisfies(e -> assertThat(e.getDatumcreatie()).as("check datumcreatie").isEqualTo(actual.getDatumcreatie()))
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()))
            .satisfies(e -> assertThat(e.getGroep()).as("check groep").isEqualTo(actual.getGroep()))
            .satisfies(e -> assertThat(e.getInterncontractid()).as("check interncontractid").isEqualTo(actual.getInterncontractid()))
            .satisfies(
                e -> assertThat(e.getInterncontractrevisie()).as("check interncontractrevisie").isEqualTo(actual.getInterncontractrevisie())
            )
            .satisfies(e -> assertThat(e.getOpmerkingen()).as("check opmerkingen").isEqualTo(actual.getOpmerkingen()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()))
            .satisfies(e -> assertThat(e.getVoorwaarde()).as("check voorwaarde").isEqualTo(actual.getVoorwaarde()))
            .satisfies(e -> assertThat(e.getZoekwoorden()).as("check zoekwoorden").isEqualTo(actual.getZoekwoorden()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContractUpdatableRelationshipsEquals(Contract expected, Contract actual) {
        assertThat(expected)
            .as("Verify Contract relationships")
            .satisfies(
                e -> assertThat(e.getBovenliggendContract()).as("check bovenliggendContract").isEqualTo(actual.getBovenliggendContract())
            )
            .satisfies(e -> assertThat(e.getHeeftLeverancier()).as("check heeftLeverancier").isEqualTo(actual.getHeeftLeverancier()))
            .satisfies(
                e ->
                    assertThat(e.getContractantLeverancier())
                        .as("check contractantLeverancier")
                        .isEqualTo(actual.getContractantLeverancier())
            );
    }
}
