package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContactTestSamples.*;
import static nl.ritense.demo.domain.NummeraanduidingTestSamples.*;
import static nl.ritense.demo.domain.VestigingTestSamples.*;
import static nl.ritense.demo.domain.WerkgelegenheidTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VestigingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vestiging.class);
        Vestiging vestiging1 = getVestigingSample1();
        Vestiging vestiging2 = new Vestiging();
        assertThat(vestiging1).isNotEqualTo(vestiging2);

        vestiging2.setId(vestiging1.getId());
        assertThat(vestiging1).isEqualTo(vestiging2);

        vestiging2 = getVestigingSample2();
        assertThat(vestiging1).isNotEqualTo(vestiging2);
    }

    @Test
    void heeftWerkgelegenheidTest() throws Exception {
        Vestiging vestiging = getVestigingRandomSampleGenerator();
        Werkgelegenheid werkgelegenheidBack = getWerkgelegenheidRandomSampleGenerator();

        vestiging.setHeeftWerkgelegenheid(werkgelegenheidBack);
        assertThat(vestiging.getHeeftWerkgelegenheid()).isEqualTo(werkgelegenheidBack);

        vestiging.heeftWerkgelegenheid(null);
        assertThat(vestiging.getHeeftWerkgelegenheid()).isNull();
    }

    @Test
    void heeftalslocatieadresNummeraanduidingTest() throws Exception {
        Vestiging vestiging = getVestigingRandomSampleGenerator();
        Nummeraanduiding nummeraanduidingBack = getNummeraanduidingRandomSampleGenerator();

        vestiging.setHeeftalslocatieadresNummeraanduiding(nummeraanduidingBack);
        assertThat(vestiging.getHeeftalslocatieadresNummeraanduiding()).isEqualTo(nummeraanduidingBack);

        vestiging.heeftalslocatieadresNummeraanduiding(null);
        assertThat(vestiging.getHeeftalslocatieadresNummeraanduiding()).isNull();
    }

    @Test
    void bijContactTest() throws Exception {
        Vestiging vestiging = getVestigingRandomSampleGenerator();
        Contact contactBack = getContactRandomSampleGenerator();

        vestiging.addBijContact(contactBack);
        assertThat(vestiging.getBijContacts()).containsOnly(contactBack);
        assertThat(contactBack.getBijVestiging()).isEqualTo(vestiging);

        vestiging.removeBijContact(contactBack);
        assertThat(vestiging.getBijContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getBijVestiging()).isNull();

        vestiging.bijContacts(new HashSet<>(Set.of(contactBack)));
        assertThat(vestiging.getBijContacts()).containsOnly(contactBack);
        assertThat(contactBack.getBijVestiging()).isEqualTo(vestiging);

        vestiging.setBijContacts(new HashSet<>());
        assertThat(vestiging.getBijContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getBijVestiging()).isNull();
    }
}
