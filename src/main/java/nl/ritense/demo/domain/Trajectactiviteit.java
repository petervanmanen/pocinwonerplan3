package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Trajectactiviteit.
 */
@Entity
@Table(name = "trajectactiviteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Trajectactiviteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "contract")
    private String contract;

    @Size(max = 20)
    @Column(name = "crediteur", length = 20)
    private String crediteur;

    @Column(name = "datumbegin")
    private LocalDate datumbegin;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstatus")
    private LocalDate datumstatus;

    @Column(name = "kinderopvang")
    private String kinderopvang;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @Size(max = 20)
    @Column(name = "succesvol", length = 20)
    private String succesvol;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trajectactiviteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContract() {
        return this.contract;
    }

    public Trajectactiviteit contract(String contract) {
        this.setContract(contract);
        return this;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getCrediteur() {
        return this.crediteur;
    }

    public Trajectactiviteit crediteur(String crediteur) {
        this.setCrediteur(crediteur);
        return this;
    }

    public void setCrediteur(String crediteur) {
        this.crediteur = crediteur;
    }

    public LocalDate getDatumbegin() {
        return this.datumbegin;
    }

    public Trajectactiviteit datumbegin(LocalDate datumbegin) {
        this.setDatumbegin(datumbegin);
        return this;
    }

    public void setDatumbegin(LocalDate datumbegin) {
        this.datumbegin = datumbegin;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Trajectactiviteit datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstatus() {
        return this.datumstatus;
    }

    public Trajectactiviteit datumstatus(LocalDate datumstatus) {
        this.setDatumstatus(datumstatus);
        return this;
    }

    public void setDatumstatus(LocalDate datumstatus) {
        this.datumstatus = datumstatus;
    }

    public String getKinderopvang() {
        return this.kinderopvang;
    }

    public Trajectactiviteit kinderopvang(String kinderopvang) {
        this.setKinderopvang(kinderopvang);
        return this;
    }

    public void setKinderopvang(String kinderopvang) {
        this.kinderopvang = kinderopvang;
    }

    public String getStatus() {
        return this.status;
    }

    public Trajectactiviteit status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccesvol() {
        return this.succesvol;
    }

    public Trajectactiviteit succesvol(String succesvol) {
        this.setSuccesvol(succesvol);
        return this;
    }

    public void setSuccesvol(String succesvol) {
        this.succesvol = succesvol;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trajectactiviteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Trajectactiviteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trajectactiviteit{" +
            "id=" + getId() +
            ", contract='" + getContract() + "'" +
            ", crediteur='" + getCrediteur() + "'" +
            ", datumbegin='" + getDatumbegin() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstatus='" + getDatumstatus() + "'" +
            ", kinderopvang='" + getKinderopvang() + "'" +
            ", status='" + getStatus() + "'" +
            ", succesvol='" + getSuccesvol() + "'" +
            "}";
    }
}
