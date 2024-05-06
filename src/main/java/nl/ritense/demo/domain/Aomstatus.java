package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Aomstatus.
 */
@Entity
@Table(name = "aomstatus")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aomstatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbeginstatus")
    private LocalDate datumbeginstatus;

    @Column(name = "datumeindestatus")
    private LocalDate datumeindestatus;

    @Column(name = "status")
    private String status;

    @Column(name = "statuscode")
    private String statuscode;

    @Column(name = "statusvolgorde")
    private String statusvolgorde;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aomstatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbeginstatus() {
        return this.datumbeginstatus;
    }

    public Aomstatus datumbeginstatus(LocalDate datumbeginstatus) {
        this.setDatumbeginstatus(datumbeginstatus);
        return this;
    }

    public void setDatumbeginstatus(LocalDate datumbeginstatus) {
        this.datumbeginstatus = datumbeginstatus;
    }

    public LocalDate getDatumeindestatus() {
        return this.datumeindestatus;
    }

    public Aomstatus datumeindestatus(LocalDate datumeindestatus) {
        this.setDatumeindestatus(datumeindestatus);
        return this;
    }

    public void setDatumeindestatus(LocalDate datumeindestatus) {
        this.datumeindestatus = datumeindestatus;
    }

    public String getStatus() {
        return this.status;
    }

    public Aomstatus status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatuscode() {
        return this.statuscode;
    }

    public Aomstatus statuscode(String statuscode) {
        this.setStatuscode(statuscode);
        return this;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatusvolgorde() {
        return this.statusvolgorde;
    }

    public Aomstatus statusvolgorde(String statusvolgorde) {
        this.setStatusvolgorde(statusvolgorde);
        return this;
    }

    public void setStatusvolgorde(String statusvolgorde) {
        this.statusvolgorde = statusvolgorde;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aomstatus)) {
            return false;
        }
        return getId() != null && getId().equals(((Aomstatus) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aomstatus{" +
            "id=" + getId() +
            ", datumbeginstatus='" + getDatumbeginstatus() + "'" +
            ", datumeindestatus='" + getDatumeindestatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", statuscode='" + getStatuscode() + "'" +
            ", statusvolgorde='" + getStatusvolgorde() + "'" +
            "}";
    }
}
