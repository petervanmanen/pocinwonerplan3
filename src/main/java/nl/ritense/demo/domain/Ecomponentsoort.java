package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Ecomponentsoort.
 */
@Entity
@Table(name = "ecomponentsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ecomponentsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ecomponent")
    private String ecomponent;

    @Column(name = "ecomponentcode")
    private String ecomponentcode;

    @Column(name = "kolom")
    private String kolom;

    @Column(name = "kolomcode")
    private String kolomcode;

    @Size(max = 100)
    @Column(name = "regeling", length = 100)
    private String regeling;

    @Column(name = "regelingcode")
    private String regelingcode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ecomponentsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEcomponent() {
        return this.ecomponent;
    }

    public Ecomponentsoort ecomponent(String ecomponent) {
        this.setEcomponent(ecomponent);
        return this;
    }

    public void setEcomponent(String ecomponent) {
        this.ecomponent = ecomponent;
    }

    public String getEcomponentcode() {
        return this.ecomponentcode;
    }

    public Ecomponentsoort ecomponentcode(String ecomponentcode) {
        this.setEcomponentcode(ecomponentcode);
        return this;
    }

    public void setEcomponentcode(String ecomponentcode) {
        this.ecomponentcode = ecomponentcode;
    }

    public String getKolom() {
        return this.kolom;
    }

    public Ecomponentsoort kolom(String kolom) {
        this.setKolom(kolom);
        return this;
    }

    public void setKolom(String kolom) {
        this.kolom = kolom;
    }

    public String getKolomcode() {
        return this.kolomcode;
    }

    public Ecomponentsoort kolomcode(String kolomcode) {
        this.setKolomcode(kolomcode);
        return this;
    }

    public void setKolomcode(String kolomcode) {
        this.kolomcode = kolomcode;
    }

    public String getRegeling() {
        return this.regeling;
    }

    public Ecomponentsoort regeling(String regeling) {
        this.setRegeling(regeling);
        return this;
    }

    public void setRegeling(String regeling) {
        this.regeling = regeling;
    }

    public String getRegelingcode() {
        return this.regelingcode;
    }

    public Ecomponentsoort regelingcode(String regelingcode) {
        this.setRegelingcode(regelingcode);
        return this;
    }

    public void setRegelingcode(String regelingcode) {
        this.regelingcode = regelingcode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ecomponentsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Ecomponentsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ecomponentsoort{" +
            "id=" + getId() +
            ", ecomponent='" + getEcomponent() + "'" +
            ", ecomponentcode='" + getEcomponentcode() + "'" +
            ", kolom='" + getKolom() + "'" +
            ", kolomcode='" + getKolomcode() + "'" +
            ", regeling='" + getRegeling() + "'" +
            ", regelingcode='" + getRegelingcode() + "'" +
            "}";
    }
}
