package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VerzuimmeldingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerzuimmeldingAllPropertiesEquals(Verzuimmelding expected, Verzuimmelding actual) {
        assertVerzuimmeldingAutoGeneratedPropertiesEquals(expected, actual);
        assertVerzuimmeldingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerzuimmeldingAllUpdatablePropertiesEquals(Verzuimmelding expected, Verzuimmelding actual) {
        assertVerzuimmeldingUpdatableFieldsEquals(expected, actual);
        assertVerzuimmeldingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerzuimmeldingAutoGeneratedPropertiesEquals(Verzuimmelding expected, Verzuimmelding actual) {
        assertThat(expected)
            .as("Verify Verzuimmelding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerzuimmeldingUpdatableFieldsEquals(Verzuimmelding expected, Verzuimmelding actual) {
        assertThat(expected)
            .as("Verify Verzuimmelding relevant properties")
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()))
            .satisfies(e -> assertThat(e.getVoorstelschool()).as("check voorstelschool").isEqualTo(actual.getVoorstelschool()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVerzuimmeldingUpdatableRelationshipsEquals(Verzuimmelding expected, Verzuimmelding actual) {
        assertThat(expected)
            .as("Verify Verzuimmelding relationships")
            .satisfies(e -> assertThat(e.getHeeftSchool()).as("check heeftSchool").isEqualTo(actual.getHeeftSchool()))
            .satisfies(e -> assertThat(e.getHeeftLeerling()).as("check heeftLeerling").isEqualTo(actual.getHeeftLeerling()));
    }
}
