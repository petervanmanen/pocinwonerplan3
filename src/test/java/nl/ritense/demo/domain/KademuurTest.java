package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KademuurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KademuurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kademuur.class);
        Kademuur kademuur1 = getKademuurSample1();
        Kademuur kademuur2 = new Kademuur();
        assertThat(kademuur1).isNotEqualTo(kademuur2);

        kademuur2.setId(kademuur1.getId());
        assertThat(kademuur1).isEqualTo(kademuur2);

        kademuur2 = getKademuurSample2();
        assertThat(kademuur1).isNotEqualTo(kademuur2);
    }
}
