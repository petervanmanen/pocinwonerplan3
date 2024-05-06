package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Wozdeelobjectcode.
 */
@Entity
@Table(name = "wozdeelobjectcode")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wozdeelobjectcode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheiddeelojectcode")
    private LocalDate datumbegingeldigheiddeelojectcode;

    @Column(name = "datumeindegeldigheiddeelobjectcode")
    private LocalDate datumeindegeldigheiddeelobjectcode;

    @Column(name = "deelobjectcode")
    private String deelobjectcode;

    @Column(name = "naamdeelobjectcode")
    private String naamdeelobjectcode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wozdeelobjectcode id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheiddeelojectcode() {
        return this.datumbegingeldigheiddeelojectcode;
    }

    public Wozdeelobjectcode datumbegingeldigheiddeelojectcode(LocalDate datumbegingeldigheiddeelojectcode) {
        this.setDatumbegingeldigheiddeelojectcode(datumbegingeldigheiddeelojectcode);
        return this;
    }

    public void setDatumbegingeldigheiddeelojectcode(LocalDate datumbegingeldigheiddeelojectcode) {
        this.datumbegingeldigheiddeelojectcode = datumbegingeldigheiddeelojectcode;
    }

    public LocalDate getDatumeindegeldigheiddeelobjectcode() {
        return this.datumeindegeldigheiddeelobjectcode;
    }

    public Wozdeelobjectcode datumeindegeldigheiddeelobjectcode(LocalDate datumeindegeldigheiddeelobjectcode) {
        this.setDatumeindegeldigheiddeelobjectcode(datumeindegeldigheiddeelobjectcode);
        return this;
    }

    public void setDatumeindegeldigheiddeelobjectcode(LocalDate datumeindegeldigheiddeelobjectcode) {
        this.datumeindegeldigheiddeelobjectcode = datumeindegeldigheiddeelobjectcode;
    }

    public String getDeelobjectcode() {
        return this.deelobjectcode;
    }

    public Wozdeelobjectcode deelobjectcode(String deelobjectcode) {
        this.setDeelobjectcode(deelobjectcode);
        return this;
    }

    public void setDeelobjectcode(String deelobjectcode) {
        this.deelobjectcode = deelobjectcode;
    }

    public String getNaamdeelobjectcode() {
        return this.naamdeelobjectcode;
    }

    public Wozdeelobjectcode naamdeelobjectcode(String naamdeelobjectcode) {
        this.setNaamdeelobjectcode(naamdeelobjectcode);
        return this;
    }

    public void setNaamdeelobjectcode(String naamdeelobjectcode) {
        this.naamdeelobjectcode = naamdeelobjectcode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wozdeelobjectcode)) {
            return false;
        }
        return getId() != null && getId().equals(((Wozdeelobjectcode) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wozdeelobjectcode{" +
            "id=" + getId() +
            ", datumbegingeldigheiddeelojectcode='" + getDatumbegingeldigheiddeelojectcode() + "'" +
            ", datumeindegeldigheiddeelobjectcode='" + getDatumeindegeldigheiddeelobjectcode() + "'" +
            ", deelobjectcode='" + getDeelobjectcode() + "'" +
            ", naamdeelobjectcode='" + getNaamdeelobjectcode() + "'" +
            "}";
    }
}
