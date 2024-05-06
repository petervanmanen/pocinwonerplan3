package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RegelingTestSamples.*;
import static nl.ritense.demo.domain.RegelingsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegelingsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regelingsoort.class);
        Regelingsoort regelingsoort1 = getRegelingsoortSample1();
        Regelingsoort regelingsoort2 = new Regelingsoort();
        assertThat(regelingsoort1).isNotEqualTo(regelingsoort2);

        regelingsoort2.setId(regelingsoort1.getId());
        assertThat(regelingsoort1).isEqualTo(regelingsoort2);

        regelingsoort2 = getRegelingsoortSample2();
        assertThat(regelingsoort1).isNotEqualTo(regelingsoort2);
    }

    @Test
    void isregelingsoortRegelingTest() throws Exception {
        Regelingsoort regelingsoort = getRegelingsoortRandomSampleGenerator();
        Regeling regelingBack = getRegelingRandomSampleGenerator();

        regelingsoort.addIsregelingsoortRegeling(regelingBack);
        assertThat(regelingsoort.getIsregelingsoortRegelings()).containsOnly(regelingBack);
        assertThat(regelingBack.getIsregelingsoortRegelingsoort()).isEqualTo(regelingsoort);

        regelingsoort.removeIsregelingsoortRegeling(regelingBack);
        assertThat(regelingsoort.getIsregelingsoortRegelings()).doesNotContain(regelingBack);
        assertThat(regelingBack.getIsregelingsoortRegelingsoort()).isNull();

        regelingsoort.isregelingsoortRegelings(new HashSet<>(Set.of(regelingBack)));
        assertThat(regelingsoort.getIsregelingsoortRegelings()).containsOnly(regelingBack);
        assertThat(regelingBack.getIsregelingsoortRegelingsoort()).isEqualTo(regelingsoort);

        regelingsoort.setIsregelingsoortRegelings(new HashSet<>());
        assertThat(regelingsoort.getIsregelingsoortRegelings()).doesNotContain(regelingBack);
        assertThat(regelingBack.getIsregelingsoortRegelingsoort()).isNull();
    }
}
