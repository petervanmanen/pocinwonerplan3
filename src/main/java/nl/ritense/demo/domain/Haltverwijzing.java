package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Haltverwijzing.
 */
@Entity
@Table(name = "haltverwijzing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Haltverwijzing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afdoening")
    private String afdoening;

    @Column(name = "datummutatie")
    private LocalDate datummutatie;

    @Column(name = "datumretour")
    private LocalDate datumretour;

    @Column(name = "memo")
    private String memo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Haltverwijzing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfdoening() {
        return this.afdoening;
    }

    public Haltverwijzing afdoening(String afdoening) {
        this.setAfdoening(afdoening);
        return this;
    }

    public void setAfdoening(String afdoening) {
        this.afdoening = afdoening;
    }

    public LocalDate getDatummutatie() {
        return this.datummutatie;
    }

    public Haltverwijzing datummutatie(LocalDate datummutatie) {
        this.setDatummutatie(datummutatie);
        return this;
    }

    public void setDatummutatie(LocalDate datummutatie) {
        this.datummutatie = datummutatie;
    }

    public LocalDate getDatumretour() {
        return this.datumretour;
    }

    public Haltverwijzing datumretour(LocalDate datumretour) {
        this.setDatumretour(datumretour);
        return this;
    }

    public void setDatumretour(LocalDate datumretour) {
        this.datumretour = datumretour;
    }

    public String getMemo() {
        return this.memo;
    }

    public Haltverwijzing memo(String memo) {
        this.setMemo(memo);
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Haltverwijzing)) {
            return false;
        }
        return getId() != null && getId().equals(((Haltverwijzing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Haltverwijzing{" +
            "id=" + getId() +
            ", afdoening='" + getAfdoening() + "'" +
            ", datummutatie='" + getDatummutatie() + "'" +
            ", datumretour='" + getDatumretour() + "'" +
            ", memo='" + getMemo() + "'" +
            "}";
    }
}
