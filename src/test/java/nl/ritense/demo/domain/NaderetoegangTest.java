package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NaderetoegangTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NaderetoegangTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Naderetoegang.class);
        Naderetoegang naderetoegang1 = getNaderetoegangSample1();
        Naderetoegang naderetoegang2 = new Naderetoegang();
        assertThat(naderetoegang1).isNotEqualTo(naderetoegang2);

        naderetoegang2.setId(naderetoegang1.getId());
        assertThat(naderetoegang1).isEqualTo(naderetoegang2);

        naderetoegang2 = getNaderetoegangSample2();
        assertThat(naderetoegang1).isNotEqualTo(naderetoegang2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Naderetoegang naderetoegang = new Naderetoegang();
        assertThat(naderetoegang.hashCode()).isZero();

        Naderetoegang naderetoegang1 = getNaderetoegangSample1();
        naderetoegang.setId(naderetoegang1.getId());
        assertThat(naderetoegang).hasSameHashCodeAs(naderetoegang1);
    }
}
