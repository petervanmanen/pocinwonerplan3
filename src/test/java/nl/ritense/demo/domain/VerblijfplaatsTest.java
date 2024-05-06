package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerblijfplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerblijfplaatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verblijfplaats.class);
        Verblijfplaats verblijfplaats1 = getVerblijfplaatsSample1();
        Verblijfplaats verblijfplaats2 = new Verblijfplaats();
        assertThat(verblijfplaats1).isNotEqualTo(verblijfplaats2);

        verblijfplaats2.setId(verblijfplaats1.getId());
        assertThat(verblijfplaats1).isEqualTo(verblijfplaats2);

        verblijfplaats2 = getVerblijfplaatsSample2();
        assertThat(verblijfplaats1).isNotEqualTo(verblijfplaats2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Verblijfplaats verblijfplaats = new Verblijfplaats();
        assertThat(verblijfplaats.hashCode()).isZero();

        Verblijfplaats verblijfplaats1 = getVerblijfplaatsSample1();
        verblijfplaats.setId(verblijfplaats1.getId());
        assertThat(verblijfplaats).hasSameHashCodeAs(verblijfplaats1);
    }
}
