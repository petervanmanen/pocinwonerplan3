package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Formulierverlenginginhuur.
 */
@Entity
@Table(name = "formulierverlenginginhuur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Formulierverlenginginhuur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeindenieuw")
    private LocalDate datumeindenieuw;

    @Column(name = "indicatieredeninhuurgewijzigd")
    private String indicatieredeninhuurgewijzigd;

    @Column(name = "indicatieverhogeninkooporder")
    private String indicatieverhogeninkooporder;

    @Column(name = "toelichting")
    private String toelichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formulierverlenginginhuur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeindenieuw() {
        return this.datumeindenieuw;
    }

    public Formulierverlenginginhuur datumeindenieuw(LocalDate datumeindenieuw) {
        this.setDatumeindenieuw(datumeindenieuw);
        return this;
    }

    public void setDatumeindenieuw(LocalDate datumeindenieuw) {
        this.datumeindenieuw = datumeindenieuw;
    }

    public String getIndicatieredeninhuurgewijzigd() {
        return this.indicatieredeninhuurgewijzigd;
    }

    public Formulierverlenginginhuur indicatieredeninhuurgewijzigd(String indicatieredeninhuurgewijzigd) {
        this.setIndicatieredeninhuurgewijzigd(indicatieredeninhuurgewijzigd);
        return this;
    }

    public void setIndicatieredeninhuurgewijzigd(String indicatieredeninhuurgewijzigd) {
        this.indicatieredeninhuurgewijzigd = indicatieredeninhuurgewijzigd;
    }

    public String getIndicatieverhogeninkooporder() {
        return this.indicatieverhogeninkooporder;
    }

    public Formulierverlenginginhuur indicatieverhogeninkooporder(String indicatieverhogeninkooporder) {
        this.setIndicatieverhogeninkooporder(indicatieverhogeninkooporder);
        return this;
    }

    public void setIndicatieverhogeninkooporder(String indicatieverhogeninkooporder) {
        this.indicatieverhogeninkooporder = indicatieverhogeninkooporder;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Formulierverlenginginhuur toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formulierverlenginginhuur)) {
            return false;
        }
        return getId() != null && getId().equals(((Formulierverlenginginhuur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formulierverlenginginhuur{" +
            "id=" + getId() +
            ", datumeindenieuw='" + getDatumeindenieuw() + "'" +
            ", indicatieredeninhuurgewijzigd='" + getIndicatieredeninhuurgewijzigd() + "'" +
            ", indicatieverhogeninkooporder='" + getIndicatieverhogeninkooporder() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
