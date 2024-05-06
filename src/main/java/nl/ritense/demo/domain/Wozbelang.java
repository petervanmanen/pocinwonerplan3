package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Wozbelang.
 */
@Entity
@Table(name = "wozbelang")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wozbelang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "eigenaargebruiker")
    private String eigenaargebruiker;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wozbelang id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Wozbelang datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Wozbelang datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public String getEigenaargebruiker() {
        return this.eigenaargebruiker;
    }

    public Wozbelang eigenaargebruiker(String eigenaargebruiker) {
        this.setEigenaargebruiker(eigenaargebruiker);
        return this;
    }

    public void setEigenaargebruiker(String eigenaargebruiker) {
        this.eigenaargebruiker = eigenaargebruiker;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wozbelang)) {
            return false;
        }
        return getId() != null && getId().equals(((Wozbelang) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wozbelang{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", eigenaargebruiker='" + getEigenaargebruiker() + "'" +
            "}";
    }
}
