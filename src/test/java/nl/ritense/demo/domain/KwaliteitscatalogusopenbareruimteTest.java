package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KwaliteitscatalogusopenbareruimteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KwaliteitscatalogusopenbareruimteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kwaliteitscatalogusopenbareruimte.class);
        Kwaliteitscatalogusopenbareruimte kwaliteitscatalogusopenbareruimte1 = getKwaliteitscatalogusopenbareruimteSample1();
        Kwaliteitscatalogusopenbareruimte kwaliteitscatalogusopenbareruimte2 = new Kwaliteitscatalogusopenbareruimte();
        assertThat(kwaliteitscatalogusopenbareruimte1).isNotEqualTo(kwaliteitscatalogusopenbareruimte2);

        kwaliteitscatalogusopenbareruimte2.setId(kwaliteitscatalogusopenbareruimte1.getId());
        assertThat(kwaliteitscatalogusopenbareruimte1).isEqualTo(kwaliteitscatalogusopenbareruimte2);

        kwaliteitscatalogusopenbareruimte2 = getKwaliteitscatalogusopenbareruimteSample2();
        assertThat(kwaliteitscatalogusopenbareruimte1).isNotEqualTo(kwaliteitscatalogusopenbareruimte2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Kwaliteitscatalogusopenbareruimte kwaliteitscatalogusopenbareruimte = new Kwaliteitscatalogusopenbareruimte();
        assertThat(kwaliteitscatalogusopenbareruimte.hashCode()).isZero();

        Kwaliteitscatalogusopenbareruimte kwaliteitscatalogusopenbareruimte1 = getKwaliteitscatalogusopenbareruimteSample1();
        kwaliteitscatalogusopenbareruimte.setId(kwaliteitscatalogusopenbareruimte1.getId());
        assertThat(kwaliteitscatalogusopenbareruimte).hasSameHashCodeAs(kwaliteitscatalogusopenbareruimte1);
    }
}
