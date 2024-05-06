package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SectorTestSamples.*;
import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SectorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sector.class);
        Sector sector1 = getSectorSample1();
        Sector sector2 = new Sector();
        assertThat(sector1).isNotEqualTo(sector2);

        sector2.setId(sector1.getId());
        assertThat(sector1).isEqualTo(sector2);

        sector2 = getSectorSample2();
        assertThat(sector1).isNotEqualTo(sector2);
    }

    @Test
    void valtbinnenSubsidieTest() throws Exception {
        Sector sector = getSectorRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        sector.addValtbinnenSubsidie(subsidieBack);
        assertThat(sector.getValtbinnenSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getValtbinnenSector()).isEqualTo(sector);

        sector.removeValtbinnenSubsidie(subsidieBack);
        assertThat(sector.getValtbinnenSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getValtbinnenSector()).isNull();

        sector.valtbinnenSubsidies(new HashSet<>(Set.of(subsidieBack)));
        assertThat(sector.getValtbinnenSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getValtbinnenSector()).isEqualTo(sector);

        sector.setValtbinnenSubsidies(new HashSet<>());
        assertThat(sector.getValtbinnenSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getValtbinnenSector()).isNull();
    }
}
