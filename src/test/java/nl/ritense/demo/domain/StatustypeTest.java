package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StatusTestSamples.*;
import static nl.ritense.demo.domain.StatustypeTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatustypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statustype.class);
        Statustype statustype1 = getStatustypeSample1();
        Statustype statustype2 = new Statustype();
        assertThat(statustype1).isNotEqualTo(statustype2);

        statustype2.setId(statustype1.getId());
        assertThat(statustype1).isEqualTo(statustype2);

        statustype2 = getStatustypeSample2();
        assertThat(statustype1).isNotEqualTo(statustype2);
    }

    @Test
    void heeftZaaktypeTest() throws Exception {
        Statustype statustype = getStatustypeRandomSampleGenerator();
        Zaaktype zaaktypeBack = getZaaktypeRandomSampleGenerator();

        statustype.setHeeftZaaktype(zaaktypeBack);
        assertThat(statustype.getHeeftZaaktype()).isEqualTo(zaaktypeBack);

        statustype.heeftZaaktype(null);
        assertThat(statustype.getHeeftZaaktype()).isNull();
    }

    @Test
    void isvanStatusTest() throws Exception {
        Statustype statustype = getStatustypeRandomSampleGenerator();
        Status statusBack = getStatusRandomSampleGenerator();

        statustype.addIsvanStatus(statusBack);
        assertThat(statustype.getIsvanStatuses()).containsOnly(statusBack);
        assertThat(statusBack.getIsvanStatustype()).isEqualTo(statustype);

        statustype.removeIsvanStatus(statusBack);
        assertThat(statustype.getIsvanStatuses()).doesNotContain(statusBack);
        assertThat(statusBack.getIsvanStatustype()).isNull();

        statustype.isvanStatuses(new HashSet<>(Set.of(statusBack)));
        assertThat(statustype.getIsvanStatuses()).containsOnly(statusBack);
        assertThat(statusBack.getIsvanStatustype()).isEqualTo(statustype);

        statustype.setIsvanStatuses(new HashSet<>());
        assertThat(statustype.getIsvanStatuses()).doesNotContain(statusBack);
        assertThat(statusBack.getIsvanStatustype()).isNull();
    }
}
