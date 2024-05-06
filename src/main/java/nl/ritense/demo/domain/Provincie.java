package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Provincie.
 */
@Entity
@Table(name = "provincie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Provincie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeindeprovincie")
    private LocalDate datumeindeprovincie;

    @Column(name = "datumingangprovincie")
    private LocalDate datumingangprovincie;

    @Column(name = "hoofdstad")
    private String hoofdstad;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    @Column(name = "oppervlakteland")
    private String oppervlakteland;

    @Column(name = "provinciecode")
    private String provinciecode;

    @Column(name = "provincienaam")
    private String provincienaam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Provincie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeindeprovincie() {
        return this.datumeindeprovincie;
    }

    public Provincie datumeindeprovincie(LocalDate datumeindeprovincie) {
        this.setDatumeindeprovincie(datumeindeprovincie);
        return this;
    }

    public void setDatumeindeprovincie(LocalDate datumeindeprovincie) {
        this.datumeindeprovincie = datumeindeprovincie;
    }

    public LocalDate getDatumingangprovincie() {
        return this.datumingangprovincie;
    }

    public Provincie datumingangprovincie(LocalDate datumingangprovincie) {
        this.setDatumingangprovincie(datumingangprovincie);
        return this;
    }

    public void setDatumingangprovincie(LocalDate datumingangprovincie) {
        this.datumingangprovincie = datumingangprovincie;
    }

    public String getHoofdstad() {
        return this.hoofdstad;
    }

    public Provincie hoofdstad(String hoofdstad) {
        this.setHoofdstad(hoofdstad);
        return this;
    }

    public void setHoofdstad(String hoofdstad) {
        this.hoofdstad = hoofdstad;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Provincie oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    public String getOppervlakteland() {
        return this.oppervlakteland;
    }

    public Provincie oppervlakteland(String oppervlakteland) {
        this.setOppervlakteland(oppervlakteland);
        return this;
    }

    public void setOppervlakteland(String oppervlakteland) {
        this.oppervlakteland = oppervlakteland;
    }

    public String getProvinciecode() {
        return this.provinciecode;
    }

    public Provincie provinciecode(String provinciecode) {
        this.setProvinciecode(provinciecode);
        return this;
    }

    public void setProvinciecode(String provinciecode) {
        this.provinciecode = provinciecode;
    }

    public String getProvincienaam() {
        return this.provincienaam;
    }

    public Provincie provincienaam(String provincienaam) {
        this.setProvincienaam(provincienaam);
        return this;
    }

    public void setProvincienaam(String provincienaam) {
        this.provincienaam = provincienaam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Provincie)) {
            return false;
        }
        return getId() != null && getId().equals(((Provincie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Provincie{" +
            "id=" + getId() +
            ", datumeindeprovincie='" + getDatumeindeprovincie() + "'" +
            ", datumingangprovincie='" + getDatumingangprovincie() + "'" +
            ", hoofdstad='" + getHoofdstad() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            ", oppervlakteland='" + getOppervlakteland() + "'" +
            ", provinciecode='" + getProvinciecode() + "'" +
            ", provincienaam='" + getProvincienaam() + "'" +
            "}";
    }
}
