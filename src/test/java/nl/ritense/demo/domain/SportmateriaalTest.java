package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BinnenlocatieTestSamples.*;
import static nl.ritense.demo.domain.SportmateriaalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SportmateriaalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sportmateriaal.class);
        Sportmateriaal sportmateriaal1 = getSportmateriaalSample1();
        Sportmateriaal sportmateriaal2 = new Sportmateriaal();
        assertThat(sportmateriaal1).isNotEqualTo(sportmateriaal2);

        sportmateriaal2.setId(sportmateriaal1.getId());
        assertThat(sportmateriaal1).isEqualTo(sportmateriaal2);

        sportmateriaal2 = getSportmateriaalSample2();
        assertThat(sportmateriaal1).isNotEqualTo(sportmateriaal2);
    }

    @Test
    void heeftBinnenlocatieTest() throws Exception {
        Sportmateriaal sportmateriaal = getSportmateriaalRandomSampleGenerator();
        Binnenlocatie binnenlocatieBack = getBinnenlocatieRandomSampleGenerator();

        sportmateriaal.addHeeftBinnenlocatie(binnenlocatieBack);
        assertThat(sportmateriaal.getHeeftBinnenlocaties()).containsOnly(binnenlocatieBack);
        assertThat(binnenlocatieBack.getHeeftSportmateriaals()).containsOnly(sportmateriaal);

        sportmateriaal.removeHeeftBinnenlocatie(binnenlocatieBack);
        assertThat(sportmateriaal.getHeeftBinnenlocaties()).doesNotContain(binnenlocatieBack);
        assertThat(binnenlocatieBack.getHeeftSportmateriaals()).doesNotContain(sportmateriaal);

        sportmateriaal.heeftBinnenlocaties(new HashSet<>(Set.of(binnenlocatieBack)));
        assertThat(sportmateriaal.getHeeftBinnenlocaties()).containsOnly(binnenlocatieBack);
        assertThat(binnenlocatieBack.getHeeftSportmateriaals()).containsOnly(sportmateriaal);

        sportmateriaal.setHeeftBinnenlocaties(new HashSet<>());
        assertThat(sportmateriaal.getHeeftBinnenlocaties()).doesNotContain(binnenlocatieBack);
        assertThat(binnenlocatieBack.getHeeftSportmateriaals()).doesNotContain(sportmateriaal);
    }
}
