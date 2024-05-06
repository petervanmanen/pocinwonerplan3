package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.TegenprestatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TegenprestatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tegenprestatie.class);
        Tegenprestatie tegenprestatie1 = getTegenprestatieSample1();
        Tegenprestatie tegenprestatie2 = new Tegenprestatie();
        assertThat(tegenprestatie1).isNotEqualTo(tegenprestatie2);

        tegenprestatie2.setId(tegenprestatie1.getId());
        assertThat(tegenprestatie1).isEqualTo(tegenprestatie2);

        tegenprestatie2 = getTegenprestatieSample2();
        assertThat(tegenprestatie1).isNotEqualTo(tegenprestatie2);
    }

    @Test
    void leverttegenprestatieClientTest() throws Exception {
        Tegenprestatie tegenprestatie = getTegenprestatieRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        tegenprestatie.setLeverttegenprestatieClient(clientBack);
        assertThat(tegenprestatie.getLeverttegenprestatieClient()).isEqualTo(clientBack);

        tegenprestatie.leverttegenprestatieClient(null);
        assertThat(tegenprestatie.getLeverttegenprestatieClient()).isNull();
    }
}
