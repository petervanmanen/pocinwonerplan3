package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EnkelvoudigdocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnkelvoudigdocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enkelvoudigdocument.class);
        Enkelvoudigdocument enkelvoudigdocument1 = getEnkelvoudigdocumentSample1();
        Enkelvoudigdocument enkelvoudigdocument2 = new Enkelvoudigdocument();
        assertThat(enkelvoudigdocument1).isNotEqualTo(enkelvoudigdocument2);

        enkelvoudigdocument2.setId(enkelvoudigdocument1.getId());
        assertThat(enkelvoudigdocument1).isEqualTo(enkelvoudigdocument2);

        enkelvoudigdocument2 = getEnkelvoudigdocumentSample2();
        assertThat(enkelvoudigdocument1).isNotEqualTo(enkelvoudigdocument2);
    }
}
