package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.ClientbegeleiderTestSamples.*;
import static nl.ritense.demo.domain.InkomensvoorzieningTestSamples.*;
import static nl.ritense.demo.domain.InkomensvoorzieningsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InkomensvoorzieningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inkomensvoorziening.class);
        Inkomensvoorziening inkomensvoorziening1 = getInkomensvoorzieningSample1();
        Inkomensvoorziening inkomensvoorziening2 = new Inkomensvoorziening();
        assertThat(inkomensvoorziening1).isNotEqualTo(inkomensvoorziening2);

        inkomensvoorziening2.setId(inkomensvoorziening1.getId());
        assertThat(inkomensvoorziening1).isEqualTo(inkomensvoorziening2);

        inkomensvoorziening2 = getInkomensvoorzieningSample2();
        assertThat(inkomensvoorziening1).isNotEqualTo(inkomensvoorziening2);
    }

    @Test
    void heeftvoorzieningClientTest() throws Exception {
        Inkomensvoorziening inkomensvoorziening = getInkomensvoorzieningRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        inkomensvoorziening.setHeeftvoorzieningClient(clientBack);
        assertThat(inkomensvoorziening.getHeeftvoorzieningClient()).isEqualTo(clientBack);
        assertThat(clientBack.getHeeftvoorzieningInkomensvoorziening()).isEqualTo(inkomensvoorziening);

        inkomensvoorziening.heeftvoorzieningClient(null);
        assertThat(inkomensvoorziening.getHeeftvoorzieningClient()).isNull();
        assertThat(clientBack.getHeeftvoorzieningInkomensvoorziening()).isNull();
    }

    @Test
    void emptyClientbegeleiderTest() throws Exception {
        Inkomensvoorziening inkomensvoorziening = getInkomensvoorzieningRandomSampleGenerator();
        Clientbegeleider clientbegeleiderBack = getClientbegeleiderRandomSampleGenerator();

        inkomensvoorziening.setEmptyClientbegeleider(clientbegeleiderBack);
        assertThat(inkomensvoorziening.getEmptyClientbegeleider()).isEqualTo(clientbegeleiderBack);

        inkomensvoorziening.emptyClientbegeleider(null);
        assertThat(inkomensvoorziening.getEmptyClientbegeleider()).isNull();
    }

    @Test
    void issoortvoorzieningInkomensvoorzieningsoortTest() throws Exception {
        Inkomensvoorziening inkomensvoorziening = getInkomensvoorzieningRandomSampleGenerator();
        Inkomensvoorzieningsoort inkomensvoorzieningsoortBack = getInkomensvoorzieningsoortRandomSampleGenerator();

        inkomensvoorziening.setIssoortvoorzieningInkomensvoorzieningsoort(inkomensvoorzieningsoortBack);
        assertThat(inkomensvoorziening.getIssoortvoorzieningInkomensvoorzieningsoort()).isEqualTo(inkomensvoorzieningsoortBack);

        inkomensvoorziening.issoortvoorzieningInkomensvoorzieningsoort(null);
        assertThat(inkomensvoorziening.getIssoortvoorzieningInkomensvoorzieningsoort()).isNull();
    }

    @Test
    void voorzieningbijstandspartijClientTest() throws Exception {
        Inkomensvoorziening inkomensvoorziening = getInkomensvoorzieningRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        inkomensvoorziening.addVoorzieningbijstandspartijClient(clientBack);
        assertThat(inkomensvoorziening.getVoorzieningbijstandspartijClients()).containsOnly(clientBack);
        assertThat(clientBack.getVoorzieningbijstandspartijInkomensvoorzienings()).containsOnly(inkomensvoorziening);

        inkomensvoorziening.removeVoorzieningbijstandspartijClient(clientBack);
        assertThat(inkomensvoorziening.getVoorzieningbijstandspartijClients()).doesNotContain(clientBack);
        assertThat(clientBack.getVoorzieningbijstandspartijInkomensvoorzienings()).doesNotContain(inkomensvoorziening);

        inkomensvoorziening.voorzieningbijstandspartijClients(new HashSet<>(Set.of(clientBack)));
        assertThat(inkomensvoorziening.getVoorzieningbijstandspartijClients()).containsOnly(clientBack);
        assertThat(clientBack.getVoorzieningbijstandspartijInkomensvoorzienings()).containsOnly(inkomensvoorziening);

        inkomensvoorziening.setVoorzieningbijstandspartijClients(new HashSet<>());
        assertThat(inkomensvoorziening.getVoorzieningbijstandspartijClients()).doesNotContain(clientBack);
        assertThat(clientBack.getVoorzieningbijstandspartijInkomensvoorzienings()).doesNotContain(inkomensvoorziening);
    }
}
