package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Nummeraanduiding.
 */
@Entity
@Table(name = "nummeraanduiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nummeraanduiding implements Serializable {

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

    @Column(name = "huisletter")
    private String huisletter;

    @Column(name = "huisnummer")
    private String huisnummer;

    @Column(name = "huisnummertoevoeging")
    private String huisnummertoevoeging;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "inonderzoek")
    private Boolean inonderzoek;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "status")
    private String status;

    @Column(name = "typeadresseerbaarobject")
    private String typeadresseerbaarobject;

    @Column(name = "versie")
    private String versie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ligtinNummeraanduidings", "heeftalscorrespondentieadrespostadresinPostadres" }, allowSetters = true)
    private Woonplaats ligtinWoonplaats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "komtovereenGebied", "ligtinNummeraanduidings", "zonderverblijfsobjectligtinPands", "ligtinAreaals" },
        allowSetters = true
    )
    private Buurt ligtinBuurt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_nummeraanduiding__ligtin_gebied",
        joinColumns = @JoinColumn(name = "nummeraanduiding_id"),
        inverseJoinColumns = @JoinColumn(name = "ligtin_gebied_id")
    )
    @JsonIgnoreProperties(value = { "komtovereenBuurt", "ligtinNummeraanduidings" }, allowSetters = true)
    private Set<Gebied> ligtinGebieds = new HashSet<>();

    @JsonIgnoreProperties(value = { "verwijstnaarNummeraanduiding" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "verwijstnaarNummeraanduiding")
    private Adresaanduiding verwijstnaarAdresaanduiding;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emptyNummeraanduiding")
    @JsonIgnoreProperties(value = { "emptyNummeraanduiding" }, allowSetters = true)
    private Set<Briefadres> emptyBriefadres = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftalslocatieadresNummeraanduiding")
    @JsonIgnoreProperties(value = { "heeftWerkgelegenheid", "heeftalslocatieadresNummeraanduiding", "bijContacts" }, allowSetters = true)
    private Set<Vestiging> heeftalslocatieadresVestigings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nummeraanduiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Nummeraanduiding datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Nummeraanduiding datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Nummeraanduiding datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumingang() {
        return this.datumingang;
    }

    public Nummeraanduiding datumingang(LocalDate datumingang) {
        this.setDatumingang(datumingang);
        return this;
    }

    public void setDatumingang(LocalDate datumingang) {
        this.datumingang = datumingang;
    }

    public Boolean getGeconstateerd() {
        return this.geconstateerd;
    }

    public Nummeraanduiding geconstateerd(Boolean geconstateerd) {
        this.setGeconstateerd(geconstateerd);
        return this;
    }

    public void setGeconstateerd(Boolean geconstateerd) {
        this.geconstateerd = geconstateerd;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Nummeraanduiding geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getHuisletter() {
        return this.huisletter;
    }

    public Nummeraanduiding huisletter(String huisletter) {
        this.setHuisletter(huisletter);
        return this;
    }

    public void setHuisletter(String huisletter) {
        this.huisletter = huisletter;
    }

    public String getHuisnummer() {
        return this.huisnummer;
    }

    public Nummeraanduiding huisnummer(String huisnummer) {
        this.setHuisnummer(huisnummer);
        return this;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getHuisnummertoevoeging() {
        return this.huisnummertoevoeging;
    }

    public Nummeraanduiding huisnummertoevoeging(String huisnummertoevoeging) {
        this.setHuisnummertoevoeging(huisnummertoevoeging);
        return this;
    }

    public void setHuisnummertoevoeging(String huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Nummeraanduiding identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public Boolean getInonderzoek() {
        return this.inonderzoek;
    }

    public Nummeraanduiding inonderzoek(Boolean inonderzoek) {
        this.setInonderzoek(inonderzoek);
        return this;
    }

    public void setInonderzoek(Boolean inonderzoek) {
        this.inonderzoek = inonderzoek;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public Nummeraanduiding postcode(String postcode) {
        this.setPostcode(postcode);
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStatus() {
        return this.status;
    }

    public Nummeraanduiding status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeadresseerbaarobject() {
        return this.typeadresseerbaarobject;
    }

    public Nummeraanduiding typeadresseerbaarobject(String typeadresseerbaarobject) {
        this.setTypeadresseerbaarobject(typeadresseerbaarobject);
        return this;
    }

    public void setTypeadresseerbaarobject(String typeadresseerbaarobject) {
        this.typeadresseerbaarobject = typeadresseerbaarobject;
    }

    public String getVersie() {
        return this.versie;
    }

    public Nummeraanduiding versie(String versie) {
        this.setVersie(versie);
        return this;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public Woonplaats getLigtinWoonplaats() {
        return this.ligtinWoonplaats;
    }

    public void setLigtinWoonplaats(Woonplaats woonplaats) {
        this.ligtinWoonplaats = woonplaats;
    }

    public Nummeraanduiding ligtinWoonplaats(Woonplaats woonplaats) {
        this.setLigtinWoonplaats(woonplaats);
        return this;
    }

    public Buurt getLigtinBuurt() {
        return this.ligtinBuurt;
    }

    public void setLigtinBuurt(Buurt buurt) {
        this.ligtinBuurt = buurt;
    }

    public Nummeraanduiding ligtinBuurt(Buurt buurt) {
        this.setLigtinBuurt(buurt);
        return this;
    }

    public Set<Gebied> getLigtinGebieds() {
        return this.ligtinGebieds;
    }

    public void setLigtinGebieds(Set<Gebied> gebieds) {
        this.ligtinGebieds = gebieds;
    }

    public Nummeraanduiding ligtinGebieds(Set<Gebied> gebieds) {
        this.setLigtinGebieds(gebieds);
        return this;
    }

    public Nummeraanduiding addLigtinGebied(Gebied gebied) {
        this.ligtinGebieds.add(gebied);
        return this;
    }

    public Nummeraanduiding removeLigtinGebied(Gebied gebied) {
        this.ligtinGebieds.remove(gebied);
        return this;
    }

    public Adresaanduiding getVerwijstnaarAdresaanduiding() {
        return this.verwijstnaarAdresaanduiding;
    }

    public void setVerwijstnaarAdresaanduiding(Adresaanduiding adresaanduiding) {
        if (this.verwijstnaarAdresaanduiding != null) {
            this.verwijstnaarAdresaanduiding.setVerwijstnaarNummeraanduiding(null);
        }
        if (adresaanduiding != null) {
            adresaanduiding.setVerwijstnaarNummeraanduiding(this);
        }
        this.verwijstnaarAdresaanduiding = adresaanduiding;
    }

    public Nummeraanduiding verwijstnaarAdresaanduiding(Adresaanduiding adresaanduiding) {
        this.setVerwijstnaarAdresaanduiding(adresaanduiding);
        return this;
    }

    public Set<Briefadres> getEmptyBriefadres() {
        return this.emptyBriefadres;
    }

    public void setEmptyBriefadres(Set<Briefadres> briefadres) {
        if (this.emptyBriefadres != null) {
            this.emptyBriefadres.forEach(i -> i.setEmptyNummeraanduiding(null));
        }
        if (briefadres != null) {
            briefadres.forEach(i -> i.setEmptyNummeraanduiding(this));
        }
        this.emptyBriefadres = briefadres;
    }

    public Nummeraanduiding emptyBriefadres(Set<Briefadres> briefadres) {
        this.setEmptyBriefadres(briefadres);
        return this;
    }

    public Nummeraanduiding addEmptyBriefadres(Briefadres briefadres) {
        this.emptyBriefadres.add(briefadres);
        briefadres.setEmptyNummeraanduiding(this);
        return this;
    }

    public Nummeraanduiding removeEmptyBriefadres(Briefadres briefadres) {
        this.emptyBriefadres.remove(briefadres);
        briefadres.setEmptyNummeraanduiding(null);
        return this;
    }

    public Set<Vestiging> getHeeftalslocatieadresVestigings() {
        return this.heeftalslocatieadresVestigings;
    }

    public void setHeeftalslocatieadresVestigings(Set<Vestiging> vestigings) {
        if (this.heeftalslocatieadresVestigings != null) {
            this.heeftalslocatieadresVestigings.forEach(i -> i.setHeeftalslocatieadresNummeraanduiding(null));
        }
        if (vestigings != null) {
            vestigings.forEach(i -> i.setHeeftalslocatieadresNummeraanduiding(this));
        }
        this.heeftalslocatieadresVestigings = vestigings;
    }

    public Nummeraanduiding heeftalslocatieadresVestigings(Set<Vestiging> vestigings) {
        this.setHeeftalslocatieadresVestigings(vestigings);
        return this;
    }

    public Nummeraanduiding addHeeftalslocatieadresVestiging(Vestiging vestiging) {
        this.heeftalslocatieadresVestigings.add(vestiging);
        vestiging.setHeeftalslocatieadresNummeraanduiding(this);
        return this;
    }

    public Nummeraanduiding removeHeeftalslocatieadresVestiging(Vestiging vestiging) {
        this.heeftalslocatieadresVestigings.remove(vestiging);
        vestiging.setHeeftalslocatieadresNummeraanduiding(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nummeraanduiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Nummeraanduiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nummeraanduiding{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumingang='" + getDatumingang() + "'" +
            ", geconstateerd='" + getGeconstateerd() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", huisletter='" + getHuisletter() + "'" +
            ", huisnummer='" + getHuisnummer() + "'" +
            ", huisnummertoevoeging='" + getHuisnummertoevoeging() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inonderzoek='" + getInonderzoek() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", status='" + getStatus() + "'" +
            ", typeadresseerbaarobject='" + getTypeadresseerbaarobject() + "'" +
            ", versie='" + getVersie() + "'" +
            "}";
    }
}
