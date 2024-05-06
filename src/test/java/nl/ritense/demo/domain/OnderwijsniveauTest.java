package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OnderwijsniveauTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnderwijsniveauTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onderwijsniveau.class);
        Onderwijsniveau onderwijsniveau1 = getOnderwijsniveauSample1();
        Onderwijsniveau onderwijsniveau2 = new Onderwijsniveau();
        assertThat(onderwijsniveau1).isNotEqualTo(onderwijsniveau2);

        onderwijsniveau2.setId(onderwijsniveau1.getId());
        assertThat(onderwijsniveau1).isEqualTo(onderwijsniveau2);

        onderwijsniveau2 = getOnderwijsniveauSample2();
        assertThat(onderwijsniveau1).isNotEqualTo(onderwijsniveau2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Onderwijsniveau onderwijsniveau = new Onderwijsniveau();
        assertThat(onderwijsniveau.hashCode()).isZero();

        Onderwijsniveau onderwijsniveau1 = getOnderwijsniveauSample1();
        onderwijsniveau.setId(onderwijsniveau1.getId());
        assertThat(onderwijsniveau).hasSameHashCodeAs(onderwijsniveau1);
    }
}
