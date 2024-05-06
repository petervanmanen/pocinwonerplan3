package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SamengestelddocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SamengestelddocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Samengestelddocument.class);
        Samengestelddocument samengestelddocument1 = getSamengestelddocumentSample1();
        Samengestelddocument samengestelddocument2 = new Samengestelddocument();
        assertThat(samengestelddocument1).isNotEqualTo(samengestelddocument2);

        samengestelddocument2.setId(samengestelddocument1.getId());
        assertThat(samengestelddocument1).isEqualTo(samengestelddocument2);

        samengestelddocument2 = getSamengestelddocumentSample2();
        assertThat(samengestelddocument1).isNotEqualTo(samengestelddocument2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Samengestelddocument samengestelddocument = new Samengestelddocument();
        assertThat(samengestelddocument.hashCode()).isZero();

        Samengestelddocument samengestelddocument1 = getSamengestelddocumentSample1();
        samengestelddocument.setId(samengestelddocument1.getId());
        assertThat(samengestelddocument).hasSameHashCodeAs(samengestelddocument1);
    }
}
