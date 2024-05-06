package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IndienerTestSamples.*;
import static nl.ritense.demo.domain.KadastralemutatieTestSamples.*;
import static nl.ritense.demo.domain.ParkeervergunningTestSamples.*;
import static nl.ritense.demo.domain.RapportagemomentTestSamples.*;
import static nl.ritense.demo.domain.RechtspersoonTestSamples.*;
import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static nl.ritense.demo.domain.TaakTestSamples.*;
import static nl.ritense.demo.domain.TenaamstellingTestSamples.*;
import static nl.ritense.demo.domain.VastgoedcontractTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RechtspersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rechtspersoon.class);
        Rechtspersoon rechtspersoon1 = getRechtspersoonSample1();
        Rechtspersoon rechtspersoon2 = new Rechtspersoon();
        assertThat(rechtspersoon1).isNotEqualTo(rechtspersoon2);

        rechtspersoon2.setId(rechtspersoon1.getId());
        assertThat(rechtspersoon1).isEqualTo(rechtspersoon2);

        rechtspersoon2 = getRechtspersoonSample2();
        assertThat(rechtspersoon1).isNotEqualTo(rechtspersoon2);
    }

    @Test
    void projectleiderRapportagemomentTest() throws Exception {
        Rechtspersoon rechtspersoon = getRechtspersoonRandomSampleGenerator();
        Rapportagemoment rapportagemomentBack = getRapportagemomentRandomSampleGenerator();

        rechtspersoon.addProjectleiderRapportagemoment(rapportagemomentBack);
        assertThat(rechtspersoon.getProjectleiderRapportagemoments()).containsOnly(rapportagemomentBack);
        assertThat(rapportagemomentBack.getProjectleiderRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.removeProjectleiderRapportagemoment(rapportagemomentBack);
        assertThat(rechtspersoon.getProjectleiderRapportagemoments()).doesNotContain(rapportagemomentBack);
        assertThat(rapportagemomentBack.getProjectleiderRechtspersoon()).isNull();

        rechtspersoon.projectleiderRapportagemoments(new HashSet<>(Set.of(rapportagemomentBack)));
        assertThat(rechtspersoon.getProjectleiderRapportagemoments()).containsOnly(rapportagemomentBack);
        assertThat(rapportagemomentBack.getProjectleiderRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.setProjectleiderRapportagemoments(new HashSet<>());
        assertThat(rechtspersoon.getProjectleiderRapportagemoments()).doesNotContain(rapportagemomentBack);
        assertThat(rapportagemomentBack.getProjectleiderRechtspersoon()).isNull();
    }

    @Test
    void aanvragerSubsidieTest() throws Exception {
        Rechtspersoon rechtspersoon = getRechtspersoonRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        rechtspersoon.addAanvragerSubsidie(subsidieBack);
        assertThat(rechtspersoon.getAanvragerSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getAanvragerRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.removeAanvragerSubsidie(subsidieBack);
        assertThat(rechtspersoon.getAanvragerSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getAanvragerRechtspersoon()).isNull();

        rechtspersoon.aanvragerSubsidies(new HashSet<>(Set.of(subsidieBack)));
        assertThat(rechtspersoon.getAanvragerSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getAanvragerRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.setAanvragerSubsidies(new HashSet<>());
        assertThat(rechtspersoon.getAanvragerSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getAanvragerRechtspersoon()).isNull();
    }

    @Test
    void heeftTenaamstellingTest() throws Exception {
        Rechtspersoon rechtspersoon = getRechtspersoonRandomSampleGenerator();
        Tenaamstelling tenaamstellingBack = getTenaamstellingRandomSampleGenerator();

        rechtspersoon.addHeeftTenaamstelling(tenaamstellingBack);
        assertThat(rechtspersoon.getHeeftTenaamstellings()).containsOnly(tenaamstellingBack);
        assertThat(tenaamstellingBack.getHeeftRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.removeHeeftTenaamstelling(tenaamstellingBack);
        assertThat(rechtspersoon.getHeeftTenaamstellings()).doesNotContain(tenaamstellingBack);
        assertThat(tenaamstellingBack.getHeeftRechtspersoon()).isNull();

        rechtspersoon.heeftTenaamstellings(new HashSet<>(Set.of(tenaamstellingBack)));
        assertThat(rechtspersoon.getHeeftTenaamstellings()).containsOnly(tenaamstellingBack);
        assertThat(tenaamstellingBack.getHeeftRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.setHeeftTenaamstellings(new HashSet<>());
        assertThat(rechtspersoon.getHeeftTenaamstellings()).doesNotContain(tenaamstellingBack);
        assertThat(tenaamstellingBack.getHeeftRechtspersoon()).isNull();
    }

    @Test
    void betrokkenenKadastralemutatieTest() throws Exception {
        Rechtspersoon rechtspersoon = getRechtspersoonRandomSampleGenerator();
        Kadastralemutatie kadastralemutatieBack = getKadastralemutatieRandomSampleGenerator();

        rechtspersoon.addBetrokkenenKadastralemutatie(kadastralemutatieBack);
        assertThat(rechtspersoon.getBetrokkenenKadastralemutaties()).containsOnly(kadastralemutatieBack);

        rechtspersoon.removeBetrokkenenKadastralemutatie(kadastralemutatieBack);
        assertThat(rechtspersoon.getBetrokkenenKadastralemutaties()).doesNotContain(kadastralemutatieBack);

        rechtspersoon.betrokkenenKadastralemutaties(new HashSet<>(Set.of(kadastralemutatieBack)));
        assertThat(rechtspersoon.getBetrokkenenKadastralemutaties()).containsOnly(kadastralemutatieBack);

        rechtspersoon.setBetrokkenenKadastralemutaties(new HashSet<>());
        assertThat(rechtspersoon.getBetrokkenenKadastralemutaties()).doesNotContain(kadastralemutatieBack);
    }

    @Test
    void isIndienerTest() throws Exception {
        Rechtspersoon rechtspersoon = getRechtspersoonRandomSampleGenerator();
        Indiener indienerBack = getIndienerRandomSampleGenerator();

        rechtspersoon.setIsIndiener(indienerBack);
        assertThat(rechtspersoon.getIsIndiener()).isEqualTo(indienerBack);
        assertThat(indienerBack.getIsRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.isIndiener(null);
        assertThat(rechtspersoon.getIsIndiener()).isNull();
        assertThat(indienerBack.getIsRechtspersoon()).isNull();
    }

    @Test
    void houderParkeervergunningTest() throws Exception {
        Rechtspersoon rechtspersoon = getRechtspersoonRandomSampleGenerator();
        Parkeervergunning parkeervergunningBack = getParkeervergunningRandomSampleGenerator();

        rechtspersoon.addHouderParkeervergunning(parkeervergunningBack);
        assertThat(rechtspersoon.getHouderParkeervergunnings()).containsOnly(parkeervergunningBack);
        assertThat(parkeervergunningBack.getHouderRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.removeHouderParkeervergunning(parkeervergunningBack);
        assertThat(rechtspersoon.getHouderParkeervergunnings()).doesNotContain(parkeervergunningBack);
        assertThat(parkeervergunningBack.getHouderRechtspersoon()).isNull();

        rechtspersoon.houderParkeervergunnings(new HashSet<>(Set.of(parkeervergunningBack)));
        assertThat(rechtspersoon.getHouderParkeervergunnings()).containsOnly(parkeervergunningBack);
        assertThat(parkeervergunningBack.getHouderRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.setHouderParkeervergunnings(new HashSet<>());
        assertThat(rechtspersoon.getHouderParkeervergunnings()).doesNotContain(parkeervergunningBack);
        assertThat(parkeervergunningBack.getHouderRechtspersoon()).isNull();
    }

    @Test
    void verstrekkerSubsidieTest() throws Exception {
        Rechtspersoon rechtspersoon = getRechtspersoonRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        rechtspersoon.addVerstrekkerSubsidie(subsidieBack);
        assertThat(rechtspersoon.getVerstrekkerSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getVerstrekkerRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.removeVerstrekkerSubsidie(subsidieBack);
        assertThat(rechtspersoon.getVerstrekkerSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getVerstrekkerRechtspersoon()).isNull();

        rechtspersoon.verstrekkerSubsidies(new HashSet<>(Set.of(subsidieBack)));
        assertThat(rechtspersoon.getVerstrekkerSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getVerstrekkerRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.setVerstrekkerSubsidies(new HashSet<>());
        assertThat(rechtspersoon.getVerstrekkerSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getVerstrekkerRechtspersoon()).isNull();
    }

    @Test
    void projectleiderTaakTest() throws Exception {
        Rechtspersoon rechtspersoon = getRechtspersoonRandomSampleGenerator();
        Taak taakBack = getTaakRandomSampleGenerator();

        rechtspersoon.addProjectleiderTaak(taakBack);
        assertThat(rechtspersoon.getProjectleiderTaaks()).containsOnly(taakBack);
        assertThat(taakBack.getProjectleiderRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.removeProjectleiderTaak(taakBack);
        assertThat(rechtspersoon.getProjectleiderTaaks()).doesNotContain(taakBack);
        assertThat(taakBack.getProjectleiderRechtspersoon()).isNull();

        rechtspersoon.projectleiderTaaks(new HashSet<>(Set.of(taakBack)));
        assertThat(rechtspersoon.getProjectleiderTaaks()).containsOnly(taakBack);
        assertThat(taakBack.getProjectleiderRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.setProjectleiderTaaks(new HashSet<>());
        assertThat(rechtspersoon.getProjectleiderTaaks()).doesNotContain(taakBack);
        assertThat(taakBack.getProjectleiderRechtspersoon()).isNull();
    }

    @Test
    void heeftVastgoedcontractTest() throws Exception {
        Rechtspersoon rechtspersoon = getRechtspersoonRandomSampleGenerator();
        Vastgoedcontract vastgoedcontractBack = getVastgoedcontractRandomSampleGenerator();

        rechtspersoon.addHeeftVastgoedcontract(vastgoedcontractBack);
        assertThat(rechtspersoon.getHeeftVastgoedcontracts()).containsOnly(vastgoedcontractBack);
        assertThat(vastgoedcontractBack.getHeeftRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.removeHeeftVastgoedcontract(vastgoedcontractBack);
        assertThat(rechtspersoon.getHeeftVastgoedcontracts()).doesNotContain(vastgoedcontractBack);
        assertThat(vastgoedcontractBack.getHeeftRechtspersoon()).isNull();

        rechtspersoon.heeftVastgoedcontracts(new HashSet<>(Set.of(vastgoedcontractBack)));
        assertThat(rechtspersoon.getHeeftVastgoedcontracts()).containsOnly(vastgoedcontractBack);
        assertThat(vastgoedcontractBack.getHeeftRechtspersoon()).isEqualTo(rechtspersoon);

        rechtspersoon.setHeeftVastgoedcontracts(new HashSet<>());
        assertThat(rechtspersoon.getHeeftVastgoedcontracts()).doesNotContain(vastgoedcontractBack);
        assertThat(vastgoedcontractBack.getHeeftRechtspersoon()).isNull();
    }
}
