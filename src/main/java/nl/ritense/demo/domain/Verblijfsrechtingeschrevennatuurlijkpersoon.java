package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Verblijfsrechtingeschrevennatuurlijkpersoon.
 */
@Entity
@Table(name = "verblijfsrechtingeschrevennatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verblijfsrechtingeschrevennatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanduidingverblijfsrecht")
    private String aanduidingverblijfsrecht;

    @Column(name = "datumaanvangverblijfsrecht")
    private String datumaanvangverblijfsrecht;

    @Column(name = "datummededelingverblijfsrecht")
    private String datummededelingverblijfsrecht;

    @Column(name = "datumvoorzieneindeverblijfsrecht")
    private String datumvoorzieneindeverblijfsrecht;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verblijfsrechtingeschrevennatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanduidingverblijfsrecht() {
        return this.aanduidingverblijfsrecht;
    }

    public Verblijfsrechtingeschrevennatuurlijkpersoon aanduidingverblijfsrecht(String aanduidingverblijfsrecht) {
        this.setAanduidingverblijfsrecht(aanduidingverblijfsrecht);
        return this;
    }

    public void setAanduidingverblijfsrecht(String aanduidingverblijfsrecht) {
        this.aanduidingverblijfsrecht = aanduidingverblijfsrecht;
    }

    public String getDatumaanvangverblijfsrecht() {
        return this.datumaanvangverblijfsrecht;
    }

    public Verblijfsrechtingeschrevennatuurlijkpersoon datumaanvangverblijfsrecht(String datumaanvangverblijfsrecht) {
        this.setDatumaanvangverblijfsrecht(datumaanvangverblijfsrecht);
        return this;
    }

    public void setDatumaanvangverblijfsrecht(String datumaanvangverblijfsrecht) {
        this.datumaanvangverblijfsrecht = datumaanvangverblijfsrecht;
    }

    public String getDatummededelingverblijfsrecht() {
        return this.datummededelingverblijfsrecht;
    }

    public Verblijfsrechtingeschrevennatuurlijkpersoon datummededelingverblijfsrecht(String datummededelingverblijfsrecht) {
        this.setDatummededelingverblijfsrecht(datummededelingverblijfsrecht);
        return this;
    }

    public void setDatummededelingverblijfsrecht(String datummededelingverblijfsrecht) {
        this.datummededelingverblijfsrecht = datummededelingverblijfsrecht;
    }

    public String getDatumvoorzieneindeverblijfsrecht() {
        return this.datumvoorzieneindeverblijfsrecht;
    }

    public Verblijfsrechtingeschrevennatuurlijkpersoon datumvoorzieneindeverblijfsrecht(String datumvoorzieneindeverblijfsrecht) {
        this.setDatumvoorzieneindeverblijfsrecht(datumvoorzieneindeverblijfsrecht);
        return this;
    }

    public void setDatumvoorzieneindeverblijfsrecht(String datumvoorzieneindeverblijfsrecht) {
        this.datumvoorzieneindeverblijfsrecht = datumvoorzieneindeverblijfsrecht;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verblijfsrechtingeschrevennatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Verblijfsrechtingeschrevennatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verblijfsrechtingeschrevennatuurlijkpersoon{" +
            "id=" + getId() +
            ", aanduidingverblijfsrecht='" + getAanduidingverblijfsrecht() + "'" +
            ", datumaanvangverblijfsrecht='" + getDatumaanvangverblijfsrecht() + "'" +
            ", datummededelingverblijfsrecht='" + getDatummededelingverblijfsrecht() + "'" +
            ", datumvoorzieneindeverblijfsrecht='" + getDatumvoorzieneindeverblijfsrecht() + "'" +
            "}";
    }
}
