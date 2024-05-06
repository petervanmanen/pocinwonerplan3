package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OrganisatorischeeenheidhrTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganisatorischeeenheidhrTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organisatorischeeenheidhr.class);
        Organisatorischeeenheidhr organisatorischeeenheidhr1 = getOrganisatorischeeenheidhrSample1();
        Organisatorischeeenheidhr organisatorischeeenheidhr2 = new Organisatorischeeenheidhr();
        assertThat(organisatorischeeenheidhr1).isNotEqualTo(organisatorischeeenheidhr2);

        organisatorischeeenheidhr2.setId(organisatorischeeenheidhr1.getId());
        assertThat(organisatorischeeenheidhr1).isEqualTo(organisatorischeeenheidhr2);

        organisatorischeeenheidhr2 = getOrganisatorischeeenheidhrSample2();
        assertThat(organisatorischeeenheidhr1).isNotEqualTo(organisatorischeeenheidhr2);
    }
}
