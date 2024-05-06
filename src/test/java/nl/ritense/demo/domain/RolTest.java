package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RolTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rol.class);
        Rol rol1 = getRolSample1();
        Rol rol2 = new Rol();
        assertThat(rol1).isNotEqualTo(rol2);

        rol2.setId(rol1.getId());
        assertThat(rol1).isEqualTo(rol2);

        rol2 = getRolSample2();
        assertThat(rol1).isNotEqualTo(rol2);
    }

    @Test
    void heeftWerknemerTest() throws Exception {
        Rol rol = getRolRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        rol.addHeeftWerknemer(werknemerBack);
        assertThat(rol.getHeeftWerknemers()).containsOnly(werknemerBack);
        assertThat(werknemerBack.getHeeftRols()).containsOnly(rol);

        rol.removeHeeftWerknemer(werknemerBack);
        assertThat(rol.getHeeftWerknemers()).doesNotContain(werknemerBack);
        assertThat(werknemerBack.getHeeftRols()).doesNotContain(rol);

        rol.heeftWerknemers(new HashSet<>(Set.of(werknemerBack)));
        assertThat(rol.getHeeftWerknemers()).containsOnly(werknemerBack);
        assertThat(werknemerBack.getHeeftRols()).containsOnly(rol);

        rol.setHeeftWerknemers(new HashSet<>());
        assertThat(rol.getHeeftWerknemers()).doesNotContain(werknemerBack);
        assertThat(werknemerBack.getHeeftRols()).doesNotContain(rol);
    }
}
