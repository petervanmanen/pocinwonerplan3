package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Meldingeigenbijdrage.
 */
@Entity
@Table(name = "meldingeigenbijdrage")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Meldingeigenbijdrage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "datumstop")
    private LocalDate datumstop;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Meldingeigenbijdrage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Meldingeigenbijdrage datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public LocalDate getDatumstop() {
        return this.datumstop;
    }

    public Meldingeigenbijdrage datumstop(LocalDate datumstop) {
        this.setDatumstop(datumstop);
        return this;
    }

    public void setDatumstop(LocalDate datumstop) {
        this.datumstop = datumstop;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meldingeigenbijdrage)) {
            return false;
        }
        return getId() != null && getId().equals(((Meldingeigenbijdrage) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meldingeigenbijdrage{" +
            "id=" + getId() +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumstop='" + getDatumstop() + "'" +
            "}";
    }
}
