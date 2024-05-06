package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class SubsidieaanvraagAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubsidieaanvraagAllPropertiesEquals(Subsidieaanvraag expected, Subsidieaanvraag actual) {
        assertSubsidieaanvraagAutoGeneratedPropertiesEquals(expected, actual);
        assertSubsidieaanvraagAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubsidieaanvraagAllUpdatablePropertiesEquals(Subsidieaanvraag expected, Subsidieaanvraag actual) {
        assertSubsidieaanvraagUpdatableFieldsEquals(expected, actual);
        assertSubsidieaanvraagUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubsidieaanvraagAutoGeneratedPropertiesEquals(Subsidieaanvraag expected, Subsidieaanvraag actual) {
        assertThat(expected)
            .as("Verify Subsidieaanvraag auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubsidieaanvraagUpdatableFieldsEquals(Subsidieaanvraag expected, Subsidieaanvraag actual) {
        assertThat(expected)
            .as("Verify Subsidieaanvraag relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getAangevraagdbedrag())
                        .as("check aangevraagdbedrag")
                        .usingComparator(bigDecimalCompareTo)
                        .isEqualTo(actual.getAangevraagdbedrag())
            )
            .satisfies(e -> assertThat(e.getDatumindiening()).as("check datumindiening").isEqualTo(actual.getDatumindiening()))
            .satisfies(e -> assertThat(e.getKenmerk()).as("check kenmerk").isEqualTo(actual.getKenmerk()))
            .satisfies(
                e -> assertThat(e.getOntvangstbevestiging()).as("check ontvangstbevestiging").isEqualTo(actual.getOntvangstbevestiging())
            )
            .satisfies(
                e -> assertThat(e.getVerwachtebeschikking()).as("check verwachtebeschikking").isEqualTo(actual.getVerwachtebeschikking())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubsidieaanvraagUpdatableRelationshipsEquals(Subsidieaanvraag expected, Subsidieaanvraag actual) {
        assertThat(expected)
            .as("Verify Subsidieaanvraag relationships")
            .satisfies(e -> assertThat(e.getBetreftSubsidie()).as("check betreftSubsidie").isEqualTo(actual.getBetreftSubsidie()))
            .satisfies(
                e ->
                    assertThat(e.getMondtuitSubsidiebeschikking())
                        .as("check mondtuitSubsidiebeschikking")
                        .isEqualTo(actual.getMondtuitSubsidiebeschikking())
            );
    }
}