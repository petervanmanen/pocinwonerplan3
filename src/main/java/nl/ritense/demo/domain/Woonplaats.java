package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Woonplaats.
 */
@Entity
@Table(name = "woonplaats")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Woonplaats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumingang")
    private LocalDate datumingang;

    @Column(name = "geconstateerd")
    private Boolean geconstateerd;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "inonderzoek")
    private Boolean inonderzoek;

    @Column(name = "status")
    private String status;

    @Column(name = "versie")
    private String versie;

    @Column(name = "woonplaatsnaam")
    private String woonplaatsnaam;

    @Column(name = "woonplaatsnaamnen")
    private String woonplaatsnaamnen;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ligtinWoonplaats")
    @JsonIgnoreProperties(
        value = {
            "ligtinWoonplaats",
            "ligtinBuurt",
            "ligtinGebieds",
            "verwijstnaarAdresaanduiding",
            "emptyBriefadres",
            "heeftalslocatieadresVestigings",
        },
        allowSetters = true
    )
    private Set<Nummeraanduiding> ligtinNummeraanduidings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftalscorrespondentieadrespostadresinWoonplaats")
    @JsonIgnoreProperties(value = { "heeftalscorrespondentieadrespostadresinWoonplaats" }, allowSetters = true)
    private Set<Postadres> heeftalscorrespondentieadrespostadresinPostadres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Woonplaats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Woonplaats datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Woonplaats datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Woonplaats datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumingang() {
        return this.datumingang;
    }

    public Woonplaats datumingang(LocalDate datumingang) {
        this.setDatumingang(datumingang);
        return this;
    }

    public void setDatumingang(LocalDate datumingang) {
        this.datumingang = datumingang;
    }

    public Boolean getGeconstateerd() {
        return this.geconstateerd;
    }

    public Woonplaats geconstateerd(Boolean geconstateerd) {
        this.setGeconstateerd(geconstateerd);
        return this;
    }

    public void setGeconstateerd(Boolean geconstateerd) {
        this.geconstateerd = geconstateerd;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Woonplaats geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Woonplaats identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public Boolean getInonderzoek() {
        return this.inonderzoek;
    }

    public Woonplaats inonderzoek(Boolean inonderzoek) {
        this.setInonderzoek(inonderzoek);
        return this;
    }

    public void setInonderzoek(Boolean inonderzoek) {
        this.inonderzoek = inonderzoek;
    }

    public String getStatus() {
        return this.status;
    }

    public Woonplaats status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersie() {
        return this.versie;
    }

    public Woonplaats versie(String versie) {
        this.setVersie(versie);
        return this;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public String getWoonplaatsnaam() {
        return this.woonplaatsnaam;
    }

    public Woonplaats woonplaatsnaam(String woonplaatsnaam) {
        this.setWoonplaatsnaam(woonplaatsnaam);
        return this;
    }

    public void setWoonplaatsnaam(String woonplaatsnaam) {
        this.woonplaatsnaam = woonplaatsnaam;
    }

    public String getWoonplaatsnaamnen() {
        return this.woonplaatsnaamnen;
    }

    public Woonplaats woonplaatsnaamnen(String woonplaatsnaamnen) {
        this.setWoonplaatsnaamnen(woonplaatsnaamnen);
        return this;
    }

    public void setWoonplaatsnaamnen(String woonplaatsnaamnen) {
        this.woonplaatsnaamnen = woonplaatsnaamnen;
    }

    public Set<Nummeraanduiding> getLigtinNummeraanduidings() {
        return this.ligtinNummeraanduidings;
    }

    public void setLigtinNummeraanduidings(Set<Nummeraanduiding> nummeraanduidings) {
        if (this.ligtinNummeraanduidings != null) {
            this.ligtinNummeraanduidings.forEach(i -> i.setLigtinWoonplaats(null));
        }
        if (nummeraanduidings != null) {
            nummeraanduidings.forEach(i -> i.setLigtinWoonplaats(this));
        }
        this.ligtinNummeraanduidings = nummeraanduidings;
    }

    public Woonplaats ligtinNummeraanduidings(Set<Nummeraanduiding> nummeraanduidings) {
        this.setLigtinNummeraanduidings(nummeraanduidings);
        return this;
    }

    public Woonplaats addLigtinNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.ligtinNummeraanduidings.add(nummeraanduiding);
        nummeraanduiding.setLigtinWoonplaats(this);
        return this;
    }

    public Woonplaats removeLigtinNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.ligtinNummeraanduidings.remove(nummeraanduiding);
        nummeraanduiding.setLigtinWoonplaats(null);
        return this;
    }

    public Set<Postadres> getHeeftalscorrespondentieadrespostadresinPostadres() {
        return this.heeftalscorrespondentieadrespostadresinPostadres;
    }

    public void setHeeftalscorrespondentieadrespostadresinPostadres(Set<Postadres> postadres) {
        if (this.heeftalscorrespondentieadrespostadresinPostadres != null) {
            this.heeftalscorrespondentieadrespostadresinPostadres.forEach(
                    i -> i.setHeeftalscorrespondentieadrespostadresinWoonplaats(null)
                );
        }
        if (postadres != null) {
            postadres.forEach(i -> i.setHeeftalscorrespondentieadrespostadresinWoonplaats(this));
        }
        this.heeftalscorrespondentieadrespostadresinPostadres = postadres;
    }

    public Woonplaats heeftalscorrespondentieadrespostadresinPostadres(Set<Postadres> postadres) {
        this.setHeeftalscorrespondentieadrespostadresinPostadres(postadres);
        return this;
    }

    public Woonplaats addHeeftalscorrespondentieadrespostadresinPostadres(Postadres postadres) {
        this.heeftalscorrespondentieadrespostadresinPostadres.add(postadres);
        postadres.setHeeftalscorrespondentieadrespostadresinWoonplaats(this);
        return this;
    }

    public Woonplaats removeHeeftalscorrespondentieadrespostadresinPostadres(Postadres postadres) {
        this.heeftalscorrespondentieadrespostadresinPostadres.remove(postadres);
        postadres.setHeeftalscorrespondentieadrespostadresinWoonplaats(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Woonplaats)) {
            return false;
        }
        return getId() != null && getId().equals(((Woonplaats) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Woonplaats{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumingang='" + getDatumingang() + "'" +
            ", geconstateerd='" + getGeconstateerd() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inonderzoek='" + getInonderzoek() + "'" +
            ", status='" + getStatus() + "'" +
            ", versie='" + getVersie() + "'" +
            ", woonplaatsnaam='" + getWoonplaatsnaam() + "'" +
            ", woonplaatsnaamnen='" + getWoonplaatsnaamnen() + "'" +
            "}";
    }
}
