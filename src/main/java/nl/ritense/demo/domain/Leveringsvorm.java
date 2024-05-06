package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Leveringsvorm.
 */
@Entity
@Table(name = "leveringsvorm")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Leveringsvorm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "leveringsvormcode")
    private String leveringsvormcode;

    @Column(name = "naam")
    private String naam;

    @Column(name = "wet")
    private String wet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Leveringsvorm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeveringsvormcode() {
        return this.leveringsvormcode;
    }

    public Leveringsvorm leveringsvormcode(String leveringsvormcode) {
        this.setLeveringsvormcode(leveringsvormcode);
        return this;
    }

    public void setLeveringsvormcode(String leveringsvormcode) {
        this.leveringsvormcode = leveringsvormcode;
    }

    public String getNaam() {
        return this.naam;
    }

    public Leveringsvorm naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getWet() {
        return this.wet;
    }

    public Leveringsvorm wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leveringsvorm)) {
            return false;
        }
        return getId() != null && getId().equals(((Leveringsvorm) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leveringsvorm{" +
            "id=" + getId() +
            ", leveringsvormcode='" + getLeveringsvormcode() + "'" +
            ", naam='" + getNaam() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
