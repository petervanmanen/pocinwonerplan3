package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DomeinoftaakveldTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DomeinoftaakveldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Domeinoftaakveld.class);
        Domeinoftaakveld domeinoftaakveld1 = getDomeinoftaakveldSample1();
        Domeinoftaakveld domeinoftaakveld2 = new Domeinoftaakveld();
        assertThat(domeinoftaakveld1).isNotEqualTo(domeinoftaakveld2);

        domeinoftaakveld2.setId(domeinoftaakveld1.getId());
        assertThat(domeinoftaakveld1).isEqualTo(domeinoftaakveld2);

        domeinoftaakveld2 = getDomeinoftaakveldSample2();
        assertThat(domeinoftaakveld1).isNotEqualTo(domeinoftaakveld2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Domeinoftaakveld domeinoftaakveld = new Domeinoftaakveld();
        assertThat(domeinoftaakveld.hashCode()).isZero();

        Domeinoftaakveld domeinoftaakveld1 = getDomeinoftaakveldSample1();
        domeinoftaakveld.setId(domeinoftaakveld1.getId());
        assertThat(domeinoftaakveld).hasSameHashCodeAs(domeinoftaakveld1);
    }
}
