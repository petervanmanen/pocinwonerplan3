package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BelanghebbendeTestSamples.*;
import static nl.ritense.demo.domain.MuseumobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BelanghebbendeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Belanghebbende.class);
        Belanghebbende belanghebbende1 = getBelanghebbendeSample1();
        Belanghebbende belanghebbende2 = new Belanghebbende();
        assertThat(belanghebbende1).isNotEqualTo(belanghebbende2);

        belanghebbende2.setId(belanghebbende1.getId());
        assertThat(belanghebbende1).isEqualTo(belanghebbende2);

        belanghebbende2 = getBelanghebbendeSample2();
        assertThat(belanghebbende1).isNotEqualTo(belanghebbende2);
    }

    @Test
    void heeftMuseumobjectTest() throws Exception {
        Belanghebbende belanghebbende = getBelanghebbendeRandomSampleGenerator();
        Museumobject museumobjectBack = getMuseumobjectRandomSampleGenerator();

        belanghebbende.addHeeftMuseumobject(museumobjectBack);
        assertThat(belanghebbende.getHeeftMuseumobjects()).containsOnly(museumobjectBack);
        assertThat(museumobjectBack.getHeeftBelanghebbendes()).containsOnly(belanghebbende);

        belanghebbende.removeHeeftMuseumobject(museumobjectBack);
        assertThat(belanghebbende.getHeeftMuseumobjects()).doesNotContain(museumobjectBack);
        assertThat(museumobjectBack.getHeeftBelanghebbendes()).doesNotContain(belanghebbende);

        belanghebbende.heeftMuseumobjects(new HashSet<>(Set.of(museumobjectBack)));
        assertThat(belanghebbende.getHeeftMuseumobjects()).containsOnly(museumobjectBack);
        assertThat(museumobjectBack.getHeeftBelanghebbendes()).containsOnly(belanghebbende);

        belanghebbende.setHeeftMuseumobjects(new HashSet<>());
        assertThat(belanghebbende.getHeeftMuseumobjects()).doesNotContain(museumobjectBack);
        assertThat(museumobjectBack.getHeeftBelanghebbendes()).doesNotContain(belanghebbende);
    }
}
