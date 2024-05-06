package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TenaamstellingTestSamples.*;
import static nl.ritense.demo.domain.ZekerheidsrechtTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZekerheidsrechtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zekerheidsrecht.class);
        Zekerheidsrecht zekerheidsrecht1 = getZekerheidsrechtSample1();
        Zekerheidsrecht zekerheidsrecht2 = new Zekerheidsrecht();
        assertThat(zekerheidsrecht1).isNotEqualTo(zekerheidsrecht2);

        zekerheidsrecht2.setId(zekerheidsrecht1.getId());
        assertThat(zekerheidsrecht1).isEqualTo(zekerheidsrecht2);

        zekerheidsrecht2 = getZekerheidsrechtSample2();
        assertThat(zekerheidsrecht1).isNotEqualTo(zekerheidsrecht2);
    }

    @Test
    void bezwaartTenaamstellingTest() throws Exception {
        Zekerheidsrecht zekerheidsrecht = getZekerheidsrechtRandomSampleGenerator();
        Tenaamstelling tenaamstellingBack = getTenaamstellingRandomSampleGenerator();

        zekerheidsrecht.setBezwaartTenaamstelling(tenaamstellingBack);
        assertThat(zekerheidsrecht.getBezwaartTenaamstelling()).isEqualTo(tenaamstellingBack);

        zekerheidsrecht.bezwaartTenaamstelling(null);
        assertThat(zekerheidsrecht.getBezwaartTenaamstelling()).isNull();
    }
}
