package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ToepasbareregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ToepasbareregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Toepasbareregel.class);
        Toepasbareregel toepasbareregel1 = getToepasbareregelSample1();
        Toepasbareregel toepasbareregel2 = new Toepasbareregel();
        assertThat(toepasbareregel1).isNotEqualTo(toepasbareregel2);

        toepasbareregel2.setId(toepasbareregel1.getId());
        assertThat(toepasbareregel1).isEqualTo(toepasbareregel2);

        toepasbareregel2 = getToepasbareregelSample2();
        assertThat(toepasbareregel1).isNotEqualTo(toepasbareregel2);
    }
}
