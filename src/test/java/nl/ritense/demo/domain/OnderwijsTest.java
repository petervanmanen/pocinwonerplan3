package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OnderwijsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnderwijsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onderwijs.class);
        Onderwijs onderwijs1 = getOnderwijsSample1();
        Onderwijs onderwijs2 = new Onderwijs();
        assertThat(onderwijs1).isNotEqualTo(onderwijs2);

        onderwijs2.setId(onderwijs1.getId());
        assertThat(onderwijs1).isEqualTo(onderwijs2);

        onderwijs2 = getOnderwijsSample2();
        assertThat(onderwijs1).isNotEqualTo(onderwijs2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Onderwijs onderwijs = new Onderwijs();
        assertThat(onderwijs.hashCode()).isZero();

        Onderwijs onderwijs1 = getOnderwijsSample1();
        onderwijs.setId(onderwijs1.getId());
        assertThat(onderwijs).hasSameHashCodeAs(onderwijs1);
    }
}
