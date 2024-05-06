package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WinkelverkoopgroepTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WinkelverkoopgroepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Winkelverkoopgroep.class);
        Winkelverkoopgroep winkelverkoopgroep1 = getWinkelverkoopgroepSample1();
        Winkelverkoopgroep winkelverkoopgroep2 = new Winkelverkoopgroep();
        assertThat(winkelverkoopgroep1).isNotEqualTo(winkelverkoopgroep2);

        winkelverkoopgroep2.setId(winkelverkoopgroep1.getId());
        assertThat(winkelverkoopgroep1).isEqualTo(winkelverkoopgroep2);

        winkelverkoopgroep2 = getWinkelverkoopgroepSample2();
        assertThat(winkelverkoopgroep1).isNotEqualTo(winkelverkoopgroep2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Winkelverkoopgroep winkelverkoopgroep = new Winkelverkoopgroep();
        assertThat(winkelverkoopgroep.hashCode()).isZero();

        Winkelverkoopgroep winkelverkoopgroep1 = getWinkelverkoopgroepSample1();
        winkelverkoopgroep.setId(winkelverkoopgroep1.getId());
        assertThat(winkelverkoopgroep).hasSameHashCodeAs(winkelverkoopgroep1);
    }
}
