package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RedenverkrijgingnationaliteitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RedenverkrijgingnationaliteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Redenverkrijgingnationaliteit.class);
        Redenverkrijgingnationaliteit redenverkrijgingnationaliteit1 = getRedenverkrijgingnationaliteitSample1();
        Redenverkrijgingnationaliteit redenverkrijgingnationaliteit2 = new Redenverkrijgingnationaliteit();
        assertThat(redenverkrijgingnationaliteit1).isNotEqualTo(redenverkrijgingnationaliteit2);

        redenverkrijgingnationaliteit2.setId(redenverkrijgingnationaliteit1.getId());
        assertThat(redenverkrijgingnationaliteit1).isEqualTo(redenverkrijgingnationaliteit2);

        redenverkrijgingnationaliteit2 = getRedenverkrijgingnationaliteitSample2();
        assertThat(redenverkrijgingnationaliteit1).isNotEqualTo(redenverkrijgingnationaliteit2);
    }
}
