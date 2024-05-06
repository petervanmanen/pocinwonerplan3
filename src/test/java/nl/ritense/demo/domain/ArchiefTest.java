package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArchiefTestSamples.*;
import static nl.ritense.demo.domain.ArchiefcategorieTestSamples.*;
import static nl.ritense.demo.domain.ArchiefstukTestSamples.*;
import static nl.ritense.demo.domain.IndelingTestSamples.*;
import static nl.ritense.demo.domain.PeriodeTestSamples.*;
import static nl.ritense.demo.domain.RechthebbendeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArchiefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Archief.class);
        Archief archief1 = getArchiefSample1();
        Archief archief2 = new Archief();
        assertThat(archief1).isNotEqualTo(archief2);

        archief2.setId(archief1.getId());
        assertThat(archief1).isEqualTo(archief2);

        archief2 = getArchiefSample2();
        assertThat(archief1).isNotEqualTo(archief2);
    }

    @Test
    void heeftRechthebbendeTest() throws Exception {
        Archief archief = getArchiefRandomSampleGenerator();
        Rechthebbende rechthebbendeBack = getRechthebbendeRandomSampleGenerator();

        archief.setHeeftRechthebbende(rechthebbendeBack);
        assertThat(archief.getHeeftRechthebbende()).isEqualTo(rechthebbendeBack);

        archief.heeftRechthebbende(null);
        assertThat(archief.getHeeftRechthebbende()).isNull();
    }

    @Test
    void valtbinnenArchiefcategorieTest() throws Exception {
        Archief archief = getArchiefRandomSampleGenerator();
        Archiefcategorie archiefcategorieBack = getArchiefcategorieRandomSampleGenerator();

        archief.addValtbinnenArchiefcategorie(archiefcategorieBack);
        assertThat(archief.getValtbinnenArchiefcategories()).containsOnly(archiefcategorieBack);

        archief.removeValtbinnenArchiefcategorie(archiefcategorieBack);
        assertThat(archief.getValtbinnenArchiefcategories()).doesNotContain(archiefcategorieBack);

        archief.valtbinnenArchiefcategories(new HashSet<>(Set.of(archiefcategorieBack)));
        assertThat(archief.getValtbinnenArchiefcategories()).containsOnly(archiefcategorieBack);

        archief.setValtbinnenArchiefcategories(new HashSet<>());
        assertThat(archief.getValtbinnenArchiefcategories()).doesNotContain(archiefcategorieBack);
    }

    @Test
    void stamtuitPeriodeTest() throws Exception {
        Archief archief = getArchiefRandomSampleGenerator();
        Periode periodeBack = getPeriodeRandomSampleGenerator();

        archief.addStamtuitPeriode(periodeBack);
        assertThat(archief.getStamtuitPeriodes()).containsOnly(periodeBack);

        archief.removeStamtuitPeriode(periodeBack);
        assertThat(archief.getStamtuitPeriodes()).doesNotContain(periodeBack);

        archief.stamtuitPeriodes(new HashSet<>(Set.of(periodeBack)));
        assertThat(archief.getStamtuitPeriodes()).containsOnly(periodeBack);

        archief.setStamtuitPeriodes(new HashSet<>());
        assertThat(archief.getStamtuitPeriodes()).doesNotContain(periodeBack);
    }

    @Test
    void isonderdeelvanArchiefstukTest() throws Exception {
        Archief archief = getArchiefRandomSampleGenerator();
        Archiefstuk archiefstukBack = getArchiefstukRandomSampleGenerator();

        archief.addIsonderdeelvanArchiefstuk(archiefstukBack);
        assertThat(archief.getIsonderdeelvanArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getIsonderdeelvanArchief()).isEqualTo(archief);

        archief.removeIsonderdeelvanArchiefstuk(archiefstukBack);
        assertThat(archief.getIsonderdeelvanArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getIsonderdeelvanArchief()).isNull();

        archief.isonderdeelvanArchiefstuks(new HashSet<>(Set.of(archiefstukBack)));
        assertThat(archief.getIsonderdeelvanArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getIsonderdeelvanArchief()).isEqualTo(archief);

        archief.setIsonderdeelvanArchiefstuks(new HashSet<>());
        assertThat(archief.getIsonderdeelvanArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getIsonderdeelvanArchief()).isNull();
    }

    @Test
    void hoortbijIndelingTest() throws Exception {
        Archief archief = getArchiefRandomSampleGenerator();
        Indeling indelingBack = getIndelingRandomSampleGenerator();

        archief.addHoortbijIndeling(indelingBack);
        assertThat(archief.getHoortbijIndelings()).containsOnly(indelingBack);
        assertThat(indelingBack.getHoortbijArchief()).isEqualTo(archief);

        archief.removeHoortbijIndeling(indelingBack);
        assertThat(archief.getHoortbijIndelings()).doesNotContain(indelingBack);
        assertThat(indelingBack.getHoortbijArchief()).isNull();

        archief.hoortbijIndelings(new HashSet<>(Set.of(indelingBack)));
        assertThat(archief.getHoortbijIndelings()).containsOnly(indelingBack);
        assertThat(indelingBack.getHoortbijArchief()).isEqualTo(archief);

        archief.setHoortbijIndelings(new HashSet<>());
        assertThat(archief.getHoortbijIndelings()).doesNotContain(indelingBack);
        assertThat(indelingBack.getHoortbijArchief()).isNull();
    }
}
