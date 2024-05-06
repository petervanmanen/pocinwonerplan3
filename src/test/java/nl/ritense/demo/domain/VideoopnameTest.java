package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VideoopnameTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VideoopnameTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Videoopname.class);
        Videoopname videoopname1 = getVideoopnameSample1();
        Videoopname videoopname2 = new Videoopname();
        assertThat(videoopname1).isNotEqualTo(videoopname2);

        videoopname2.setId(videoopname1.getId());
        assertThat(videoopname1).isEqualTo(videoopname2);

        videoopname2 = getVideoopnameSample2();
        assertThat(videoopname1).isNotEqualTo(videoopname2);
    }
}
