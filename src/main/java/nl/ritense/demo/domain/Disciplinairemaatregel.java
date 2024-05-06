package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Disciplinairemaatregel.
 */
@Entity
@Table(name = "disciplinairemaatregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Disciplinairemaatregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumgeconstateerd")
    private LocalDate datumgeconstateerd;

    @Column(name = "datumopgelegd")
    private LocalDate datumopgelegd;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "reden")
    private String reden;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Disciplinairemaatregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumgeconstateerd() {
        return this.datumgeconstateerd;
    }

    public Disciplinairemaatregel datumgeconstateerd(LocalDate datumgeconstateerd) {
        this.setDatumgeconstateerd(datumgeconstateerd);
        return this;
    }

    public void setDatumgeconstateerd(LocalDate datumgeconstateerd) {
        this.datumgeconstateerd = datumgeconstateerd;
    }

    public LocalDate getDatumopgelegd() {
        return this.datumopgelegd;
    }

    public Disciplinairemaatregel datumopgelegd(LocalDate datumopgelegd) {
        this.setDatumopgelegd(datumopgelegd);
        return this;
    }

    public void setDatumopgelegd(LocalDate datumopgelegd) {
        this.datumopgelegd = datumopgelegd;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Disciplinairemaatregel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getReden() {
        return this.reden;
    }

    public Disciplinairemaatregel reden(String reden) {
        this.setReden(reden);
        return this;
    }

    public void setReden(String reden) {
        this.reden = reden;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disciplinairemaatregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Disciplinairemaatregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disciplinairemaatregel{" +
            "id=" + getId() +
            ", datumgeconstateerd='" + getDatumgeconstateerd() + "'" +
            ", datumopgelegd='" + getDatumopgelegd() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", reden='" + getReden() + "'" +
            "}";
    }
}
