package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KadastralegemeenteAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralegemeenteAllPropertiesEquals(Kadastralegemeente expected, Kadastralegemeente actual) {
        assertKadastralegemeenteAutoGeneratedPropertiesEquals(expected, actual);
        assertKadastralegemeenteAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralegemeenteAllUpdatablePropertiesEquals(Kadastralegemeente expected, Kadastralegemeente actual) {
        assertKadastralegemeenteUpdatableFieldsEquals(expected, actual);
        assertKadastralegemeenteUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralegemeenteAutoGeneratedPropertiesEquals(Kadastralegemeente expected, Kadastralegemeente actual) {
        assertThat(expected)
            .as("Verify Kadastralegemeente auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralegemeenteUpdatableFieldsEquals(Kadastralegemeente expected, Kadastralegemeente actual) {
        assertThat(expected)
            .as("Verify Kadastralegemeente relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getDatumbegingeldigheidkadastralegemeente())
                        .as("check datumbegingeldigheidkadastralegemeente")
                        .isEqualTo(actual.getDatumbegingeldigheidkadastralegemeente())
            )
            .satisfies(
                e ->
                    assertThat(e.getDatumeindegeldigheidkadastralegemeente())
                        .as("check datumeindegeldigheidkadastralegemeente")
                        .isEqualTo(actual.getDatumeindegeldigheidkadastralegemeente())
            )
            .satisfies(
                e ->
                    assertThat(e.getKadastralegemeentecode())
                        .as("check kadastralegemeentecode")
                        .isEqualTo(actual.getKadastralegemeentecode())
            )
            .satisfies(e -> assertThat(e.getNaam()).as("check naam").isEqualTo(actual.getNaam()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralegemeenteUpdatableRelationshipsEquals(Kadastralegemeente expected, Kadastralegemeente actual) {}
}
