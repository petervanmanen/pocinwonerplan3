package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OrganisatorischeeenheidTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganisatorischeeenheidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organisatorischeeenheid.class);
        Organisatorischeeenheid organisatorischeeenheid1 = getOrganisatorischeeenheidSample1();
        Organisatorischeeenheid organisatorischeeenheid2 = new Organisatorischeeenheid();
        assertThat(organisatorischeeenheid1).isNotEqualTo(organisatorischeeenheid2);

        organisatorischeeenheid2.setId(organisatorischeeenheid1.getId());
        assertThat(organisatorischeeenheid1).isEqualTo(organisatorischeeenheid2);

        organisatorischeeenheid2 = getOrganisatorischeeenheidSample2();
        assertThat(organisatorischeeenheid1).isNotEqualTo(organisatorischeeenheid2);
    }
}
