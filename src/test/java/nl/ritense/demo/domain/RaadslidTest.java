package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IndienerTestSamples.*;
import static nl.ritense.demo.domain.RaadscommissieTestSamples.*;
import static nl.ritense.demo.domain.RaadslidTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RaadslidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Raadslid.class);
        Raadslid raadslid1 = getRaadslidSample1();
        Raadslid raadslid2 = new Raadslid();
        assertThat(raadslid1).isNotEqualTo(raadslid2);

        raadslid2.setId(raadslid1.getId());
        assertThat(raadslid1).isEqualTo(raadslid2);

        raadslid2 = getRaadslidSample2();
        assertThat(raadslid1).isNotEqualTo(raadslid2);
    }

    @Test
    void islidvanRaadscommissieTest() throws Exception {
        Raadslid raadslid = getRaadslidRandomSampleGenerator();
        Raadscommissie raadscommissieBack = getRaadscommissieRandomSampleGenerator();

        raadslid.addIslidvanRaadscommissie(raadscommissieBack);
        assertThat(raadslid.getIslidvanRaadscommissies()).containsOnly(raadscommissieBack);

        raadslid.removeIslidvanRaadscommissie(raadscommissieBack);
        assertThat(raadslid.getIslidvanRaadscommissies()).doesNotContain(raadscommissieBack);

        raadslid.islidvanRaadscommissies(new HashSet<>(Set.of(raadscommissieBack)));
        assertThat(raadslid.getIslidvanRaadscommissies()).containsOnly(raadscommissieBack);

        raadslid.setIslidvanRaadscommissies(new HashSet<>());
        assertThat(raadslid.getIslidvanRaadscommissies()).doesNotContain(raadscommissieBack);
    }

    @Test
    void isIndienerTest() throws Exception {
        Raadslid raadslid = getRaadslidRandomSampleGenerator();
        Indiener indienerBack = getIndienerRandomSampleGenerator();

        raadslid.setIsIndiener(indienerBack);
        assertThat(raadslid.getIsIndiener()).isEqualTo(indienerBack);
        assertThat(indienerBack.getIsRaadslid()).isEqualTo(raadslid);

        raadslid.isIndiener(null);
        assertThat(raadslid.getIsIndiener()).isNull();
        assertThat(indienerBack.getIsRaadslid()).isNull();
    }
}
