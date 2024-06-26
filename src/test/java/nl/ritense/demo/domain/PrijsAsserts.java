package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class PrijsAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsAllPropertiesEquals(Prijs expected, Prijs actual) {
        assertPrijsAutoGeneratedPropertiesEquals(expected, actual);
        assertPrijsAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsAllUpdatablePropertiesEquals(Prijs expected, Prijs actual) {
        assertPrijsUpdatableFieldsEquals(expected, actual);
        assertPrijsUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsAutoGeneratedPropertiesEquals(Prijs expected, Prijs actual) {
        assertThat(expected)
            .as("Verify Prijs auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsUpdatableFieldsEquals(Prijs expected, Prijs actual) {
        assertThat(expected)
            .as("Verify Prijs relevant properties")
            .satisfies(e -> assertThat(e.getBedrag()).as("check bedrag").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getBedrag()))
            .satisfies(
                e -> assertThat(e.getDatumeindegeldigheid()).as("check datumeindegeldigheid").isEqualTo(actual.getDatumeindegeldigheid())
            )
            .satisfies(e -> assertThat(e.getDatumstart()).as("check datumstart").isEqualTo(actual.getDatumstart()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPrijsUpdatableRelationshipsEquals(Prijs expected, Prijs actual) {
        assertThat(expected)
            .as("Verify Prijs relationships")
            .satisfies(e -> assertThat(e.getHeeftprijsProduct()).as("check heeftprijsProduct").isEqualTo(actual.getHeeftprijsProduct()));
    }
}
