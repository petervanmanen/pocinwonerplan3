package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArchiefTestSamples.*;
import static nl.ritense.demo.domain.ArchiefcategorieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArchiefcategorieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Archiefcategorie.class);
        Archiefcategorie archiefcategorie1 = getArchiefcategorieSample1();
        Archiefcategorie archiefcategorie2 = new Archiefcategorie();
        assertThat(archiefcategorie1).isNotEqualTo(archiefcategorie2);

        archiefcategorie2.setId(archiefcategorie1.getId());
        assertThat(archiefcategorie1).isEqualTo(archiefcategorie2);

        archiefcategorie2 = getArchiefcategorieSample2();
        assertThat(archiefcategorie1).isNotEqualTo(archiefcategorie2);
    }

    @Test
    void valtbinnenArchiefTest() throws Exception {
        Archiefcategorie archiefcategorie = getArchiefcategorieRandomSampleGenerator();
        Archief archiefBack = getArchiefRandomSampleGenerator();

        archiefcategorie.addValtbinnenArchief(archiefBack);
        assertThat(archiefcategorie.getValtbinnenArchiefs()).containsOnly(archiefBack);
        assertThat(archiefBack.getValtbinnenArchiefcategories()).containsOnly(archiefcategorie);

        archiefcategorie.removeValtbinnenArchief(archiefBack);
        assertThat(archiefcategorie.getValtbinnenArchiefs()).doesNotContain(archiefBack);
        assertThat(archiefBack.getValtbinnenArchiefcategories()).doesNotContain(archiefcategorie);

        archiefcategorie.valtbinnenArchiefs(new HashSet<>(Set.of(archiefBack)));
        assertThat(archiefcategorie.getValtbinnenArchiefs()).containsOnly(archiefBack);
        assertThat(archiefBack.getValtbinnenArchiefcategories()).containsOnly(archiefcategorie);

        archiefcategorie.setValtbinnenArchiefs(new HashSet<>());
        assertThat(archiefcategorie.getValtbinnenArchiefs()).doesNotContain(archiefBack);
        assertThat(archiefBack.getValtbinnenArchiefcategories()).doesNotContain(archiefcategorie);
    }
}
