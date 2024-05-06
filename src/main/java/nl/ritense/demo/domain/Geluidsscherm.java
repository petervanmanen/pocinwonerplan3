package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Geluidsscherm.
 */
@Entity
@Table(name = "geluidsscherm")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Geluidsscherm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantaldeuren")
    private String aantaldeuren;

    @Column(name = "aantalpanelen")
    private String aantalpanelen;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Geluidsscherm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantaldeuren() {
        return this.aantaldeuren;
    }

    public Geluidsscherm aantaldeuren(String aantaldeuren) {
        this.setAantaldeuren(aantaldeuren);
        return this;
    }

    public void setAantaldeuren(String aantaldeuren) {
        this.aantaldeuren = aantaldeuren;
    }

    public String getAantalpanelen() {
        return this.aantalpanelen;
    }

    public Geluidsscherm aantalpanelen(String aantalpanelen) {
        this.setAantalpanelen(aantalpanelen);
        return this;
    }

    public void setAantalpanelen(String aantalpanelen) {
        this.aantalpanelen = aantalpanelen;
    }

    public String getType() {
        return this.type;
    }

    public Geluidsscherm type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Geluidsscherm)) {
            return false;
        }
        return getId() != null && getId().equals(((Geluidsscherm) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Geluidsscherm{" +
            "id=" + getId() +
            ", aantaldeuren='" + getAantaldeuren() + "'" +
            ", aantalpanelen='" + getAantalpanelen() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
