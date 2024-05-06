package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class ParkeerrechtAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParkeerrechtAllPropertiesEquals(Parkeerrecht expected, Parkeerrecht actual) {
        assertParkeerrechtAutoGeneratedPropertiesEquals(expected, actual);
        assertParkeerrechtAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParkeerrechtAllUpdatablePropertiesEquals(Parkeerrecht expected, Parkeerrecht actual) {
        assertParkeerrechtUpdatableFieldsEquals(expected, actual);
        assertParkeerrechtUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParkeerrechtAutoGeneratedPropertiesEquals(Parkeerrecht expected, Parkeerrecht actual) {
        assertThat(expected)
            .as("Verify Parkeerrecht auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParkeerrechtUpdatableFieldsEquals(Parkeerrecht expected, Parkeerrecht actual) {
        assertThat(expected)
            .as("Verify Parkeerrecht relevant properties")
            .satisfies(e -> assertThat(e.getAanmaaktijd()).as("check aanmaaktijd").isEqualTo(actual.getAanmaaktijd()))
            .satisfies(
                e ->
                    assertThat(e.getBedragaankoop())
                        .as("check bedragaankoop")
                        .usingComparator(bigDecimalCompareTo)
                        .isEqualTo(actual.getBedragaankoop())
            )
            .satisfies(
                e ->
                    assertThat(e.getBedragbtw()).as("check bedragbtw").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getBedragbtw())
            )
            .satisfies(e -> assertThat(e.getDatumtijdeinde()).as("check datumtijdeinde").isEqualTo(actual.getDatumtijdeinde()))
            .satisfies(e -> assertThat(e.getDatumtijdstart()).as("check datumtijdstart").isEqualTo(actual.getDatumtijdstart()))
            .satisfies(e -> assertThat(e.getProductnaam()).as("check productnaam").isEqualTo(actual.getProductnaam()))
            .satisfies(
                e -> assertThat(e.getProductomschrijving()).as("check productomschrijving").isEqualTo(actual.getProductomschrijving())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertParkeerrechtUpdatableRelationshipsEquals(Parkeerrecht expected, Parkeerrecht actual) {
        assertThat(expected)
            .as("Verify Parkeerrecht relationships")
            .satisfies(
                e ->
                    assertThat(e.getLeverancierBelprovider())
                        .as("check leverancierBelprovider")
                        .isEqualTo(actual.getLeverancierBelprovider())
            )
            .satisfies(e -> assertThat(e.getBetreftVoertuig()).as("check betreftVoertuig").isEqualTo(actual.getBetreftVoertuig()));
    }
}