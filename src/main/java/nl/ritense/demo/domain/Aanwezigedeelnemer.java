package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Aanwezigedeelnemer.
 */
@Entity
@Table(name = "aanwezigedeelnemer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aanwezigedeelnemer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanvangaanwezigheid")
    private String aanvangaanwezigheid;

    @Column(name = "eindeaanwezigheid")
    private String eindeaanwezigheid;

    @Column(name = "naam")
    private String naam;

    @Column(name = "rol")
    private String rol;

    @Column(name = "vertegenwoordigtorganisatie")
    private String vertegenwoordigtorganisatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aanwezigedeelnemer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanvangaanwezigheid() {
        return this.aanvangaanwezigheid;
    }

    public Aanwezigedeelnemer aanvangaanwezigheid(String aanvangaanwezigheid) {
        this.setAanvangaanwezigheid(aanvangaanwezigheid);
        return this;
    }

    public void setAanvangaanwezigheid(String aanvangaanwezigheid) {
        this.aanvangaanwezigheid = aanvangaanwezigheid;
    }

    public String getEindeaanwezigheid() {
        return this.eindeaanwezigheid;
    }

    public Aanwezigedeelnemer eindeaanwezigheid(String eindeaanwezigheid) {
        this.setEindeaanwezigheid(eindeaanwezigheid);
        return this;
    }

    public void setEindeaanwezigheid(String eindeaanwezigheid) {
        this.eindeaanwezigheid = eindeaanwezigheid;
    }

    public String getNaam() {
        return this.naam;
    }

    public Aanwezigedeelnemer naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getRol() {
        return this.rol;
    }

    public Aanwezigedeelnemer rol(String rol) {
        this.setRol(rol);
        return this;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getVertegenwoordigtorganisatie() {
        return this.vertegenwoordigtorganisatie;
    }

    public Aanwezigedeelnemer vertegenwoordigtorganisatie(String vertegenwoordigtorganisatie) {
        this.setVertegenwoordigtorganisatie(vertegenwoordigtorganisatie);
        return this;
    }

    public void setVertegenwoordigtorganisatie(String vertegenwoordigtorganisatie) {
        this.vertegenwoordigtorganisatie = vertegenwoordigtorganisatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aanwezigedeelnemer)) {
            return false;
        }
        return getId() != null && getId().equals(((Aanwezigedeelnemer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aanwezigedeelnemer{" +
            "id=" + getId() +
            ", aanvangaanwezigheid='" + getAanvangaanwezigheid() + "'" +
            ", eindeaanwezigheid='" + getEindeaanwezigheid() + "'" +
            ", naam='" + getNaam() + "'" +
            ", rol='" + getRol() + "'" +
            ", vertegenwoordigtorganisatie='" + getVertegenwoordigtorganisatie() + "'" +
            "}";
    }
}
