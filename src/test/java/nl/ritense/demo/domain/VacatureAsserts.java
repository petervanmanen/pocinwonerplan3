package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VacatureAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVacatureAllPropertiesEquals(Vacature expected, Vacature actual) {
        assertVacatureAutoGeneratedPropertiesEquals(expected, actual);
        assertVacatureAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVacatureAllUpdatablePropertiesEquals(Vacature expected, Vacature actual) {
        assertVacatureUpdatableFieldsEquals(expected, actual);
        assertVacatureUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVacatureAutoGeneratedPropertiesEquals(Vacature expected, Vacature actual) {
        assertThat(expected)
            .as("Verify Vacature auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVacatureUpdatableFieldsEquals(Vacature expected, Vacature actual) {
        assertThat(expected)
            .as("Verify Vacature relevant properties")
            .satisfies(e -> assertThat(e.getDatumgesloten()).as("check datumgesloten").isEqualTo(actual.getDatumgesloten()))
            .satisfies(e -> assertThat(e.getDatumopengesteld()).as("check datumopengesteld").isEqualTo(actual.getDatumopengesteld()))
            .satisfies(e -> assertThat(e.getDeeltijd()).as("check deeltijd").isEqualTo(actual.getDeeltijd()))
            .satisfies(e -> assertThat(e.getExtern()).as("check extern").isEqualTo(actual.getExtern()))
            .satisfies(e -> assertThat(e.getIntern()).as("check intern").isEqualTo(actual.getIntern()))
            .satisfies(e -> assertThat(e.getVastedienst()).as("check vastedienst").isEqualTo(actual.getVastedienst()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVacatureUpdatableRelationshipsEquals(Vacature expected, Vacature actual) {
        assertThat(expected)
            .as("Verify Vacature relationships")
            .satisfies(
                e ->
                    assertThat(e.getVacaturebijfunctieFunctie())
                        .as("check vacaturebijfunctieFunctie")
                        .isEqualTo(actual.getVacaturebijfunctieFunctie())
            );
    }
}
