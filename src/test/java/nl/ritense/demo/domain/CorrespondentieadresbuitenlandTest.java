package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CorrespondentieadresbuitenlandTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CorrespondentieadresbuitenlandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Correspondentieadresbuitenland.class);
        Correspondentieadresbuitenland correspondentieadresbuitenland1 = getCorrespondentieadresbuitenlandSample1();
        Correspondentieadresbuitenland correspondentieadresbuitenland2 = new Correspondentieadresbuitenland();
        assertThat(correspondentieadresbuitenland1).isNotEqualTo(correspondentieadresbuitenland2);

        correspondentieadresbuitenland2.setId(correspondentieadresbuitenland1.getId());
        assertThat(correspondentieadresbuitenland1).isEqualTo(correspondentieadresbuitenland2);

        correspondentieadresbuitenland2 = getCorrespondentieadresbuitenlandSample2();
        assertThat(correspondentieadresbuitenland1).isNotEqualTo(correspondentieadresbuitenland2);
    }
}
