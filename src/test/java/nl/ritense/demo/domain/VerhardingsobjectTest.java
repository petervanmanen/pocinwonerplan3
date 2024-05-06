package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerhardingsobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerhardingsobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verhardingsobject.class);
        Verhardingsobject verhardingsobject1 = getVerhardingsobjectSample1();
        Verhardingsobject verhardingsobject2 = new Verhardingsobject();
        assertThat(verhardingsobject1).isNotEqualTo(verhardingsobject2);

        verhardingsobject2.setId(verhardingsobject1.getId());
        assertThat(verhardingsobject1).isEqualTo(verhardingsobject2);

        verhardingsobject2 = getVerhardingsobjectSample2();
        assertThat(verhardingsobject1).isNotEqualTo(verhardingsobject2);
    }
}
