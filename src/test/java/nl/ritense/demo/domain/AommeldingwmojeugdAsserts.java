package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AommeldingwmojeugdAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAommeldingwmojeugdAllPropertiesEquals(Aommeldingwmojeugd expected, Aommeldingwmojeugd actual) {
        assertAommeldingwmojeugdAutoGeneratedPropertiesEquals(expected, actual);
        assertAommeldingwmojeugdAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAommeldingwmojeugdAllUpdatablePropertiesEquals(Aommeldingwmojeugd expected, Aommeldingwmojeugd actual) {
        assertAommeldingwmojeugdUpdatableFieldsEquals(expected, actual);
        assertAommeldingwmojeugdUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAommeldingwmojeugdAutoGeneratedPropertiesEquals(Aommeldingwmojeugd expected, Aommeldingwmojeugd actual) {
        assertThat(expected)
            .as("Verify Aommeldingwmojeugd auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAommeldingwmojeugdUpdatableFieldsEquals(Aommeldingwmojeugd expected, Aommeldingwmojeugd actual) {
        assertThat(expected)
            .as("Verify Aommeldingwmojeugd relevant properties")
            .satisfies(e -> assertThat(e.getAanmelder()).as("check aanmelder").isEqualTo(actual.getAanmelder()))
            .satisfies(e -> assertThat(e.getAanmeldingdoor()).as("check aanmeldingdoor").isEqualTo(actual.getAanmeldingdoor()))
            .satisfies(
                e ->
                    assertThat(e.getAanmeldingdoorlandelijk())
                        .as("check aanmeldingdoorlandelijk")
                        .isEqualTo(actual.getAanmeldingdoorlandelijk())
            )
            .satisfies(e -> assertThat(e.getAanmeldwijze()).as("check aanmeldwijze").isEqualTo(actual.getAanmeldwijze()))
            .satisfies(e -> assertThat(e.getDeskundigheid()).as("check deskundigheid").isEqualTo(actual.getDeskundigheid()))
            .satisfies(e -> assertThat(e.getIsclientopdehoogte()).as("check isclientopdehoogte").isEqualTo(actual.getIsclientopdehoogte()))
            .satisfies(e -> assertThat(e.getOnderzoekswijze()).as("check onderzoekswijze").isEqualTo(actual.getOnderzoekswijze()))
            .satisfies(e -> assertThat(e.getRedenafsluiting()).as("check redenafsluiting").isEqualTo(actual.getRedenafsluiting()))
            .satisfies(e -> assertThat(e.getVervolg()).as("check vervolg").isEqualTo(actual.getVervolg()))
            .satisfies(e -> assertThat(e.getVerwezen()).as("check verwezen").isEqualTo(actual.getVerwezen()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAommeldingwmojeugdUpdatableRelationshipsEquals(Aommeldingwmojeugd expected, Aommeldingwmojeugd actual) {}
}
