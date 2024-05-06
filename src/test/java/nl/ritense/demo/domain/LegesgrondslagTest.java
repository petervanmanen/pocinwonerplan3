package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LegesgrondslagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LegesgrondslagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Legesgrondslag.class);
        Legesgrondslag legesgrondslag1 = getLegesgrondslagSample1();
        Legesgrondslag legesgrondslag2 = new Legesgrondslag();
        assertThat(legesgrondslag1).isNotEqualTo(legesgrondslag2);

        legesgrondslag2.setId(legesgrondslag1.getId());
        assertThat(legesgrondslag1).isEqualTo(legesgrondslag2);

        legesgrondslag2 = getLegesgrondslagSample2();
        assertThat(legesgrondslag1).isNotEqualTo(legesgrondslag2);
    }
}
