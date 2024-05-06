package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Handelsnamenvestiging.
 */
@Entity
@Table(name = "handelsnamenvestiging")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Handelsnamenvestiging implements Serializable {

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

    public Handelsnamenvestiging id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandelsnaam() {
        return this.handelsnaam;
    }

    public Handelsnamenvestiging handelsnaam(String handelsnaam) {
        this.setHandelsnaam(handelsnaam);
        return this;
    }

    public void setHandelsnaam(String handelsnaam) {
        this.handelsnaam = handelsnaam;
    }

    public String getVerkortenaam() {
        return this.verkortenaam;
    }

    public Handelsnamenvestiging verkortenaam(String verkortenaam) {
        this.setVerkortenaam(verkortenaam);
        return this;
    }

    public void setVerkortenaam(String verkortenaam) {
        this.verkortenaam = verkortenaam;
    }

    public String getVolgorde() {
        return this.volgorde;
    }

    public Handelsnamenvestiging volgorde(String volgorde) {
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
        if (!(o instanceof Handelsnamenvestiging)) {
            return false;
        }
        return getId() != null && getId().equals(((Handelsnamenvestiging) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Handelsnamenvestiging{" +
            "id=" + getId() +
            ", handelsnaam='" + getHandelsnaam() + "'" +
            ", verkortenaam='" + getVerkortenaam() + "'" +
            ", volgorde='" + getVolgorde() + "'" +
            "}";
    }
}
