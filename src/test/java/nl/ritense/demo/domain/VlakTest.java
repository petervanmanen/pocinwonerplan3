package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PutTestSamples.*;
import static nl.ritense.demo.domain.SpoorTestSamples.*;
import static nl.ritense.demo.domain.VlakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VlakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vlak.class);
        Vlak vlak1 = getVlakSample1();
        Vlak vlak2 = new Vlak();
        assertThat(vlak1).isNotEqualTo(vlak2);

        vlak2.setId(vlak1.getId());
        assertThat(vlak1).isEqualTo(vlak2);

        vlak2 = getVlakSample2();
        assertThat(vlak1).isNotEqualTo(vlak2);
    }

    @Test
    void heeftSpoorTest() throws Exception {
        Vlak vlak = getVlakRandomSampleGenerator();
        Spoor spoorBack = getSpoorRandomSampleGenerator();

        vlak.addHeeftSpoor(spoorBack);
        assertThat(vlak.getHeeftSpoors()).containsOnly(spoorBack);
        assertThat(spoorBack.getHeeftVlak()).isEqualTo(vlak);

        vlak.removeHeeftSpoor(spoorBack);
        assertThat(vlak.getHeeftSpoors()).doesNotContain(spoorBack);
        assertThat(spoorBack.getHeeftVlak()).isNull();

        vlak.heeftSpoors(new HashSet<>(Set.of(spoorBack)));
        assertThat(vlak.getHeeftSpoors()).containsOnly(spoorBack);
        assertThat(spoorBack.getHeeftVlak()).isEqualTo(vlak);

        vlak.setHeeftSpoors(new HashSet<>());
        assertThat(vlak.getHeeftSpoors()).doesNotContain(spoorBack);
        assertThat(spoorBack.getHeeftVlak()).isNull();
    }

    @Test
    void heeftPutTest() throws Exception {
        Vlak vlak = getVlakRandomSampleGenerator();
        Put putBack = getPutRandomSampleGenerator();

        vlak.setHeeftPut(putBack);
        assertThat(vlak.getHeeftPut()).isEqualTo(putBack);

        vlak.heeftPut(null);
        assertThat(vlak.getHeeftPut()).isNull();
    }
}
