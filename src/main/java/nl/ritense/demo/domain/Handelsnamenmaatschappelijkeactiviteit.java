package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Handelsnamenmaatschappelijkeactiviteit.
 */
@Entity
@Table(name = "handelsnamenmaatschappelijkeactiviteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Handelsnamenmaatschappelijkeactiviteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "handelsnaam")
    private String handelsnaam;

    @Column(name = "verkortenaam")
    private String verkortenaam;

    @Column(name = "volgorde")
    private String volgorde;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Handelsnamenmaatschappelijkeactiviteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandelsnaam() {
        return this.handelsnaam;
    }

    public Handelsnamenmaatschappelijkeactiviteit handelsnaam(String handelsnaam) {
        this.setHandelsnaam(handelsnaam);
        return this;
    }

    public void setHandelsnaam(String handelsnaam) {
        this.handelsnaam = handelsnaam;
    }

    public String getVerkortenaam() {
        return this.verkortenaam;
    }

    public Handelsnamenmaatschappelijkeactiviteit verkortenaam(String verkortenaam) {
        this.setVerkortenaam(verkortenaam);
        return this;
    }

    public void setVerkortenaam(String verkortenaam) {
        this.verkortenaam = verkortenaam;
    }

    public String getVolgorde() {
        return this.volgorde;
    }

    public Handelsnamenmaatschappelijkeactiviteit volgorde(String volgorde) {
        this.setVolgorde(volgorde);
        return this;
    }

    public void setVolgorde(String volgorde) {
        this.volgorde = volgorde;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Handelsnamenmaatschappelijkeactiviteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Handelsnamenmaatschappelijkeactiviteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Handelsnamenmaatschappelijkeactiviteit{" +
            "id=" + getId() +
            ", handelsnaam='" + getHandelsnaam() + "'" +
            ", verkortenaam='" + getVerkortenaam() + "'" +
            ", volgorde='" + getVolgorde() + "'" +
            "}";
    }
}
