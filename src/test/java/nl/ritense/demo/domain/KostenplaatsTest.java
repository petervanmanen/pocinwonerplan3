package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BegrotingregelTestSamples.*;
import static nl.ritense.demo.domain.FactuurTestSamples.*;
import static nl.ritense.demo.domain.HoofdrekeningTestSamples.*;
import static nl.ritense.demo.domain.InkooporderTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.MutatieTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static nl.ritense.demo.domain.ProgrammaTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.SubrekeningTestSamples.*;
import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static nl.ritense.demo.domain.SubsidiecomponentTestSamples.*;
import static nl.ritense.demo.domain.TaakveldTestSamples.*;
import static nl.ritense.demo.domain.VastgoedobjectTestSamples.*;
import static nl.ritense.demo.domain.WerkorderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KostenplaatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kostenplaats.class);
        Kostenplaats kostenplaats1 = getKostenplaatsSample1();
        Kostenplaats kostenplaats2 = new Kostenplaats();
        assertThat(kostenplaats1).isNotEqualTo(kostenplaats2);

        kostenplaats2.setId(kostenplaats1.getId());
        assertThat(kostenplaats1).isEqualTo(kostenplaats2);

        kostenplaats2 = getKostenplaatsSample2();
        assertThat(kostenplaats1).isNotEqualTo(kostenplaats2);
    }

    @Test
    void heeftVastgoedobjectTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Vastgoedobject vastgoedobjectBack = getVastgoedobjectRandomSampleGenerator();

        kostenplaats.addHeeftVastgoedobject(vastgoedobjectBack);
        assertThat(kostenplaats.getHeeftVastgoedobjects()).containsOnly(vastgoedobjectBack);
        assertThat(vastgoedobjectBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeHeeftVastgoedobject(vastgoedobjectBack);
        assertThat(kostenplaats.getHeeftVastgoedobjects()).doesNotContain(vastgoedobjectBack);
        assertThat(vastgoedobjectBack.getHeeftKostenplaats()).isNull();

        kostenplaats.heeftVastgoedobjects(new HashSet<>(Set.of(vastgoedobjectBack)));
        assertThat(kostenplaats.getHeeftVastgoedobjects()).containsOnly(vastgoedobjectBack);
        assertThat(vastgoedobjectBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setHeeftVastgoedobjects(new HashSet<>());
        assertThat(kostenplaats.getHeeftVastgoedobjects()).doesNotContain(vastgoedobjectBack);
        assertThat(vastgoedobjectBack.getHeeftKostenplaats()).isNull();
    }

    @Test
    void heeftWerkorderTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Werkorder werkorderBack = getWerkorderRandomSampleGenerator();

        kostenplaats.addHeeftWerkorder(werkorderBack);
        assertThat(kostenplaats.getHeeftWerkorders()).containsOnly(werkorderBack);
        assertThat(werkorderBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeHeeftWerkorder(werkorderBack);
        assertThat(kostenplaats.getHeeftWerkorders()).doesNotContain(werkorderBack);
        assertThat(werkorderBack.getHeeftKostenplaats()).isNull();

        kostenplaats.heeftWerkorders(new HashSet<>(Set.of(werkorderBack)));
        assertThat(kostenplaats.getHeeftWerkorders()).containsOnly(werkorderBack);
        assertThat(werkorderBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setHeeftWerkorders(new HashSet<>());
        assertThat(kostenplaats.getHeeftWerkorders()).doesNotContain(werkorderBack);
        assertThat(werkorderBack.getHeeftKostenplaats()).isNull();
    }

    @Test
    void heeftSubrekeningTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Subrekening subrekeningBack = getSubrekeningRandomSampleGenerator();

        kostenplaats.addHeeftSubrekening(subrekeningBack);
        assertThat(kostenplaats.getHeeftSubrekenings()).containsOnly(subrekeningBack);
        assertThat(subrekeningBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeHeeftSubrekening(subrekeningBack);
        assertThat(kostenplaats.getHeeftSubrekenings()).doesNotContain(subrekeningBack);
        assertThat(subrekeningBack.getHeeftKostenplaats()).isNull();

        kostenplaats.heeftSubrekenings(new HashSet<>(Set.of(subrekeningBack)));
        assertThat(kostenplaats.getHeeftSubrekenings()).containsOnly(subrekeningBack);
        assertThat(subrekeningBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setHeeftSubrekenings(new HashSet<>());
        assertThat(kostenplaats.getHeeftSubrekenings()).doesNotContain(subrekeningBack);
        assertThat(subrekeningBack.getHeeftKostenplaats()).isNull();
    }

    @Test
    void heeftInkooporderTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        kostenplaats.addHeeftInkooporder(inkooporderBack);
        assertThat(kostenplaats.getHeeftInkooporders()).containsOnly(inkooporderBack);

        kostenplaats.removeHeeftInkooporder(inkooporderBack);
        assertThat(kostenplaats.getHeeftInkooporders()).doesNotContain(inkooporderBack);

        kostenplaats.heeftInkooporders(new HashSet<>(Set.of(inkooporderBack)));
        assertThat(kostenplaats.getHeeftInkooporders()).containsOnly(inkooporderBack);

        kostenplaats.setHeeftInkooporders(new HashSet<>());
        assertThat(kostenplaats.getHeeftInkooporders()).doesNotContain(inkooporderBack);
    }

    @Test
    void heeftTaakveldTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Taakveld taakveldBack = getTaakveldRandomSampleGenerator();

        kostenplaats.addHeeftTaakveld(taakveldBack);
        assertThat(kostenplaats.getHeeftTaakvelds()).containsOnly(taakveldBack);

        kostenplaats.removeHeeftTaakveld(taakveldBack);
        assertThat(kostenplaats.getHeeftTaakvelds()).doesNotContain(taakveldBack);

        kostenplaats.heeftTaakvelds(new HashSet<>(Set.of(taakveldBack)));
        assertThat(kostenplaats.getHeeftTaakvelds()).containsOnly(taakveldBack);

        kostenplaats.setHeeftTaakvelds(new HashSet<>());
        assertThat(kostenplaats.getHeeftTaakvelds()).doesNotContain(taakveldBack);
    }

    @Test
    void heeftProgrammaTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Programma programmaBack = getProgrammaRandomSampleGenerator();

        kostenplaats.addHeeftProgramma(programmaBack);
        assertThat(kostenplaats.getHeeftProgrammas()).containsOnly(programmaBack);
        assertThat(programmaBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeHeeftProgramma(programmaBack);
        assertThat(kostenplaats.getHeeftProgrammas()).doesNotContain(programmaBack);
        assertThat(programmaBack.getHeeftKostenplaats()).isNull();

        kostenplaats.heeftProgrammas(new HashSet<>(Set.of(programmaBack)));
        assertThat(kostenplaats.getHeeftProgrammas()).containsOnly(programmaBack);
        assertThat(programmaBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setHeeftProgrammas(new HashSet<>());
        assertThat(kostenplaats.getHeeftProgrammas()).doesNotContain(programmaBack);
        assertThat(programmaBack.getHeeftKostenplaats()).isNull();
    }

    @Test
    void heeftSubsidieTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        kostenplaats.addHeeftSubsidie(subsidieBack);
        assertThat(kostenplaats.getHeeftSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeHeeftSubsidie(subsidieBack);
        assertThat(kostenplaats.getHeeftSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getHeeftKostenplaats()).isNull();

        kostenplaats.heeftSubsidies(new HashSet<>(Set.of(subsidieBack)));
        assertThat(kostenplaats.getHeeftSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setHeeftSubsidies(new HashSet<>());
        assertThat(kostenplaats.getHeeftSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getHeeftKostenplaats()).isNull();
    }

    @Test
    void heeftSubsidiecomponentTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Subsidiecomponent subsidiecomponentBack = getSubsidiecomponentRandomSampleGenerator();

        kostenplaats.addHeeftSubsidiecomponent(subsidiecomponentBack);
        assertThat(kostenplaats.getHeeftSubsidiecomponents()).containsOnly(subsidiecomponentBack);
        assertThat(subsidiecomponentBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeHeeftSubsidiecomponent(subsidiecomponentBack);
        assertThat(kostenplaats.getHeeftSubsidiecomponents()).doesNotContain(subsidiecomponentBack);
        assertThat(subsidiecomponentBack.getHeeftKostenplaats()).isNull();

        kostenplaats.heeftSubsidiecomponents(new HashSet<>(Set.of(subsidiecomponentBack)));
        assertThat(kostenplaats.getHeeftSubsidiecomponents()).containsOnly(subsidiecomponentBack);
        assertThat(subsidiecomponentBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setHeeftSubsidiecomponents(new HashSet<>());
        assertThat(kostenplaats.getHeeftSubsidiecomponents()).doesNotContain(subsidiecomponentBack);
        assertThat(subsidiecomponentBack.getHeeftKostenplaats()).isNull();
    }

    @Test
    void betreftBegrotingregelTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Begrotingregel begrotingregelBack = getBegrotingregelRandomSampleGenerator();

        kostenplaats.addBetreftBegrotingregel(begrotingregelBack);
        assertThat(kostenplaats.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeBetreftBegrotingregel(begrotingregelBack);
        assertThat(kostenplaats.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftKostenplaats()).isNull();

        kostenplaats.betreftBegrotingregels(new HashSet<>(Set.of(begrotingregelBack)));
        assertThat(kostenplaats.getBetreftBegrotingregels()).containsOnly(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setBetreftBegrotingregels(new HashSet<>());
        assertThat(kostenplaats.getBetreftBegrotingregels()).doesNotContain(begrotingregelBack);
        assertThat(begrotingregelBack.getBetreftKostenplaats()).isNull();
    }

    @Test
    void schrijftopFactuurTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Factuur factuurBack = getFactuurRandomSampleGenerator();

        kostenplaats.addSchrijftopFactuur(factuurBack);
        assertThat(kostenplaats.getSchrijftopFactuurs()).containsOnly(factuurBack);
        assertThat(factuurBack.getSchrijftopKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeSchrijftopFactuur(factuurBack);
        assertThat(kostenplaats.getSchrijftopFactuurs()).doesNotContain(factuurBack);
        assertThat(factuurBack.getSchrijftopKostenplaats()).isNull();

        kostenplaats.schrijftopFactuurs(new HashSet<>(Set.of(factuurBack)));
        assertThat(kostenplaats.getSchrijftopFactuurs()).containsOnly(factuurBack);
        assertThat(factuurBack.getSchrijftopKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setSchrijftopFactuurs(new HashSet<>());
        assertThat(kostenplaats.getSchrijftopFactuurs()).doesNotContain(factuurBack);
        assertThat(factuurBack.getSchrijftopKostenplaats()).isNull();
    }

    @Test
    void heeftbetrekkingopMutatieTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Mutatie mutatieBack = getMutatieRandomSampleGenerator();

        kostenplaats.addHeeftbetrekkingopMutatie(mutatieBack);
        assertThat(kostenplaats.getHeeftbetrekkingopMutaties()).containsOnly(mutatieBack);
        assertThat(mutatieBack.getHeeftbetrekkingopKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeHeeftbetrekkingopMutatie(mutatieBack);
        assertThat(kostenplaats.getHeeftbetrekkingopMutaties()).doesNotContain(mutatieBack);
        assertThat(mutatieBack.getHeeftbetrekkingopKostenplaats()).isNull();

        kostenplaats.heeftbetrekkingopMutaties(new HashSet<>(Set.of(mutatieBack)));
        assertThat(kostenplaats.getHeeftbetrekkingopMutaties()).containsOnly(mutatieBack);
        assertThat(mutatieBack.getHeeftbetrekkingopKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setHeeftbetrekkingopMutaties(new HashSet<>());
        assertThat(kostenplaats.getHeeftbetrekkingopMutaties()).doesNotContain(mutatieBack);
        assertThat(mutatieBack.getHeeftbetrekkingopKostenplaats()).isNull();
    }

    @Test
    void heeftProductTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        kostenplaats.addHeeftProduct(productBack);
        assertThat(kostenplaats.getHeeftProducts()).containsOnly(productBack);
        assertThat(productBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.removeHeeftProduct(productBack);
        assertThat(kostenplaats.getHeeftProducts()).doesNotContain(productBack);
        assertThat(productBack.getHeeftKostenplaats()).isNull();

        kostenplaats.heeftProducts(new HashSet<>(Set.of(productBack)));
        assertThat(kostenplaats.getHeeftProducts()).containsOnly(productBack);
        assertThat(productBack.getHeeftKostenplaats()).isEqualTo(kostenplaats);

        kostenplaats.setHeeftProducts(new HashSet<>());
        assertThat(kostenplaats.getHeeftProducts()).doesNotContain(productBack);
        assertThat(productBack.getHeeftKostenplaats()).isNull();
    }

    @Test
    void heeftHoofdrekeningTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        kostenplaats.addHeeftHoofdrekening(hoofdrekeningBack);
        assertThat(kostenplaats.getHeeftHoofdrekenings()).containsOnly(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getHeeftKostenplaats()).containsOnly(kostenplaats);

        kostenplaats.removeHeeftHoofdrekening(hoofdrekeningBack);
        assertThat(kostenplaats.getHeeftHoofdrekenings()).doesNotContain(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getHeeftKostenplaats()).doesNotContain(kostenplaats);

        kostenplaats.heeftHoofdrekenings(new HashSet<>(Set.of(hoofdrekeningBack)));
        assertThat(kostenplaats.getHeeftHoofdrekenings()).containsOnly(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getHeeftKostenplaats()).containsOnly(kostenplaats);

        kostenplaats.setHeeftHoofdrekenings(new HashSet<>());
        assertThat(kostenplaats.getHeeftHoofdrekenings()).doesNotContain(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getHeeftKostenplaats()).doesNotContain(kostenplaats);
    }

    @Test
    void heeftProjectTest() throws Exception {
        Kostenplaats kostenplaats = getKostenplaatsRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        kostenplaats.addHeeftProject(projectBack);
        assertThat(kostenplaats.getHeeftProjects()).containsOnly(projectBack);
        assertThat(projectBack.getHeeftKostenplaats()).containsOnly(kostenplaats);

        kostenplaats.removeHeeftProject(projectBack);
        assertThat(kostenplaats.getHeeftProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getHeeftKostenplaats()).doesNotContain(kostenplaats);

        kostenplaats.heeftProjects(new HashSet<>(Set.of(projectBack)));
        assertThat(kostenplaats.getHeeftProjects()).containsOnly(projectBack);
        assertThat(projectBack.getHeeftKostenplaats()).containsOnly(kostenplaats);

        kostenplaats.setHeeftProjects(new HashSet<>());
        assertThat(kostenplaats.getHeeftProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getHeeftKostenplaats()).doesNotContain(kostenplaats);
    }
}
