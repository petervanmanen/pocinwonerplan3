package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.TaalniveauTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaalniveauTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taalniveau.class);
        Taalniveau taalniveau1 = getTaalniveauSample1();
        Taalniveau taalniveau2 = new Taalniveau();
        assertThat(taalniveau1).isNotEqualTo(taalniveau2);

        taalniveau2.setId(taalniveau1.getId());
        assertThat(taalniveau1).isEqualTo(taalniveau2);

        taalniveau2 = getTaalniveauSample2();
        assertThat(taalniveau1).isNotEqualTo(taalniveau2);
    }

    @Test
    void heefttaalniveauClientTest() throws Exception {
        Taalniveau taalniveau = getTaalniveauRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        taalniveau.addHeefttaalniveauClient(clientBack);
        assertThat(taalniveau.getHeefttaalniveauClients()).containsOnly(clientBack);
        assertThat(clientBack.getHeefttaalniveauTaalniveaus()).containsOnly(taalniveau);

        taalniveau.removeHeefttaalniveauClient(clientBack);
        assertThat(taalniveau.getHeefttaalniveauClients()).doesNotContain(clientBack);
        assertThat(clientBack.getHeefttaalniveauTaalniveaus()).doesNotContain(taalniveau);

        taalniveau.heefttaalniveauClients(new HashSet<>(Set.of(clientBack)));
        assertThat(taalniveau.getHeefttaalniveauClients()).containsOnly(clientBack);
        assertThat(clientBack.getHeefttaalniveauTaalniveaus()).containsOnly(taalniveau);

        taalniveau.setHeefttaalniveauClients(new HashSet<>());
        assertThat(taalniveau.getHeefttaalniveauClients()).doesNotContain(clientBack);
        assertThat(clientBack.getHeefttaalniveauTaalniveaus()).doesNotContain(taalniveau);
    }
}
