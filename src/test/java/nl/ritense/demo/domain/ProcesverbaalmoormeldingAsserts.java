package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcesverbaalmoormeldingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProcesverbaalmoormeldingAllPropertiesEquals(
        Procesverbaalmoormelding expected,
        Procesverbaalmoormelding actual
    ) {
        assertProcesverbaalmoormeldingAutoGeneratedPropertiesEquals(expected, actual);
        assertProcesverbaalmoormeldingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProcesverbaalmoormeldingAllUpdatablePropertiesEquals(
        Procesverbaalmoormelding expected,
        Procesverbaalmoormelding actual
    ) {
        assertProcesverbaalmoormeldingUpdatableFieldsEquals(expected, actual);
        assertProcesverbaalmoormeldingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProcesverbaalmoormeldingAutoGeneratedPropertiesEquals(
        Procesverbaalmoormelding expected,
        Procesverbaalmoormelding actual
    ) {
        assertThat(expected)
            .as("Verify Procesverbaalmoormelding auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProcesverbaalmoormeldingUpdatableFieldsEquals(
        Procesverbaalmoormelding expected,
        Procesverbaalmoormelding actual
    ) {
        assertThat(expected)
            .as("Verify Procesverbaalmoormelding relevant properties")
            .satisfies(e -> assertThat(e.getDatum()).as("check datum").isEqualTo(actual.getDatum()))
            .satisfies(e -> assertThat(e.getGoedkeuring()).as("check goedkeuring").isEqualTo(actual.getGoedkeuring()))
            .satisfies(e -> assertThat(e.getOpmerkingen()).as("check opmerkingen").isEqualTo(actual.getOpmerkingen()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProcesverbaalmoormeldingUpdatableRelationshipsEquals(
        Procesverbaalmoormelding expected,
        Procesverbaalmoormelding actual
    ) {}
}
