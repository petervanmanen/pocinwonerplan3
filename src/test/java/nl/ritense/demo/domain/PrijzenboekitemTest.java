package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PrijzenboekitemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrijzenboekitemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prijzenboekitem.class);
        Prijzenboekitem prijzenboekitem1 = getPrijzenboekitemSample1();
        Prijzenboekitem prijzenboekitem2 = new Prijzenboekitem();
        assertThat(prijzenboekitem1).isNotEqualTo(prijzenboekitem2);

        prijzenboekitem2.setId(prijzenboekitem1.getId());
        assertThat(prijzenboekitem1).isEqualTo(prijzenboekitem2);

        prijzenboekitem2 = getPrijzenboekitemSample2();
        assertThat(prijzenboekitem1).isNotEqualTo(prijzenboekitem2);
    }
}
