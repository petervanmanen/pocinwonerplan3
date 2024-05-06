package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjectclassificatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjectclassificatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobjectclassificatie.class);
        Eobjectclassificatie eobjectclassificatie1 = getEobjectclassificatieSample1();
        Eobjectclassificatie eobjectclassificatie2 = new Eobjectclassificatie();
        assertThat(eobjectclassificatie1).isNotEqualTo(eobjectclassificatie2);

        eobjectclassificatie2.setId(eobjectclassificatie1.getId());
        assertThat(eobjectclassificatie1).isEqualTo(eobjectclassificatie2);

        eobjectclassificatie2 = getEobjectclassificatieSample2();
        assertThat(eobjectclassificatie1).isNotEqualTo(eobjectclassificatie2);
    }
}
