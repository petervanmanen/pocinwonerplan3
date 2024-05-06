package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ReisdocumentsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReisdocumentsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reisdocumentsoort.class);
        Reisdocumentsoort reisdocumentsoort1 = getReisdocumentsoortSample1();
        Reisdocumentsoort reisdocumentsoort2 = new Reisdocumentsoort();
        assertThat(reisdocumentsoort1).isNotEqualTo(reisdocumentsoort2);

        reisdocumentsoort2.setId(reisdocumentsoort1.getId());
        assertThat(reisdocumentsoort1).isEqualTo(reisdocumentsoort2);

        reisdocumentsoort2 = getReisdocumentsoortSample2();
        assertThat(reisdocumentsoort1).isNotEqualTo(reisdocumentsoort2);
    }
}
