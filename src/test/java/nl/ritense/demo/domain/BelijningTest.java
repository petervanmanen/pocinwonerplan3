package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BelijningTestSamples.*;
import static nl.ritense.demo.domain.BinnenlocatieTestSamples.*;
import static nl.ritense.demo.domain.VeldTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BelijningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Belijning.class);
        Belijning belijning1 = getBelijningSample1();
        Belijning belijning2 = new Belijning();
        assertThat(belijning1).isNotEqualTo(belijning2);

        belijning2.setId(belijning1.getId());
        assertThat(belijning1).isEqualTo(belijning2);

        belijning2 = getBelijningSample2();
        assertThat(belijning1).isNotEqualTo(belijning2);
    }

    @Test
    void heeftBinnenlocatieTest() throws Exception {
        Belijning belijning = getBelijningRandomSampleGenerator();
        Binnenlocatie binnenlocatieBack = getBinnenlocatieRandomSampleGenerator();

        belijning.addHeeftBinnenlocatie(binnenlocatieBack);
        assertThat(belijning.getHeeftBinnenlocaties()).containsOnly(binnenlocatieBack);
        assertThat(binnenlocatieBack.getHeeftBelijnings()).containsOnly(belijning);

        belijning.removeHeeftBinnenlocatie(binnenlocatieBack);
        assertThat(belijning.getHeeftBinnenlocaties()).doesNotContain(binnenlocatieBack);
        assertThat(binnenlocatieBack.getHeeftBelijnings()).doesNotContain(belijning);

        belijning.heeftBinnenlocaties(new HashSet<>(Set.of(binnenlocatieBack)));
        assertThat(belijning.getHeeftBinnenlocaties()).containsOnly(binnenlocatieBack);
        assertThat(binnenlocatieBack.getHeeftBelijnings()).containsOnly(belijning);

        belijning.setHeeftBinnenlocaties(new HashSet<>());
        assertThat(belijning.getHeeftBinnenlocaties()).doesNotContain(binnenlocatieBack);
        assertThat(binnenlocatieBack.getHeeftBelijnings()).doesNotContain(belijning);
    }

    @Test
    void heeftVeldTest() throws Exception {
        Belijning belijning = getBelijningRandomSampleGenerator();
        Veld veldBack = getVeldRandomSampleGenerator();

        belijning.addHeeftVeld(veldBack);
        assertThat(belijning.getHeeftVelds()).containsOnly(veldBack);
        assertThat(veldBack.getHeeftBelijnings()).containsOnly(belijning);

        belijning.removeHeeftVeld(veldBack);
        assertThat(belijning.getHeeftVelds()).doesNotContain(veldBack);
        assertThat(veldBack.getHeeftBelijnings()).doesNotContain(belijning);

        belijning.heeftVelds(new HashSet<>(Set.of(veldBack)));
        assertThat(belijning.getHeeftVelds()).containsOnly(veldBack);
        assertThat(veldBack.getHeeftBelijnings()).containsOnly(belijning);

        belijning.setHeeftVelds(new HashSet<>());
        assertThat(belijning.getHeeftVelds()).doesNotContain(veldBack);
        assertThat(veldBack.getHeeftBelijnings()).doesNotContain(belijning);
    }
}
