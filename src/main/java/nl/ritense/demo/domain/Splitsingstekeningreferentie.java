package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Splitsingstekeningreferentie.
 */
@Entity
@Table(name = "splitsingstekeningreferentie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Splitsingstekeningreferentie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bronorganisatie")
    private String bronorganisatie;

    @Column(name = "datumcreatie")
    private LocalDate datumcreatie;

    @Column(name = "identificatietekening")
    private String identificatietekening;

    @Column(name = "titel")
    private String titel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Splitsingstekeningreferentie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBronorganisatie() {
        return this.bronorganisatie;
    }

    public Splitsingstekeningreferentie bronorganisatie(String bronorganisatie) {
        this.setBronorganisatie(bronorganisatie);
        return this;
    }

    public void setBronorganisatie(String bronorganisatie) {
        this.bronorganisatie = bronorganisatie;
    }

    public LocalDate getDatumcreatie() {
        return this.datumcreatie;
    }

    public Splitsingstekeningreferentie datumcreatie(LocalDate datumcreatie) {
        this.setDatumcreatie(datumcreatie);
        return this;
    }

    public void setDatumcreatie(LocalDate datumcreatie) {
        this.datumcreatie = datumcreatie;
    }

    public String getIdentificatietekening() {
        return this.identificatietekening;
    }

    public Splitsingstekeningreferentie identificatietekening(String identificatietekening) {
        this.setIdentificatietekening(identificatietekening);
        return this;
    }

    public void setIdentificatietekening(String identificatietekening) {
        this.identificatietekening = identificatietekening;
    }

    public String getTitel() {
        return this.titel;
    }

    public Splitsingstekeningreferentie titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Splitsingstekeningreferentie)) {
            return false;
        }
        return getId() != null && getId().equals(((Splitsingstekeningreferentie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Splitsingstekeningreferentie{" +
            "id=" + getId() +
            ", bronorganisatie='" + getBronorganisatie() + "'" +
            ", datumcreatie='" + getDatumcreatie() + "'" +
            ", identificatietekening='" + getIdentificatietekening() + "'" +
            ", titel='" + getTitel() + "'" +
            "}";
    }
}
