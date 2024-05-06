package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Uitkeringsrun.
 */
@Entity
@Table(name = "uitkeringsrun")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Uitkeringsrun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumrun")
    private LocalDate datumrun;

    @Size(max = 20)
    @Column(name = "frequentie", length = 20)
    private String frequentie;

    @Size(max = 20)
    @Column(name = "perioderun", length = 20)
    private String perioderun;

    @Column(name = "soortrun")
    private String soortrun;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Uitkeringsrun id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumrun() {
        return this.datumrun;
    }

    public Uitkeringsrun datumrun(LocalDate datumrun) {
        this.setDatumrun(datumrun);
        return this;
    }

    public void setDatumrun(LocalDate datumrun) {
        this.datumrun = datumrun;
    }

    public String getFrequentie() {
        return this.frequentie;
    }

    public Uitkeringsrun frequentie(String frequentie) {
        this.setFrequentie(frequentie);
        return this;
    }

    public void setFrequentie(String frequentie) {
        this.frequentie = frequentie;
    }

    public String getPerioderun() {
        return this.perioderun;
    }

    public Uitkeringsrun perioderun(String perioderun) {
        this.setPerioderun(perioderun);
        return this;
    }

    public void setPerioderun(String perioderun) {
        this.perioderun = perioderun;
    }

    public String getSoortrun() {
        return this.soortrun;
    }

    public Uitkeringsrun soortrun(String soortrun) {
        this.setSoortrun(soortrun);
        return this;
    }

    public void setSoortrun(String soortrun) {
        this.soortrun = soortrun;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uitkeringsrun)) {
            return false;
        }
        return getId() != null && getId().equals(((Uitkeringsrun) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uitkeringsrun{" +
            "id=" + getId() +
            ", datumrun='" + getDatumrun() + "'" +
            ", frequentie='" + getFrequentie() + "'" +
            ", perioderun='" + getPerioderun() + "'" +
            ", soortrun='" + getSoortrun() + "'" +
            "}";
    }
}
