package nl.ritense.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class Lstclass1Asserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLstclass1AllPropertiesEquals(Lstclass1 expected, Lstclass1 actual) {
        assertLstclass1AutoGeneratedPropertiesEquals(expected, actual);
        assertLstclass1AllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLstclass1AllUpdatablePropertiesEquals(Lstclass1 expected, Lstclass1 actual) {
        assertLstclass1UpdatableFieldsEquals(expected, actual);
        assertLstclass1UpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLstclass1AutoGeneratedPropertiesEquals(Lstclass1 expected, Lstclass1 actual) {
        assertThat(expected)
            .as("Verify Lstclass1 auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLstclass1UpdatableFieldsEquals(Lstclass1 expected, Lstclass1 actual) {
        assertThat(expected)
            .as("Verify Lstclass1 relevant properties")
            .satisfies(e -> assertThat(e.getWaarde()).as("check waarde").isEqualTo(actual.getWaarde()))
            .satisfies(e -> assertThat(e.getDwhrecordid()).as("check dwhrecordid").isEqualTo(actual.getDwhrecordid()))
            .satisfies(e -> assertThat(e.getDwhodsrecordid()).as("check dwhodsrecordid").isEqualTo(actual.getDwhodsrecordid()))
            .satisfies(e -> assertThat(e.getDwhstartdt()).as("check dwhstartdt").isEqualTo(actual.getDwhstartdt()))
            .satisfies(e -> assertThat(e.getDwheinddt()).as("check dwheinddt").isEqualTo(actual.getDwheinddt()))
            .satisfies(e -> assertThat(e.getDwhrunid()).as("check dwhrunid").isEqualTo(actual.getDwhrunid()))
            .satisfies(e -> assertThat(e.getDwhbron()).as("check dwhbron").isEqualTo(actual.getDwhbron()))
            .satisfies(e -> assertThat(e.getDwhlaaddt()).as("check dwhlaaddt").isEqualTo(actual.getDwhlaaddt()))
            .satisfies(e -> assertThat(e.getDwhactueel()).as("check dwhactueel").isEqualTo(actual.getDwhactueel()))
            .satisfies(e -> assertThat(e.getLstclass1id()).as("check lstclass1id").isEqualTo(actual.getLstclass1id()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLstclass1UpdatableRelationshipsEquals(Lstclass1 expected, Lstclass1 actual) {}
}
