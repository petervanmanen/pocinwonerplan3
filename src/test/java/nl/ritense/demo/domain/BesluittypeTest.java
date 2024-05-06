package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BesluitTestSamples.*;
import static nl.ritense.demo.domain.BesluittypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BesluittypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Besluittype.class);
        Besluittype besluittype1 = getBesluittypeSample1();
        Besluittype besluittype2 = new Besluittype();
        assertThat(besluittype1).isNotEqualTo(besluittype2);

        besluittype2.setId(besluittype1.getId());
        assertThat(besluittype1).isEqualTo(besluittype2);

        besluittype2 = getBesluittypeSample2();
        assertThat(besluittype1).isNotEqualTo(besluittype2);
    }

    @Test
    void isvanBesluitTest() throws Exception {
        Besluittype besluittype = getBesluittypeRandomSampleGenerator();
        Besluit besluitBack = getBesluitRandomSampleGenerator();

        besluittype.addIsvanBesluit(besluitBack);
        assertThat(besluittype.getIsvanBesluits()).containsOnly(besluitBack);
        assertThat(besluitBack.getIsvanBesluittype()).isEqualTo(besluittype);

        besluittype.removeIsvanBesluit(besluitBack);
        assertThat(besluittype.getIsvanBesluits()).doesNotContain(besluitBack);
        assertThat(besluitBack.getIsvanBesluittype()).isNull();

        besluittype.isvanBesluits(new HashSet<>(Set.of(besluitBack)));
        assertThat(besluittype.getIsvanBesluits()).containsOnly(besluitBack);
        assertThat(besluitBack.getIsvanBesluittype()).isEqualTo(besluittype);

        besluittype.setIsvanBesluits(new HashSet<>());
        assertThat(besluittype.getIsvanBesluits()).doesNotContain(besluitBack);
        assertThat(besluitBack.getIsvanBesluittype()).isNull();
    }
}
