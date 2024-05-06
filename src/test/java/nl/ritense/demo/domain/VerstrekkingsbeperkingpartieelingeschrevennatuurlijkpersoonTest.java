package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.class);
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon1 =
            getVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonSample1();
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon2 =
            new Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon();
        assertThat(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon1).isNotEqualTo(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon2
        );

        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon2.setId(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon1.getId()
        );
        assertThat(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon1).isEqualTo(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon2
        );

        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon2 =
            getVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonSample2();
        assertThat(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon1).isNotEqualTo(
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon2
        );
    }
}
