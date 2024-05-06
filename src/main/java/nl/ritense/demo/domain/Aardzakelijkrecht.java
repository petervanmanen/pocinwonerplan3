package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Aardzakelijkrecht.
 */
@Entity
@Table(name = "aardzakelijkrecht")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aardzakelijkrecht implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codeaardzakelijkrecht")
    private String codeaardzakelijkrecht;

    @Column(name = "datumbegingeldigheidaardzakelijkrecht")
    private LocalDate datumbegingeldigheidaardzakelijkrecht;

    @Column(name = "datumeindegeldigheidaardzakelijkrecht")
    private LocalDate datumeindegeldigheidaardzakelijkrecht;

    @Column(name = "naamaardzakelijkrecht")
    private String naamaardzakelijkrecht;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aardzakelijkrecht id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeaardzakelijkrecht() {
        return this.codeaardzakelijkrecht;
    }

    public Aardzakelijkrecht codeaardzakelijkrecht(String codeaardzakelijkrecht) {
        this.setCodeaardzakelijkrecht(codeaardzakelijkrecht);
        return this;
    }

    public void setCodeaardzakelijkrecht(String codeaardzakelijkrecht) {
        this.codeaardzakelijkrecht = codeaardzakelijkrecht;
    }

    public LocalDate getDatumbegingeldigheidaardzakelijkrecht() {
        return this.datumbegingeldigheidaardzakelijkrecht;
    }

    public Aardzakelijkrecht datumbegingeldigheidaardzakelijkrecht(LocalDate datumbegingeldigheidaardzakelijkrecht) {
        this.setDatumbegingeldigheidaardzakelijkrecht(datumbegingeldigheidaardzakelijkrecht);
        return this;
    }

    public void setDatumbegingeldigheidaardzakelijkrecht(LocalDate datumbegingeldigheidaardzakelijkrecht) {
        this.datumbegingeldigheidaardzakelijkrecht = datumbegingeldigheidaardzakelijkrecht;
    }

    public LocalDate getDatumeindegeldigheidaardzakelijkrecht() {
        return this.datumeindegeldigheidaardzakelijkrecht;
    }

    public Aardzakelijkrecht datumeindegeldigheidaardzakelijkrecht(LocalDate datumeindegeldigheidaardzakelijkrecht) {
        this.setDatumeindegeldigheidaardzakelijkrecht(datumeindegeldigheidaardzakelijkrecht);
        return this;
    }

    public void setDatumeindegeldigheidaardzakelijkrecht(LocalDate datumeindegeldigheidaardzakelijkrecht) {
        this.datumeindegeldigheidaardzakelijkrecht = datumeindegeldigheidaardzakelijkrecht;
    }

    public String getNaamaardzakelijkrecht() {
        return this.naamaardzakelijkrecht;
    }

    public Aardzakelijkrecht naamaardzakelijkrecht(String naamaardzakelijkrecht) {
        this.setNaamaardzakelijkrecht(naamaardzakelijkrecht);
        return this;
    }

    public void setNaamaardzakelijkrecht(String naamaardzakelijkrecht) {
        this.naamaardzakelijkrecht = naamaardzakelijkrecht;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aardzakelijkrecht)) {
            return false;
        }
        return getId() != null && getId().equals(((Aardzakelijkrecht) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aardzakelijkrecht{" +
            "id=" + getId() +
            ", codeaardzakelijkrecht='" + getCodeaardzakelijkrecht() + "'" +
            ", datumbegingeldigheidaardzakelijkrecht='" + getDatumbegingeldigheidaardzakelijkrecht() + "'" +
            ", datumeindegeldigheidaardzakelijkrecht='" + getDatumeindegeldigheidaardzakelijkrecht() + "'" +
            ", naamaardzakelijkrecht='" + getNaamaardzakelijkrecht() + "'" +
            "}";
    }
}
