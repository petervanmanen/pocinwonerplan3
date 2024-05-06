package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ReisdocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReisdocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reisdocument.class);
        Reisdocument reisdocument1 = getReisdocumentSample1();
        Reisdocument reisdocument2 = new Reisdocument();
        assertThat(reisdocument1).isNotEqualTo(reisdocument2);

        reisdocument2.setId(reisdocument1.getId());
        assertThat(reisdocument1).isEqualTo(reisdocument2);

        reisdocument2 = getReisdocumentSample2();
        assertThat(reisdocument1).isNotEqualTo(reisdocument2);
    }
}
