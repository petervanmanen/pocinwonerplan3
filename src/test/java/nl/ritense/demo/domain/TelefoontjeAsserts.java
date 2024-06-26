package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TelefoontjeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoontjeAllPropertiesEquals(Telefoontje expected, Telefoontje actual) {
        assertTelefoontjeAutoGeneratedPropertiesEquals(expected, actual);
        assertTelefoontjeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoontjeAllUpdatablePropertiesEquals(Telefoontje expected, Telefoontje actual) {
        assertTelefoontjeUpdatableFieldsEquals(expected, actual);
        assertTelefoontjeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoontjeAutoGeneratedPropertiesEquals(Telefoontje expected, Telefoontje actual) {
        assertThat(expected)
            .as("Verify Telefoontje auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoontjeUpdatableFieldsEquals(Telefoontje expected, Telefoontje actual) {
        assertThat(expected)
            .as("Verify Telefoontje relevant properties")
            .satisfies(
                e -> assertThat(e.getAfhandeltijdnagesprek()).as("check afhandeltijdnagesprek").isEqualTo(actual.getAfhandeltijdnagesprek())
            )
            .satisfies(e -> assertThat(e.getDeltaisdnconnectie()).as("check deltaisdnconnectie").isEqualTo(actual.getDeltaisdnconnectie()))
            .satisfies(e -> assertThat(e.getEindtijd()).as("check eindtijd").isEqualTo(actual.getEindtijd()))
            .satisfies(e -> assertThat(e.getStarttijd()).as("check starttijd").isEqualTo(actual.getStarttijd()))
            .satisfies(e -> assertThat(e.getTotaleonholdtijd()).as("check totaleonholdtijd").isEqualTo(actual.getTotaleonholdtijd()))
            .satisfies(e -> assertThat(e.getTotalespreektijd()).as("check totalespreektijd").isEqualTo(actual.getTotalespreektijd()))
            .satisfies(e -> assertThat(e.getTotalewachttijd()).as("check totalewachttijd").isEqualTo(actual.getTotalewachttijd()))
            .satisfies(e -> assertThat(e.getTotlatetijdsduur()).as("check totlatetijdsduur").isEqualTo(actual.getTotlatetijdsduur()))
            .satisfies(e -> assertThat(e.getTrackid()).as("check trackid").isEqualTo(actual.getTrackid()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTelefoontjeUpdatableRelationshipsEquals(Telefoontje expected, Telefoontje actual) {
        assertThat(expected)
            .as("Verify Telefoontje relationships")
            .satisfies(
                e -> assertThat(e.getHeeftTelefoonstatus()).as("check heeftTelefoonstatus").isEqualTo(actual.getHeeftTelefoonstatus())
            )
            .satisfies(
                e ->
                    assertThat(e.getHeeftTelefoononderwerp())
                        .as("check heeftTelefoononderwerp")
                        .isEqualTo(actual.getHeeftTelefoononderwerp())
            );
    }
}
