package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PrijsafspraakAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsafspraakAllPropertiesEquals(Prijsafspraak expected, Prijsafspraak actual) {
        assertPrijsafspraakAutoGeneratedPropertiesEquals(expected, actual);
        assertPrijsafspraakAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsafspraakAllUpdatablePropertiesEquals(Prijsafspraak expected, Prijsafspraak actual) {
        assertPrijsafspraakUpdatableFieldsEquals(expected, actual);
        assertPrijsafspraakUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsafspraakAutoGeneratedPropertiesEquals(Prijsafspraak expected, Prijsafspraak actual) {
        assertThat(expected)
            .as("Verify Prijsafspraak auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsafspraakUpdatableFieldsEquals(Prijsafspraak expected, Prijsafspraak actual) {
        assertThat(expected)
            .as("Verify Prijsafspraak relevant properties")
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()))
            .satisfies(e -> assertThat(e.getTitel()).as("check titel").isEqualTo(actual.getTitel()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsafspraakUpdatableRelationshipsEquals(Prijsafspraak expected, Prijsafspraak actual) {}
}
