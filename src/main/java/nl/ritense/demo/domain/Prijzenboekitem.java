package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Prijzenboekitem.
 */
@Entity
@Table(name = "prijzenboekitem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prijzenboekitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "prijs", precision = 21, scale = 2)
    private BigDecimal prijs;

    @Column(name = "verrichting")
    private String verrichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prijzenboekitem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Prijzenboekitem datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Prijzenboekitem datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public BigDecimal getPrijs() {
        return this.prijs;
    }

    public Prijzenboekitem prijs(BigDecimal prijs) {
        this.setPrijs(prijs);
        return this;
    }

    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    public String getVerrichting() {
        return this.verrichting;
    }

    public Prijzenboekitem verrichting(String verrichting) {
        this.setVerrichting(verrichting);
        return this;
    }

    public void setVerrichting(String verrichting) {
        this.verrichting = verrichting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prijzenboekitem)) {
            return false;
        }
        return getId() != null && getId().equals(((Prijzenboekitem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prijzenboekitem{" +
            "id=" + getId() +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", prijs=" + getPrijs() +
            ", verrichting='" + getVerrichting() + "'" +
            "}";
    }
}
