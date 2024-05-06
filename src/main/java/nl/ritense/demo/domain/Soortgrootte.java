package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Soortgrootte.
 */
@Entity
@Table(name = "soortgrootte")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Soortgrootte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codesoortgrootte")
    private String codesoortgrootte;

    @Column(name = "datumbegingeldigheidsoortgrootte")
    private LocalDate datumbegingeldigheidsoortgrootte;

    @Column(name = "datumeindegeldigheidsoortgrootte")
    private LocalDate datumeindegeldigheidsoortgrootte;

    @Column(name = "naamsoortgrootte")
    private String naamsoortgrootte;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Soortgrootte id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodesoortgrootte() {
        return this.codesoortgrootte;
    }

    public Soortgrootte codesoortgrootte(String codesoortgrootte) {
        this.setCodesoortgrootte(codesoortgrootte);
        return this;
    }

    public void setCodesoortgrootte(String codesoortgrootte) {
        this.codesoortgrootte = codesoortgrootte;
    }

    public LocalDate getDatumbegingeldigheidsoortgrootte() {
        return this.datumbegingeldigheidsoortgrootte;
    }

    public Soortgrootte datumbegingeldigheidsoortgrootte(LocalDate datumbegingeldigheidsoortgrootte) {
        this.setDatumbegingeldigheidsoortgrootte(datumbegingeldigheidsoortgrootte);
        return this;
    }

    public void setDatumbegingeldigheidsoortgrootte(LocalDate datumbegingeldigheidsoortgrootte) {
        this.datumbegingeldigheidsoortgrootte = datumbegingeldigheidsoortgrootte;
    }

    public LocalDate getDatumeindegeldigheidsoortgrootte() {
        return this.datumeindegeldigheidsoortgrootte;
    }

    public Soortgrootte datumeindegeldigheidsoortgrootte(LocalDate datumeindegeldigheidsoortgrootte) {
        this.setDatumeindegeldigheidsoortgrootte(datumeindegeldigheidsoortgrootte);
        return this;
    }

    public void setDatumeindegeldigheidsoortgrootte(LocalDate datumeindegeldigheidsoortgrootte) {
        this.datumeindegeldigheidsoortgrootte = datumeindegeldigheidsoortgrootte;
    }

    public String getNaamsoortgrootte() {
        return this.naamsoortgrootte;
    }

    public Soortgrootte naamsoortgrootte(String naamsoortgrootte) {
        this.setNaamsoortgrootte(naamsoortgrootte);
        return this;
    }

    public void setNaamsoortgrootte(String naamsoortgrootte) {
        this.naamsoortgrootte = naamsoortgrootte;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Soortgrootte)) {
            return false;
        }
        return getId() != null && getId().equals(((Soortgrootte) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Soortgrootte{" +
            "id=" + getId() +
            ", codesoortgrootte='" + getCodesoortgrootte() + "'" +
            ", datumbegingeldigheidsoortgrootte='" + getDatumbegingeldigheidsoortgrootte() + "'" +
            ", datumeindegeldigheidsoortgrootte='" + getDatumeindegeldigheidsoortgrootte() + "'" +
            ", naamsoortgrootte='" + getNaamsoortgrootte() + "'" +
            "}";
    }
}
