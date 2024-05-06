package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Wozdeelobject.
 */
@Entity
@Table(name = "wozdeelobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wozdeelobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codewozdeelobject")
    private String codewozdeelobject;

    @Column(name = "datumbegingeldigheiddeelobject")
    private LocalDate datumbegingeldigheiddeelobject;

    @Column(name = "datumeindegeldigheiddeelobject")
    private LocalDate datumeindegeldigheiddeelobject;

    @Column(name = "statuswozdeelobject")
    private String statuswozdeelobject;

    @Column(name = "wozdeelobjectnummer")
    private String wozdeelobjectnummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wozdeelobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodewozdeelobject() {
        return this.codewozdeelobject;
    }

    public Wozdeelobject codewozdeelobject(String codewozdeelobject) {
        this.setCodewozdeelobject(codewozdeelobject);
        return this;
    }

    public void setCodewozdeelobject(String codewozdeelobject) {
        this.codewozdeelobject = codewozdeelobject;
    }

    public LocalDate getDatumbegingeldigheiddeelobject() {
        return this.datumbegingeldigheiddeelobject;
    }

    public Wozdeelobject datumbegingeldigheiddeelobject(LocalDate datumbegingeldigheiddeelobject) {
        this.setDatumbegingeldigheiddeelobject(datumbegingeldigheiddeelobject);
        return this;
    }

    public void setDatumbegingeldigheiddeelobject(LocalDate datumbegingeldigheiddeelobject) {
        this.datumbegingeldigheiddeelobject = datumbegingeldigheiddeelobject;
    }

    public LocalDate getDatumeindegeldigheiddeelobject() {
        return this.datumeindegeldigheiddeelobject;
    }

    public Wozdeelobject datumeindegeldigheiddeelobject(LocalDate datumeindegeldigheiddeelobject) {
        this.setDatumeindegeldigheiddeelobject(datumeindegeldigheiddeelobject);
        return this;
    }

    public void setDatumeindegeldigheiddeelobject(LocalDate datumeindegeldigheiddeelobject) {
        this.datumeindegeldigheiddeelobject = datumeindegeldigheiddeelobject;
    }

    public String getStatuswozdeelobject() {
        return this.statuswozdeelobject;
    }

    public Wozdeelobject statuswozdeelobject(String statuswozdeelobject) {
        this.setStatuswozdeelobject(statuswozdeelobject);
        return this;
    }

    public void setStatuswozdeelobject(String statuswozdeelobject) {
        this.statuswozdeelobject = statuswozdeelobject;
    }

    public String getWozdeelobjectnummer() {
        return this.wozdeelobjectnummer;
    }

    public Wozdeelobject wozdeelobjectnummer(String wozdeelobjectnummer) {
        this.setWozdeelobjectnummer(wozdeelobjectnummer);
        return this;
    }

    public void setWozdeelobjectnummer(String wozdeelobjectnummer) {
        this.wozdeelobjectnummer = wozdeelobjectnummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wozdeelobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Wozdeelobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wozdeelobject{" +
            "id=" + getId() +
            ", codewozdeelobject='" + getCodewozdeelobject() + "'" +
            ", datumbegingeldigheiddeelobject='" + getDatumbegingeldigheiddeelobject() + "'" +
            ", datumeindegeldigheiddeelobject='" + getDatumeindegeldigheiddeelobject() + "'" +
            ", statuswozdeelobject='" + getStatuswozdeelobject() + "'" +
            ", wozdeelobjectnummer='" + getWozdeelobjectnummer() + "'" +
            "}";
    }
}
