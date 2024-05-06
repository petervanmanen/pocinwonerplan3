package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjectrelatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjectrelatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobjectrelatie.class);
        Eobjectrelatie eobjectrelatie1 = getEobjectrelatieSample1();
        Eobjectrelatie eobjectrelatie2 = new Eobjectrelatie();
        assertThat(eobjectrelatie1).isNotEqualTo(eobjectrelatie2);

        eobjectrelatie2.setId(eobjectrelatie1.getId());
        assertThat(eobjectrelatie1).isEqualTo(eobjectrelatie2);

        eobjectrelatie2 = getEobjectrelatieSample2();
        assertThat(eobjectrelatie1).isNotEqualTo(eobjectrelatie2);
    }
}
