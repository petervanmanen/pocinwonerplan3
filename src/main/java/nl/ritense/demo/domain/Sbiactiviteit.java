package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Sbiactiviteit.
 */
@Entity
@Table(name = "sbiactiviteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sbiactiviteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeindesbiactiviteit")
    private LocalDate datumeindesbiactiviteit;

    @Column(name = "datumingangsbiactiviteit")
    private LocalDate datumingangsbiactiviteit;

    @Column(name = "hoofdniveau")
    private String hoofdniveau;

    @Size(max = 100)
    @Column(name = "hoofdniveauomschrijving", length = 100)
    private String hoofdniveauomschrijving;

    @Column(name = "naamactiviteit")
    private String naamactiviteit;

    @Size(max = 10)
    @Column(name = "sbicode", length = 10)
    private String sbicode;

    @Size(max = 10)
    @Column(name = "sbigroep", length = 10)
    private String sbigroep;

    @Size(max = 100)
    @Column(name = "sbigroepomschrijving", length = 100)
    private String sbigroepomschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sbiactiviteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeindesbiactiviteit() {
        return this.datumeindesbiactiviteit;
    }

    public Sbiactiviteit datumeindesbiactiviteit(LocalDate datumeindesbiactiviteit) {
        this.setDatumeindesbiactiviteit(datumeindesbiactiviteit);
        return this;
    }

    public void setDatumeindesbiactiviteit(LocalDate datumeindesbiactiviteit) {
        this.datumeindesbiactiviteit = datumeindesbiactiviteit;
    }

    public LocalDate getDatumingangsbiactiviteit() {
        return this.datumingangsbiactiviteit;
    }

    public Sbiactiviteit datumingangsbiactiviteit(LocalDate datumingangsbiactiviteit) {
        this.setDatumingangsbiactiviteit(datumingangsbiactiviteit);
        return this;
    }

    public void setDatumingangsbiactiviteit(LocalDate datumingangsbiactiviteit) {
        this.datumingangsbiactiviteit = datumingangsbiactiviteit;
    }

    public String getHoofdniveau() {
        return this.hoofdniveau;
    }

    public Sbiactiviteit hoofdniveau(String hoofdniveau) {
        this.setHoofdniveau(hoofdniveau);
        return this;
    }

    public void setHoofdniveau(String hoofdniveau) {
        this.hoofdniveau = hoofdniveau;
    }

    public String getHoofdniveauomschrijving() {
        return this.hoofdniveauomschrijving;
    }

    public Sbiactiviteit hoofdniveauomschrijving(String hoofdniveauomschrijving) {
        this.setHoofdniveauomschrijving(hoofdniveauomschrijving);
        return this;
    }

    public void setHoofdniveauomschrijving(String hoofdniveauomschrijving) {
        this.hoofdniveauomschrijving = hoofdniveauomschrijving;
    }

    public String getNaamactiviteit() {
        return this.naamactiviteit;
    }

    public Sbiactiviteit naamactiviteit(String naamactiviteit) {
        this.setNaamactiviteit(naamactiviteit);
        return this;
    }

    public void setNaamactiviteit(String naamactiviteit) {
        this.naamactiviteit = naamactiviteit;
    }

    public String getSbicode() {
        return this.sbicode;
    }

    public Sbiactiviteit sbicode(String sbicode) {
        this.setSbicode(sbicode);
        return this;
    }

    public void setSbicode(String sbicode) {
        this.sbicode = sbicode;
    }

    public String getSbigroep() {
        return this.sbigroep;
    }

    public Sbiactiviteit sbigroep(String sbigroep) {
        this.setSbigroep(sbigroep);
        return this;
    }

    public void setSbigroep(String sbigroep) {
        this.sbigroep = sbigroep;
    }

    public String getSbigroepomschrijving() {
        return this.sbigroepomschrijving;
    }

    public Sbiactiviteit sbigroepomschrijving(String sbigroepomschrijving) {
        this.setSbigroepomschrijving(sbigroepomschrijving);
        return this;
    }

    public void setSbigroepomschrijving(String sbigroepomschrijving) {
        this.sbigroepomschrijving = sbigroepomschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sbiactiviteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Sbiactiviteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sbiactiviteit{" +
            "id=" + getId() +
            ", datumeindesbiactiviteit='" + getDatumeindesbiactiviteit() + "'" +
            ", datumingangsbiactiviteit='" + getDatumingangsbiactiviteit() + "'" +
            ", hoofdniveau='" + getHoofdniveau() + "'" +
            ", hoofdniveauomschrijving='" + getHoofdniveauomschrijving() + "'" +
            ", naamactiviteit='" + getNaamactiviteit() + "'" +
            ", sbicode='" + getSbicode() + "'" +
            ", sbigroep='" + getSbigroep() + "'" +
            ", sbigroepomschrijving='" + getSbigroepomschrijving() + "'" +
            "}";
    }
}
