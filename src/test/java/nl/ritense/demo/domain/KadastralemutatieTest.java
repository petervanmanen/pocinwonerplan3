package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KadastralemutatieTestSamples.*;
import static nl.ritense.demo.domain.RechtspersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KadastralemutatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kadastralemutatie.class);
        Kadastralemutatie kadastralemutatie1 = getKadastralemutatieSample1();
        Kadastralemutatie kadastralemutatie2 = new Kadastralemutatie();
        assertThat(kadastralemutatie1).isNotEqualTo(kadastralemutatie2);

        kadastralemutatie2.setId(kadastralemutatie1.getId());
        assertThat(kadastralemutatie1).isEqualTo(kadastralemutatie2);

        kadastralemutatie2 = getKadastralemutatieSample2();
        assertThat(kadastralemutatie1).isNotEqualTo(kadastralemutatie2);
    }

    @Test
    void betrokkenenRechtspersoonTest() throws Exception {
        Kadastralemutatie kadastralemutatie = getKadastralemutatieRandomSampleGenerator();
        Rechtspersoon rechtspersoonBack = getRechtspersoonRandomSampleGenerator();

        kadastralemutatie.addBetrokkenenRechtspersoon(rechtspersoonBack);
        assertThat(kadastralemutatie.getBetrokkenenRechtspersoons()).containsOnly(rechtspersoonBack);
        assertThat(rechtspersoonBack.getBetrokkenenKadastralemutaties()).containsOnly(kadastralemutatie);

        kadastralemutatie.removeBetrokkenenRechtspersoon(rechtspersoonBack);
        assertThat(kadastralemutatie.getBetrokkenenRechtspersoons()).doesNotContain(rechtspersoonBack);
        assertThat(rechtspersoonBack.getBetrokkenenKadastralemutaties()).doesNotContain(kadastralemutatie);

        kadastralemutatie.betrokkenenRechtspersoons(new HashSet<>(Set.of(rechtspersoonBack)));
        assertThat(kadastralemutatie.getBetrokkenenRechtspersoons()).containsOnly(rechtspersoonBack);
        assertThat(rechtspersoonBack.getBetrokkenenKadastralemutaties()).containsOnly(kadastralemutatie);

        kadastralemutatie.setBetrokkenenRechtspersoons(new HashSet<>());
        assertThat(kadastralemutatie.getBetrokkenenRechtspersoons()).doesNotContain(rechtspersoonBack);
        assertThat(rechtspersoonBack.getBetrokkenenKadastralemutaties()).doesNotContain(kadastralemutatie);
    }
}
