package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Sportterrein.
 */
@Entity
@Table(name = "sportterrein")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sportterrein implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "drainage")
    private Boolean drainage;

    @Column(name = "gebruiksvorm")
    private String gebruiksvorm;

    @Column(name = "sportcomplex")
    private String sportcomplex;

    @Column(name = "sportterreintypesport")
    private String sportterreintypesport;

    @Column(name = "type")
    private String type;

    @Column(name = "typeplus")
    private String typeplus;

    @Column(name = "veldnummer")
    private String veldnummer;

    @Column(name = "verlicht")
    private Boolean verlicht;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sportterrein id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDrainage() {
        return this.drainage;
    }

    public Sportterrein drainage(Boolean drainage) {
        this.setDrainage(drainage);
        return this;
    }

    public void setDrainage(Boolean drainage) {
        this.drainage = drainage;
    }

    public String getGebruiksvorm() {
        return this.gebruiksvorm;
    }

    public Sportterrein gebruiksvorm(String gebruiksvorm) {
        this.setGebruiksvorm(gebruiksvorm);
        return this;
    }

    public void setGebruiksvorm(String gebruiksvorm) {
        this.gebruiksvorm = gebruiksvorm;
    }

    public String getSportcomplex() {
        return this.sportcomplex;
    }

    public Sportterrein sportcomplex(String sportcomplex) {
        this.setSportcomplex(sportcomplex);
        return this;
    }

    public void setSportcomplex(String sportcomplex) {
        this.sportcomplex = sportcomplex;
    }

    public String getSportterreintypesport() {
        return this.sportterreintypesport;
    }

    public Sportterrein sportterreintypesport(String sportterreintypesport) {
        this.setSportterreintypesport(sportterreintypesport);
        return this;
    }

    public void setSportterreintypesport(String sportterreintypesport) {
        this.sportterreintypesport = sportterreintypesport;
    }

    public String getType() {
        return this.type;
    }

    public Sportterrein type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Sportterrein typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    public String getVeldnummer() {
        return this.veldnummer;
    }

    public Sportterrein veldnummer(String veldnummer) {
        this.setVeldnummer(veldnummer);
        return this;
    }

    public void setVeldnummer(String veldnummer) {
        this.veldnummer = veldnummer;
    }

    public Boolean getVerlicht() {
        return this.verlicht;
    }

    public Sportterrein verlicht(Boolean verlicht) {
        this.setVerlicht(verlicht);
        return this;
    }

    public void setVerlicht(Boolean verlicht) {
        this.verlicht = verlicht;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sportterrein)) {
            return false;
        }
        return getId() != null && getId().equals(((Sportterrein) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sportterrein{" +
            "id=" + getId() +
            ", drainage='" + getDrainage() + "'" +
            ", gebruiksvorm='" + getGebruiksvorm() + "'" +
            ", sportcomplex='" + getSportcomplex() + "'" +
            ", sportterreintypesport='" + getSportterreintypesport() + "'" +
            ", type='" + getType() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            ", veldnummer='" + getVeldnummer() + "'" +
            ", verlicht='" + getVerlicht() + "'" +
            "}";
    }
}
