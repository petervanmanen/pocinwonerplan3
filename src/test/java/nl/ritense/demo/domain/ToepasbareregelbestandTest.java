package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ToepasbareregelbestandTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ToepasbareregelbestandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Toepasbareregelbestand.class);
        Toepasbareregelbestand toepasbareregelbestand1 = getToepasbareregelbestandSample1();
        Toepasbareregelbestand toepasbareregelbestand2 = new Toepasbareregelbestand();
        assertThat(toepasbareregelbestand1).isNotEqualTo(toepasbareregelbestand2);

        toepasbareregelbestand2.setId(toepasbareregelbestand1.getId());
        assertThat(toepasbareregelbestand1).isEqualTo(toepasbareregelbestand2);

        toepasbareregelbestand2 = getToepasbareregelbestandSample2();
        assertThat(toepasbareregelbestand1).isNotEqualTo(toepasbareregelbestand2);
    }
}
