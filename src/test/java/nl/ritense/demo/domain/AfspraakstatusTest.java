package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AfspraakstatusTestSamples.*;
import static nl.ritense.demo.domain.BalieafspraakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AfspraakstatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Afspraakstatus.class);
        Afspraakstatus afspraakstatus1 = getAfspraakstatusSample1();
        Afspraakstatus afspraakstatus2 = new Afspraakstatus();
        assertThat(afspraakstatus1).isNotEqualTo(afspraakstatus2);

        afspraakstatus2.setId(afspraakstatus1.getId());
        assertThat(afspraakstatus1).isEqualTo(afspraakstatus2);

        afspraakstatus2 = getAfspraakstatusSample2();
        assertThat(afspraakstatus1).isNotEqualTo(afspraakstatus2);
    }

    @Test
    void heeftBalieafspraakTest() throws Exception {
        Afspraakstatus afspraakstatus = getAfspraakstatusRandomSampleGenerator();
        Balieafspraak balieafspraakBack = getBalieafspraakRandomSampleGenerator();

        afspraakstatus.addHeeftBalieafspraak(balieafspraakBack);
        assertThat(afspraakstatus.getHeeftBalieafspraaks()).containsOnly(balieafspraakBack);
        assertThat(balieafspraakBack.getHeeftAfspraakstatus()).isEqualTo(afspraakstatus);

        afspraakstatus.removeHeeftBalieafspraak(balieafspraakBack);
        assertThat(afspraakstatus.getHeeftBalieafspraaks()).doesNotContain(balieafspraakBack);
        assertThat(balieafspraakBack.getHeeftAfspraakstatus()).isNull();

        afspraakstatus.heeftBalieafspraaks(new HashSet<>(Set.of(balieafspraakBack)));
        assertThat(afspraakstatus.getHeeftBalieafspraaks()).containsOnly(balieafspraakBack);
        assertThat(balieafspraakBack.getHeeftAfspraakstatus()).isEqualTo(afspraakstatus);

        afspraakstatus.setHeeftBalieafspraaks(new HashSet<>());
        assertThat(afspraakstatus.getHeeftBalieafspraaks()).doesNotContain(balieafspraakBack);
        assertThat(balieafspraakBack.getHeeftAfspraakstatus()).isNull();
    }
}
