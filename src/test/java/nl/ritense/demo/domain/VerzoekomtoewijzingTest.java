package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerzoekomtoewijzingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerzoekomtoewijzingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verzoekomtoewijzing.class);
        Verzoekomtoewijzing verzoekomtoewijzing1 = getVerzoekomtoewijzingSample1();
        Verzoekomtoewijzing verzoekomtoewijzing2 = new Verzoekomtoewijzing();
        assertThat(verzoekomtoewijzing1).isNotEqualTo(verzoekomtoewijzing2);

        verzoekomtoewijzing2.setId(verzoekomtoewijzing1.getId());
        assertThat(verzoekomtoewijzing1).isEqualTo(verzoekomtoewijzing2);

        verzoekomtoewijzing2 = getVerzoekomtoewijzingSample2();
        assertThat(verzoekomtoewijzing1).isNotEqualTo(verzoekomtoewijzing2);
    }
}
