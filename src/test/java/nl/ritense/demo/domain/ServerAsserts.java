package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServerAllPropertiesEquals(Server expected, Server actual) {
        assertServerAutoGeneratedPropertiesEquals(expected, actual);
        assertServerAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServerAllUpdatablePropertiesEquals(Server expected, Server actual) {
        assertServerUpdatableFieldsEquals(expected, actual);
        assertServerUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServerAutoGeneratedPropertiesEquals(Server expected, Server actual) {
        assertThat(expected)
            .as("Verify Server auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServerUpdatableFieldsEquals(Server expected, Server actual) {
        assertThat(expected)
            .as("Verify Server relevant properties")
            .satisfies(e -> assertThat(e.getActief()).as("check actief").isEqualTo(actual.getActief()))
            .satisfies(e -> assertThat(e.getIpadres()).as("check ipadres").isEqualTo(actual.getIpadres()))
            .satisfies(e -> assertThat(e.getLocatie()).as("check locatie").isEqualTo(actual.getLocatie()))
            .satisfies(e -> assertThat(e.getOrganisatie()).as("check organisatie").isEqualTo(actual.getOrganisatie()))
            .satisfies(e -> assertThat(e.getSerienummer()).as("check serienummer").isEqualTo(actual.getSerienummer()))
            .satisfies(e -> assertThat(e.getServerid()).as("check serverid").isEqualTo(actual.getServerid()))
            .satisfies(e -> assertThat(e.getServertype()).as("check servertype").isEqualTo(actual.getServertype()))
            .satisfies(e -> assertThat(e.getVlan()).as("check vlan").isEqualTo(actual.getVlan()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServerUpdatableRelationshipsEquals(Server expected, Server actual) {
        assertThat(expected)
            .as("Verify Server relationships")
            .satisfies(
                e ->
                    assertThat(e.getHeeftleverancierLeverancier())
                        .as("check heeftleverancierLeverancier")
                        .isEqualTo(actual.getHeeftleverancierLeverancier())
            );
    }
}