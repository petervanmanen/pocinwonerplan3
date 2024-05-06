package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VestigingvanzaakbehandelendeorganisatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VestigingvanzaakbehandelendeorganisatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vestigingvanzaakbehandelendeorganisatie.class);
        Vestigingvanzaakbehandelendeorganisatie vestigingvanzaakbehandelendeorganisatie1 =
            getVestigingvanzaakbehandelendeorganisatieSample1();
        Vestigingvanzaakbehandelendeorganisatie vestigingvanzaakbehandelendeorganisatie2 = new Vestigingvanzaakbehandelendeorganisatie();
        assertThat(vestigingvanzaakbehandelendeorganisatie1).isNotEqualTo(vestigingvanzaakbehandelendeorganisatie2);

        vestigingvanzaakbehandelendeorganisatie2.setId(vestigingvanzaakbehandelendeorganisatie1.getId());
        assertThat(vestigingvanzaakbehandelendeorganisatie1).isEqualTo(vestigingvanzaakbehandelendeorganisatie2);

        vestigingvanzaakbehandelendeorganisatie2 = getVestigingvanzaakbehandelendeorganisatieSample2();
        assertThat(vestigingvanzaakbehandelendeorganisatie1).isNotEqualTo(vestigingvanzaakbehandelendeorganisatie2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Vestigingvanzaakbehandelendeorganisatie vestigingvanzaakbehandelendeorganisatie = new Vestigingvanzaakbehandelendeorganisatie();
        assertThat(vestigingvanzaakbehandelendeorganisatie.hashCode()).isZero();

        Vestigingvanzaakbehandelendeorganisatie vestigingvanzaakbehandelendeorganisatie1 =
            getVestigingvanzaakbehandelendeorganisatieSample1();
        vestigingvanzaakbehandelendeorganisatie.setId(vestigingvanzaakbehandelendeorganisatie1.getId());
        assertThat(vestigingvanzaakbehandelendeorganisatie).hasSameHashCodeAs(vestigingvanzaakbehandelendeorganisatie1);
    }
}
