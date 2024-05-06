package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OntbindinghuwelijkgeregistreerdpartnerschapTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OntbindinghuwelijkgeregistreerdpartnerschapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ontbindinghuwelijkgeregistreerdpartnerschap.class);
        Ontbindinghuwelijkgeregistreerdpartnerschap ontbindinghuwelijkgeregistreerdpartnerschap1 =
            getOntbindinghuwelijkgeregistreerdpartnerschapSample1();
        Ontbindinghuwelijkgeregistreerdpartnerschap ontbindinghuwelijkgeregistreerdpartnerschap2 =
            new Ontbindinghuwelijkgeregistreerdpartnerschap();
        assertThat(ontbindinghuwelijkgeregistreerdpartnerschap1).isNotEqualTo(ontbindinghuwelijkgeregistreerdpartnerschap2);

        ontbindinghuwelijkgeregistreerdpartnerschap2.setId(ontbindinghuwelijkgeregistreerdpartnerschap1.getId());
        assertThat(ontbindinghuwelijkgeregistreerdpartnerschap1).isEqualTo(ontbindinghuwelijkgeregistreerdpartnerschap2);

        ontbindinghuwelijkgeregistreerdpartnerschap2 = getOntbindinghuwelijkgeregistreerdpartnerschapSample2();
        assertThat(ontbindinghuwelijkgeregistreerdpartnerschap1).isNotEqualTo(ontbindinghuwelijkgeregistreerdpartnerschap2);
    }
}
