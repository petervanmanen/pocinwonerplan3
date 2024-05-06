package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeerlingTestSamples.*;
import static nl.ritense.demo.domain.LoopbaanstapTestSamples.*;
import static nl.ritense.demo.domain.OnderwijsloopbaanTestSamples.*;
import static nl.ritense.demo.domain.SchoolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnderwijsloopbaanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onderwijsloopbaan.class);
        Onderwijsloopbaan onderwijsloopbaan1 = getOnderwijsloopbaanSample1();
        Onderwijsloopbaan onderwijsloopbaan2 = new Onderwijsloopbaan();
        assertThat(onderwijsloopbaan1).isNotEqualTo(onderwijsloopbaan2);

        onderwijsloopbaan2.setId(onderwijsloopbaan1.getId());
        assertThat(onderwijsloopbaan1).isEqualTo(onderwijsloopbaan2);

        onderwijsloopbaan2 = getOnderwijsloopbaanSample2();
        assertThat(onderwijsloopbaan1).isNotEqualTo(onderwijsloopbaan2);
    }

    @Test
    void emptyLoopbaanstapTest() throws Exception {
        Onderwijsloopbaan onderwijsloopbaan = getOnderwijsloopbaanRandomSampleGenerator();
        Loopbaanstap loopbaanstapBack = getLoopbaanstapRandomSampleGenerator();

        onderwijsloopbaan.addEmptyLoopbaanstap(loopbaanstapBack);
        assertThat(onderwijsloopbaan.getEmptyLoopbaanstaps()).containsOnly(loopbaanstapBack);
        assertThat(loopbaanstapBack.getEmptyOnderwijsloopbaan()).isEqualTo(onderwijsloopbaan);

        onderwijsloopbaan.removeEmptyLoopbaanstap(loopbaanstapBack);
        assertThat(onderwijsloopbaan.getEmptyLoopbaanstaps()).doesNotContain(loopbaanstapBack);
        assertThat(loopbaanstapBack.getEmptyOnderwijsloopbaan()).isNull();

        onderwijsloopbaan.emptyLoopbaanstaps(new HashSet<>(Set.of(loopbaanstapBack)));
        assertThat(onderwijsloopbaan.getEmptyLoopbaanstaps()).containsOnly(loopbaanstapBack);
        assertThat(loopbaanstapBack.getEmptyOnderwijsloopbaan()).isEqualTo(onderwijsloopbaan);

        onderwijsloopbaan.setEmptyLoopbaanstaps(new HashSet<>());
        assertThat(onderwijsloopbaan.getEmptyLoopbaanstaps()).doesNotContain(loopbaanstapBack);
        assertThat(loopbaanstapBack.getEmptyOnderwijsloopbaan()).isNull();
    }

    @Test
    void heeftLeerlingTest() throws Exception {
        Onderwijsloopbaan onderwijsloopbaan = getOnderwijsloopbaanRandomSampleGenerator();
        Leerling leerlingBack = getLeerlingRandomSampleGenerator();

        onderwijsloopbaan.setHeeftLeerling(leerlingBack);
        assertThat(onderwijsloopbaan.getHeeftLeerling()).isEqualTo(leerlingBack);

        onderwijsloopbaan.heeftLeerling(null);
        assertThat(onderwijsloopbaan.getHeeftLeerling()).isNull();
    }

    @Test
    void kentSchoolTest() throws Exception {
        Onderwijsloopbaan onderwijsloopbaan = getOnderwijsloopbaanRandomSampleGenerator();
        School schoolBack = getSchoolRandomSampleGenerator();

        onderwijsloopbaan.addKentSchool(schoolBack);
        assertThat(onderwijsloopbaan.getKentSchools()).containsOnly(schoolBack);
        assertThat(schoolBack.getKentOnderwijsloopbaans()).containsOnly(onderwijsloopbaan);

        onderwijsloopbaan.removeKentSchool(schoolBack);
        assertThat(onderwijsloopbaan.getKentSchools()).doesNotContain(schoolBack);
        assertThat(schoolBack.getKentOnderwijsloopbaans()).doesNotContain(onderwijsloopbaan);

        onderwijsloopbaan.kentSchools(new HashSet<>(Set.of(schoolBack)));
        assertThat(onderwijsloopbaan.getKentSchools()).containsOnly(schoolBack);
        assertThat(schoolBack.getKentOnderwijsloopbaans()).containsOnly(onderwijsloopbaan);

        onderwijsloopbaan.setKentSchools(new HashSet<>());
        assertThat(onderwijsloopbaan.getKentSchools()).doesNotContain(schoolBack);
        assertThat(schoolBack.getKentOnderwijsloopbaans()).doesNotContain(onderwijsloopbaan);
    }
}
