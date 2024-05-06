package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WeginrichtingsobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WeginrichtingsobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Weginrichtingsobject.class);
        Weginrichtingsobject weginrichtingsobject1 = getWeginrichtingsobjectSample1();
        Weginrichtingsobject weginrichtingsobject2 = new Weginrichtingsobject();
        assertThat(weginrichtingsobject1).isNotEqualTo(weginrichtingsobject2);

        weginrichtingsobject2.setId(weginrichtingsobject1.getId());
        assertThat(weginrichtingsobject1).isEqualTo(weginrichtingsobject2);

        weginrichtingsobject2 = getWeginrichtingsobjectSample2();
        assertThat(weginrichtingsobject1).isNotEqualTo(weginrichtingsobject2);
    }
}
