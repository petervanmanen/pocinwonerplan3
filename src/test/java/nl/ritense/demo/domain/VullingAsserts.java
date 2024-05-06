package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VullingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVullingAllPropertiesEquals(Vulling expected, Vulling actual) {
        assertVullingAutoGeneratedPropertiesEquals(expected, actual);
        assertVullingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVullingAllUpdatablePropertiesEquals(Vulling expected, Vulling actual) {
        assertVullingUpdatableFieldsEquals(expected, actual);
        assertVullingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVullingAutoGeneratedPropertiesEquals(Vulling expected, Vulling actual) {
        assertThat(expected)
            .as("Verify Vulling auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVullingUpdatableFieldsEquals(Vulling expected, Vulling actual) {
        assertThat(expected)
            .as("Verify Vulling relevant properties")
            .satisfies(e -> assertThat(e.getGrondsoort()).as("check grondsoort").isEqualTo(actual.getGrondsoort()))
            .satisfies(e -> assertThat(e.getKey()).as("check key").isEqualTo(actual.getKey()))
            .satisfies(e -> assertThat(e.getKeyspoor()).as("check keyspoor").isEqualTo(actual.getKeyspoor()))
            .satisfies(e -> assertThat(e.getKleur()).as("check kleur").isEqualTo(actual.getKleur()))
            .satisfies(e -> assertThat(e.getProjectcd()).as("check projectcd").isEqualTo(actual.getProjectcd()))
            .satisfies(e -> assertThat(e.getPutnummer()).as("check putnummer").isEqualTo(actual.getPutnummer()))
            .satisfies(e -> assertThat(e.getSpoornummer()).as("check spoornummer").isEqualTo(actual.getSpoornummer()))
            .satisfies(e -> assertThat(e.getStructuur()).as("check structuur").isEqualTo(actual.getStructuur()))
            .satisfies(e -> assertThat(e.getVlaknummer()).as("check vlaknummer").isEqualTo(actual.getVlaknummer()))
            .satisfies(e -> assertThat(e.getVullingnummer()).as("check vullingnummer").isEqualTo(actual.getVullingnummer()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVullingUpdatableRelationshipsEquals(Vulling expected, Vulling actual) {
        assertThat(expected)
            .as("Verify Vulling relationships")
            .satisfies(e -> assertThat(e.getHeeftSpoor()).as("check heeftSpoor").isEqualTo(actual.getHeeftSpoor()));
    }
}