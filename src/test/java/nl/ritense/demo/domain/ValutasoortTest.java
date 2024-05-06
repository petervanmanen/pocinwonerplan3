package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ValutasoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ValutasoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Valutasoort.class);
        Valutasoort valutasoort1 = getValutasoortSample1();
        Valutasoort valutasoort2 = new Valutasoort();
        assertThat(valutasoort1).isNotEqualTo(valutasoort2);

        valutasoort2.setId(valutasoort1.getId());
        assertThat(valutasoort1).isEqualTo(valutasoort2);

        valutasoort2 = getValutasoortSample2();
        assertThat(valutasoort1).isNotEqualTo(valutasoort2);
    }
}
