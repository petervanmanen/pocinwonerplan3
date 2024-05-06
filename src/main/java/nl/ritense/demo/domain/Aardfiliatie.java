package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Aardfiliatie.
 */
@Entity
@Table(name = "aardfiliatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aardfiliatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codeaardfiliatie")
    private String codeaardfiliatie;

    @Column(name = "datumbegingeldigheidaardfiliatie")
    private LocalDate datumbegingeldigheidaardfiliatie;

    @Column(name = "datumeindegeldigheidaardfiliatie")
    private LocalDate datumeindegeldigheidaardfiliatie;

    @Column(name = "naamaardfiliatie")
    private String naamaardfiliatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aardfiliatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeaardfiliatie() {
        return this.codeaardfiliatie;
    }

    public Aardfiliatie codeaardfiliatie(String codeaardfiliatie) {
        this.setCodeaardfiliatie(codeaardfiliatie);
        return this;
    }

    public void setCodeaardfiliatie(String codeaardfiliatie) {
        this.codeaardfiliatie = codeaardfiliatie;
    }

    public LocalDate getDatumbegingeldigheidaardfiliatie() {
        return this.datumbegingeldigheidaardfiliatie;
    }

    public Aardfiliatie datumbegingeldigheidaardfiliatie(LocalDate datumbegingeldigheidaardfiliatie) {
        this.setDatumbegingeldigheidaardfiliatie(datumbegingeldigheidaardfiliatie);
        return this;
    }

    public void setDatumbegingeldigheidaardfiliatie(LocalDate datumbegingeldigheidaardfiliatie) {
        this.datumbegingeldigheidaardfiliatie = datumbegingeldigheidaardfiliatie;
    }

    public LocalDate getDatumeindegeldigheidaardfiliatie() {
        return this.datumeindegeldigheidaardfiliatie;
    }

    public Aardfiliatie datumeindegeldigheidaardfiliatie(LocalDate datumeindegeldigheidaardfiliatie) {
        this.setDatumeindegeldigheidaardfiliatie(datumeindegeldigheidaardfiliatie);
        return this;
    }

    public void setDatumeindegeldigheidaardfiliatie(LocalDate datumeindegeldigheidaardfiliatie) {
        this.datumeindegeldigheidaardfiliatie = datumeindegeldigheidaardfiliatie;
    }

    public String getNaamaardfiliatie() {
        return this.naamaardfiliatie;
    }

    public Aardfiliatie naamaardfiliatie(String naamaardfiliatie) {
        this.setNaamaardfiliatie(naamaardfiliatie);
        return this;
    }

    public void setNaamaardfiliatie(String naamaardfiliatie) {
        this.naamaardfiliatie = naamaardfiliatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aardfiliatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Aardfiliatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aardfiliatie{" +
            "id=" + getId() +
            ", codeaardfiliatie='" + getCodeaardfiliatie() + "'" +
            ", datumbegingeldigheidaardfiliatie='" + getDatumbegingeldigheidaardfiliatie() + "'" +
            ", datumeindegeldigheidaardfiliatie='" + getDatumeindegeldigheidaardfiliatie() + "'" +
            ", naamaardfiliatie='" + getNaamaardfiliatie() + "'" +
            "}";
    }
}
