package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Cultuurcodebebouwd.
 */
@Entity
@Table(name = "cultuurcodebebouwd")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cultuurcodebebouwd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cultuurcodebebouwd")
    private String cultuurcodebebouwd;

    @Column(name = "datumbegingeldigheidcultuurcodebebouwd")
    private LocalDate datumbegingeldigheidcultuurcodebebouwd;

    @Column(name = "datumeindegeldigheidcultuurcodebebouwd")
    private LocalDate datumeindegeldigheidcultuurcodebebouwd;

    @Column(name = "naamcultuurcodebebouwd")
    private String naamcultuurcodebebouwd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cultuurcodebebouwd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCultuurcodebebouwd() {
        return this.cultuurcodebebouwd;
    }

    public Cultuurcodebebouwd cultuurcodebebouwd(String cultuurcodebebouwd) {
        this.setCultuurcodebebouwd(cultuurcodebebouwd);
        return this;
    }

    public void setCultuurcodebebouwd(String cultuurcodebebouwd) {
        this.cultuurcodebebouwd = cultuurcodebebouwd;
    }

    public LocalDate getDatumbegingeldigheidcultuurcodebebouwd() {
        return this.datumbegingeldigheidcultuurcodebebouwd;
    }

    public Cultuurcodebebouwd datumbegingeldigheidcultuurcodebebouwd(LocalDate datumbegingeldigheidcultuurcodebebouwd) {
        this.setDatumbegingeldigheidcultuurcodebebouwd(datumbegingeldigheidcultuurcodebebouwd);
        return this;
    }

    public void setDatumbegingeldigheidcultuurcodebebouwd(LocalDate datumbegingeldigheidcultuurcodebebouwd) {
        this.datumbegingeldigheidcultuurcodebebouwd = datumbegingeldigheidcultuurcodebebouwd;
    }

    public LocalDate getDatumeindegeldigheidcultuurcodebebouwd() {
        return this.datumeindegeldigheidcultuurcodebebouwd;
    }

    public Cultuurcodebebouwd datumeindegeldigheidcultuurcodebebouwd(LocalDate datumeindegeldigheidcultuurcodebebouwd) {
        this.setDatumeindegeldigheidcultuurcodebebouwd(datumeindegeldigheidcultuurcodebebouwd);
        return this;
    }

    public void setDatumeindegeldigheidcultuurcodebebouwd(LocalDate datumeindegeldigheidcultuurcodebebouwd) {
        this.datumeindegeldigheidcultuurcodebebouwd = datumeindegeldigheidcultuurcodebebouwd;
    }

    public String getNaamcultuurcodebebouwd() {
        return this.naamcultuurcodebebouwd;
    }

    public Cultuurcodebebouwd naamcultuurcodebebouwd(String naamcultuurcodebebouwd) {
        this.setNaamcultuurcodebebouwd(naamcultuurcodebebouwd);
        return this;
    }

    public void setNaamcultuurcodebebouwd(String naamcultuurcodebebouwd) {
        this.naamcultuurcodebebouwd = naamcultuurcodebebouwd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cultuurcodebebouwd)) {
            return false;
        }
        return getId() != null && getId().equals(((Cultuurcodebebouwd) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cultuurcodebebouwd{" +
            "id=" + getId() +
            ", cultuurcodebebouwd='" + getCultuurcodebebouwd() + "'" +
            ", datumbegingeldigheidcultuurcodebebouwd='" + getDatumbegingeldigheidcultuurcodebebouwd() + "'" +
            ", datumeindegeldigheidcultuurcodebebouwd='" + getDatumeindegeldigheidcultuurcodebebouwd() + "'" +
            ", naamcultuurcodebebouwd='" + getNaamcultuurcodebebouwd() + "'" +
            "}";
    }
}
