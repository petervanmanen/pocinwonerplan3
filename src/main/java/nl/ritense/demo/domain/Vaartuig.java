package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Vaartuig.
 */
@Entity
@Table(name = "vaartuig")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vaartuig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "breedte")
    private String breedte;

    @Column(name = "hoogte")
    private String hoogte;

    @Size(max = 20)
    @Column(name = "kleur", length = 20)
    private String kleur;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "naamvaartuig")
    private String naamvaartuig;

    @Column(name = "registratienummer")
    private String registratienummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vaartuig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreedte() {
        return this.breedte;
    }

    public Vaartuig breedte(String breedte) {
        this.setBreedte(breedte);
        return this;
    }

    public void setBreedte(String breedte) {
        this.breedte = breedte;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Vaartuig hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getKleur() {
        return this.kleur;
    }

    public Vaartuig kleur(String kleur) {
        this.setKleur(kleur);
        return this;
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Vaartuig lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getNaamvaartuig() {
        return this.naamvaartuig;
    }

    public Vaartuig naamvaartuig(String naamvaartuig) {
        this.setNaamvaartuig(naamvaartuig);
        return this;
    }

    public void setNaamvaartuig(String naamvaartuig) {
        this.naamvaartuig = naamvaartuig;
    }

    public String getRegistratienummer() {
        return this.registratienummer;
    }

    public Vaartuig registratienummer(String registratienummer) {
        this.setRegistratienummer(registratienummer);
        return this;
    }

    public void setRegistratienummer(String registratienummer) {
        this.registratienummer = registratienummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vaartuig)) {
            return false;
        }
        return getId() != null && getId().equals(((Vaartuig) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vaartuig{" +
            "id=" + getId() +
            ", breedte='" + getBreedte() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", kleur='" + getKleur() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", naamvaartuig='" + getNaamvaartuig() + "'" +
            ", registratienummer='" + getRegistratienummer() + "'" +
            "}";
    }
}
