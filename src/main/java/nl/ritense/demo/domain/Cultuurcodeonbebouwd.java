package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Cultuurcodeonbebouwd.
 */
@Entity
@Table(name = "cultuurcodeonbebouwd")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cultuurcodeonbebouwd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cultuurcodeonbebouwd")
    private String cultuurcodeonbebouwd;

    @Column(name = "datumbegingeldigheidcultuurcodeonbebouwd")
    private LocalDate datumbegingeldigheidcultuurcodeonbebouwd;

    @Column(name = "datumeindegeldigheidcultuurcodeonbebouwd")
    private LocalDate datumeindegeldigheidcultuurcodeonbebouwd;

    @Column(name = "naamcultuurcodeonbebouwd")
    private String naamcultuurcodeonbebouwd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cultuurcodeonbebouwd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCultuurcodeonbebouwd() {
        return this.cultuurcodeonbebouwd;
    }

    public Cultuurcodeonbebouwd cultuurcodeonbebouwd(String cultuurcodeonbebouwd) {
        this.setCultuurcodeonbebouwd(cultuurcodeonbebouwd);
        return this;
    }

    public void setCultuurcodeonbebouwd(String cultuurcodeonbebouwd) {
        this.cultuurcodeonbebouwd = cultuurcodeonbebouwd;
    }

    public LocalDate getDatumbegingeldigheidcultuurcodeonbebouwd() {
        return this.datumbegingeldigheidcultuurcodeonbebouwd;
    }

    public Cultuurcodeonbebouwd datumbegingeldigheidcultuurcodeonbebouwd(LocalDate datumbegingeldigheidcultuurcodeonbebouwd) {
        this.setDatumbegingeldigheidcultuurcodeonbebouwd(datumbegingeldigheidcultuurcodeonbebouwd);
        return this;
    }

    public void setDatumbegingeldigheidcultuurcodeonbebouwd(LocalDate datumbegingeldigheidcultuurcodeonbebouwd) {
        this.datumbegingeldigheidcultuurcodeonbebouwd = datumbegingeldigheidcultuurcodeonbebouwd;
    }

    public LocalDate getDatumeindegeldigheidcultuurcodeonbebouwd() {
        return this.datumeindegeldigheidcultuurcodeonbebouwd;
    }

    public Cultuurcodeonbebouwd datumeindegeldigheidcultuurcodeonbebouwd(LocalDate datumeindegeldigheidcultuurcodeonbebouwd) {
        this.setDatumeindegeldigheidcultuurcodeonbebouwd(datumeindegeldigheidcultuurcodeonbebouwd);
        return this;
    }

    public void setDatumeindegeldigheidcultuurcodeonbebouwd(LocalDate datumeindegeldigheidcultuurcodeonbebouwd) {
        this.datumeindegeldigheidcultuurcodeonbebouwd = datumeindegeldigheidcultuurcodeonbebouwd;
    }

    public String getNaamcultuurcodeonbebouwd() {
        return this.naamcultuurcodeonbebouwd;
    }

    public Cultuurcodeonbebouwd naamcultuurcodeonbebouwd(String naamcultuurcodeonbebouwd) {
        this.setNaamcultuurcodeonbebouwd(naamcultuurcodeonbebouwd);
        return this;
    }

    public void setNaamcultuurcodeonbebouwd(String naamcultuurcodeonbebouwd) {
        this.naamcultuurcodeonbebouwd = naamcultuurcodeonbebouwd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cultuurcodeonbebouwd)) {
            return false;
        }
        return getId() != null && getId().equals(((Cultuurcodeonbebouwd) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cultuurcodeonbebouwd{" +
            "id=" + getId() +
            ", cultuurcodeonbebouwd='" + getCultuurcodeonbebouwd() + "'" +
            ", datumbegingeldigheidcultuurcodeonbebouwd='" + getDatumbegingeldigheidcultuurcodeonbebouwd() + "'" +
            ", datumeindegeldigheidcultuurcodeonbebouwd='" + getDatumeindegeldigheidcultuurcodeonbebouwd() + "'" +
            ", naamcultuurcodeonbebouwd='" + getNaamcultuurcodeonbebouwd() + "'" +
            "}";
    }
}
