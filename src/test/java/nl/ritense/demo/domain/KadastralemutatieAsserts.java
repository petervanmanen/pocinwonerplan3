package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class KadastralemutatieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralemutatieAllPropertiesEquals(Kadastralemutatie expected, Kadastralemutatie actual) {
        assertKadastralemutatieAutoGeneratedPropertiesEquals(expected, actual);
        assertKadastralemutatieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralemutatieAllUpdatablePropertiesEquals(Kadastralemutatie expected, Kadastralemutatie actual) {
        assertKadastralemutatieUpdatableFieldsEquals(expected, actual);
        assertKadastralemutatieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralemutatieAutoGeneratedPropertiesEquals(Kadastralemutatie expected, Kadastralemutatie actual) {
        assertThat(expected)
            .as("Verify Kadastralemutatie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralemutatieUpdatableFieldsEquals(Kadastralemutatie expected, Kadastralemutatie actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertKadastralemutatieUpdatableRelationshipsEquals(Kadastralemutatie expected, Kadastralemutatie actual) {
        assertThat(expected)
            .as("Verify Kadastralemutatie relationships")
            .satisfies(
                e ->
                    assertThat(e.getBetrokkenenRechtspersoons())
                        .as("check betrokkenenRechtspersoons")
                        .isEqualTo(actual.getBetrokkenenRechtspersoons())
            );
    }
}
