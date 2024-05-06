package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PrijsafspraakTestSamples.*;
import static nl.ritense.demo.domain.PrijsregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrijsafspraakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prijsafspraak.class);
        Prijsafspraak prijsafspraak1 = getPrijsafspraakSample1();
        Prijsafspraak prijsafspraak2 = new Prijsafspraak();
        assertThat(prijsafspraak1).isNotEqualTo(prijsafspraak2);

        prijsafspraak2.setId(prijsafspraak1.getId());
        assertThat(prijsafspraak1).isEqualTo(prijsafspraak2);

        prijsafspraak2 = getPrijsafspraakSample2();
        assertThat(prijsafspraak1).isNotEqualTo(prijsafspraak2);
    }

    @Test
    void heeftPrijsregelTest() throws Exception {
        Prijsafspraak prijsafspraak = getPrijsafspraakRandomSampleGenerator();
        Prijsregel prijsregelBack = getPrijsregelRandomSampleGenerator();

        prijsafspraak.addHeeftPrijsregel(prijsregelBack);
        assertThat(prijsafspraak.getHeeftPrijsregels()).containsOnly(prijsregelBack);
        assertThat(prijsregelBack.getHeeftPrijsafspraak()).isEqualTo(prijsafspraak);

        prijsafspraak.removeHeeftPrijsregel(prijsregelBack);
        assertThat(prijsafspraak.getHeeftPrijsregels()).doesNotContain(prijsregelBack);
        assertThat(prijsregelBack.getHeeftPrijsafspraak()).isNull();

        prijsafspraak.heeftPrijsregels(new HashSet<>(Set.of(prijsregelBack)));
        assertThat(prijsafspraak.getHeeftPrijsregels()).containsOnly(prijsregelBack);
        assertThat(prijsregelBack.getHeeftPrijsafspraak()).isEqualTo(prijsafspraak);

        prijsafspraak.setHeeftPrijsregels(new HashSet<>());
        assertThat(prijsafspraak.getHeeftPrijsregels()).doesNotContain(prijsregelBack);
        assertThat(prijsregelBack.getHeeftPrijsafspraak()).isNull();
    }
}
