package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class MjopitemAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMjopitemAllPropertiesEquals(Mjopitem expected, Mjopitem actual) {
        assertMjopitemAutoGeneratedPropertiesEquals(expected, actual);
        assertMjopitemAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMjopitemAllUpdatablePropertiesEquals(Mjopitem expected, Mjopitem actual) {
        assertMjopitemUpdatableFieldsEquals(expected, actual);
        assertMjopitemUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMjopitemAutoGeneratedPropertiesEquals(Mjopitem expected, Mjopitem actual) {
        assertThat(expected)
            .as("Verify Mjopitem auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMjopitemUpdatableFieldsEquals(Mjopitem expected, Mjopitem actual) {
        assertThat(expected)
            .as("Verify Mjopitem relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(
                e ->
                    assertThat(e.getDatumopzeggingaanbieder())
                        .as("check datumopzeggingaanbieder")
                        .isEqualTo(actual.getDatumopzeggingaanbieder())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumopzeggingontvanger())
                        .as("check datumopzeggingontvanger")
                        .isEqualTo(actual.getDatumopzeggingontvanger())
            )
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()))
            .satisfies(e -> assertThat(e.getKosten()).as("check kosten").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getKosten()))
            .satisfies(e -> assertThat(e.getOmschrijving()).as("check omschrijving").isEqualTo(actual.getOmschrijving()))
            .satisfies(
                e -> assertThat(e.getOpzegtermijnaanbieder()).as("check opzegtermijnaanbieder").isEqualTo(actual.getOpzegtermijnaanbieder())
            )
            .satisfies(
                e -> assertThat(e.getOpzegtermijnontvanger()).as("check opzegtermijnontvanger").isEqualTo(actual.getOpzegtermijnontvanger())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMjopitemUpdatableRelationshipsEquals(Mjopitem expected, Mjopitem actual) {}
}
