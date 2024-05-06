package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Soortwozobject.
 */
@Entity
@Table(name = "soortwozobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Soortwozobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidsoortobjectcode")
    private LocalDate datumbegingeldigheidsoortobjectcode;

    @Column(name = "datumeindegeldigheidsoortobjectcode")
    private LocalDate datumeindegeldigheidsoortobjectcode;

    @Column(name = "naamsoortobjectcode")
    private String naamsoortobjectcode;

    @Column(name = "opmerkingensoortobjectcode")
    private String opmerkingensoortobjectcode;

    @Column(name = "soortobjectcode")
    private String soortobjectcode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Soortwozobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidsoortobjectcode() {
        return this.datumbegingeldigheidsoortobjectcode;
    }

    public Soortwozobject datumbegingeldigheidsoortobjectcode(LocalDate datumbegingeldigheidsoortobjectcode) {
        this.setDatumbegingeldigheidsoortobjectcode(datumbegingeldigheidsoortobjectcode);
        return this;
    }

    public void setDatumbegingeldigheidsoortobjectcode(LocalDate datumbegingeldigheidsoortobjectcode) {
        this.datumbegingeldigheidsoortobjectcode = datumbegingeldigheidsoortobjectcode;
    }

    public LocalDate getDatumeindegeldigheidsoortobjectcode() {
        return this.datumeindegeldigheidsoortobjectcode;
    }

    public Soortwozobject datumeindegeldigheidsoortobjectcode(LocalDate datumeindegeldigheidsoortobjectcode) {
        this.setDatumeindegeldigheidsoortobjectcode(datumeindegeldigheidsoortobjectcode);
        return this;
    }

    public void setDatumeindegeldigheidsoortobjectcode(LocalDate datumeindegeldigheidsoortobjectcode) {
        this.datumeindegeldigheidsoortobjectcode = datumeindegeldigheidsoortobjectcode;
    }

    public String getNaamsoortobjectcode() {
        return this.naamsoortobjectcode;
    }

    public Soortwozobject naamsoortobjectcode(String naamsoortobjectcode) {
        this.setNaamsoortobjectcode(naamsoortobjectcode);
        return this;
    }

    public void setNaamsoortobjectcode(String naamsoortobjectcode) {
        this.naamsoortobjectcode = naamsoortobjectcode;
    }

    public String getOpmerkingensoortobjectcode() {
        return this.opmerkingensoortobjectcode;
    }

    public Soortwozobject opmerkingensoortobjectcode(String opmerkingensoortobjectcode) {
        this.setOpmerkingensoortobjectcode(opmerkingensoortobjectcode);
        return this;
    }

    public void setOpmerkingensoortobjectcode(String opmerkingensoortobjectcode) {
        this.opmerkingensoortobjectcode = opmerkingensoortobjectcode;
    }

    public String getSoortobjectcode() {
        return this.soortobjectcode;
    }

    public Soortwozobject soortobjectcode(String soortobjectcode) {
        this.setSoortobjectcode(soortobjectcode);
        return this;
    }

    public void setSoortobjectcode(String soortobjectcode) {
        this.soortobjectcode = soortobjectcode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Soortwozobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Soortwozobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Soortwozobject{" +
            "id=" + getId() +
            ", datumbegingeldigheidsoortobjectcode='" + getDatumbegingeldigheidsoortobjectcode() + "'" +
            ", datumeindegeldigheidsoortobjectcode='" + getDatumeindegeldigheidsoortobjectcode() + "'" +
            ", naamsoortobjectcode='" + getNaamsoortobjectcode() + "'" +
            ", opmerkingensoortobjectcode='" + getOpmerkingensoortobjectcode() + "'" +
            ", soortobjectcode='" + getSoortobjectcode() + "'" +
            "}";
    }
}
