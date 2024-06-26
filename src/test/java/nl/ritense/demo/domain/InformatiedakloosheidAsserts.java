package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class InformatiedakloosheidAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInformatiedakloosheidAllPropertiesEquals(Informatiedakloosheid expected, Informatiedakloosheid actual) {
        assertInformatiedakloosheidAutoGeneratedPropertiesEquals(expected, actual);
        assertInformatiedakloosheidAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInformatiedakloosheidAllUpdatablePropertiesEquals(
        Informatiedakloosheid expected,
        Informatiedakloosheid actual
    ) {
        assertInformatiedakloosheidUpdatableFieldsEquals(expected, actual);
        assertInformatiedakloosheidUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInformatiedakloosheidAutoGeneratedPropertiesEquals(
        Informatiedakloosheid expected,
        Informatiedakloosheid actual
    ) {
        assertThat(expected)
            .as("Verify Informatiedakloosheid auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInformatiedakloosheidUpdatableFieldsEquals(Informatiedakloosheid expected, Informatiedakloosheid actual) {
        assertThat(expected)
            .as("Verify Informatiedakloosheid relevant properties")
            .satisfies(e -> assertThat(e.getDatumeinde()).as("check datumeinde").isEqualTo(actual.getDatumeinde()))
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()))
            .satisfies(e -> assertThat(e.getGemeenteoorsprong()).as("check gemeenteoorsprong").isEqualTo(actual.getGemeenteoorsprong()))
            .satisfies(
                e ->
                    assertThat(e.getToestemminggemeentelijkbriefadres())
                        .as("check toestemminggemeentelijkbriefadres")
                        .isEqualTo(actual.getToestemminggemeentelijkbriefadres())
            )
            .satisfies(
                e ->
                    assertThat(e.getToestemmingnachtopvang())
                        .as("check toestemmingnachtopvang")
                        .isEqualTo(actual.getToestemmingnachtopvang())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInformatiedakloosheidUpdatableRelationshipsEquals(
        Informatiedakloosheid expected,
        Informatiedakloosheid actual
    ) {}
}
