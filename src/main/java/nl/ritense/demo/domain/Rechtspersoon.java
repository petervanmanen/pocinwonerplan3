package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rechtspersoon.
 */
@Entity
@Table(name = "rechtspersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rechtspersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresbinnenland")
    private String adresbinnenland;

    @Column(name = "adresbuitenland")
    private String adresbuitenland;

    @Size(max = 100)
    @Column(name = "adrescorrespondentie", length = 100)
    private String adrescorrespondentie;

    @Column(name = "emailadres")
    private String emailadres;

    @Size(max = 20)
    @Column(name = "faxnummer", length = 20)
    private String faxnummer;

    @Column(name = "identificatie")
    private String identificatie;

    @Size(max = 20)
    @Column(name = "kvknummer", length = 20)
    private String kvknummer;

    @Column(name = "naam")
    private String naam;

    @Size(max = 100)
    @Column(name = "rechtsvorm", length = 100)
    private String rechtsvorm;

    @Column(name = "rekeningnummer")
    private String rekeningnummer;

    @Size(max = 20)
    @Column(name = "telefoonnummer", length = 20)
    private String telefoonnummer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectleiderRechtspersoon")
    @JsonIgnoreProperties(value = { "heeftDocuments", "heeftSubsidie", "projectleiderRechtspersoon" }, allowSetters = true)
    private Set<Rapportagemoment> projectleiderRapportagemoments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aanvragerRechtspersoon")
    @JsonIgnoreProperties(
        value = {
            "heeftZaak",
            "heeftRapportagemoments",
            "heeftTaaks",
            "valtbinnenSector",
            "behandelaarMedewerker",
            "verstrekkerRechtspersoon",
            "heeftKostenplaats",
            "heeftDocument",
            "betreftSubsidieaanvraag",
            "betreftSubsidiebeschikking",
            "aanvragerRechtspersoon",
            "aanvragerMedewerker",
        },
        allowSetters = true
    )
    private Set<Subsidie> aanvragerSubsidies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftRechtspersoon")
    @JsonIgnoreProperties(value = { "emptyAantekenings", "heeftRechtspersoon", "bezwaartZekerheidsrechts" }, allowSetters = true)
    private Set<Tenaamstelling> heeftTenaamstellings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_rechtspersoon__betrokkenen_kadastralemutatie",
        joinColumns = @JoinColumn(name = "rechtspersoon_id"),
        inverseJoinColumns = @JoinColumn(name = "betrokkenen_kadastralemutatie_id")
    )
    @JsonIgnoreProperties(value = { "betrokkenenRechtspersoons" }, allowSetters = true)
    private Set<Kadastralemutatie> betrokkenenKadastralemutaties = new HashSet<>();

    @JsonIgnoreProperties(value = { "isCollegelid", "isRaadslid", "isRechtspersoon", "heeftRaadsstuks" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "isRechtspersoon")
    private Indiener isIndiener;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houderRechtspersoon")
    @JsonIgnoreProperties(
        value = { "resulteertParkeerrecht", "houderRechtspersoon", "soortProductgroep", "soortProductsoort" },
        allowSetters = true
    )
    private Set<Parkeervergunning> houderParkeervergunnings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "verstrekkerRechtspersoon")
    @JsonIgnoreProperties(
        value = {
            "heeftZaak",
            "heeftRapportagemoments",
            "heeftTaaks",
            "valtbinnenSector",
            "behandelaarMedewerker",
            "verstrekkerRechtspersoon",
            "heeftKostenplaats",
            "heeftDocument",
            "betreftSubsidieaanvraag",
            "betreftSubsidiebeschikking",
            "aanvragerRechtspersoon",
            "aanvragerMedewerker",
        },
        allowSetters = true
    )
    private Set<Subsidie> verstrekkerSubsidies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectleiderRechtspersoon")
    @JsonIgnoreProperties(value = { "projectleiderRechtspersoon", "heeftSubsidie" }, allowSetters = true)
    private Set<Taak> projectleiderTaaks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftRechtspersoon")
    @JsonIgnoreProperties(value = { "heeftRechtspersoon" }, allowSetters = true)
    private Set<Vastgoedcontract> heeftVastgoedcontracts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rechtspersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresbinnenland() {
        return this.adresbinnenland;
    }

    public Rechtspersoon adresbinnenland(String adresbinnenland) {
        this.setAdresbinnenland(adresbinnenland);
        return this;
    }

    public void setAdresbinnenland(String adresbinnenland) {
        this.adresbinnenland = adresbinnenland;
    }

    public String getAdresbuitenland() {
        return this.adresbuitenland;
    }

    public Rechtspersoon adresbuitenland(String adresbuitenland) {
        this.setAdresbuitenland(adresbuitenland);
        return this;
    }

    public void setAdresbuitenland(String adresbuitenland) {
        this.adresbuitenland = adresbuitenland;
    }

    public String getAdrescorrespondentie() {
        return this.adrescorrespondentie;
    }

    public Rechtspersoon adrescorrespondentie(String adrescorrespondentie) {
        this.setAdrescorrespondentie(adrescorrespondentie);
        return this;
    }

    public void setAdrescorrespondentie(String adrescorrespondentie) {
        this.adrescorrespondentie = adrescorrespondentie;
    }

    public String getEmailadres() {
        return this.emailadres;
    }

    public Rechtspersoon emailadres(String emailadres) {
        this.setEmailadres(emailadres);
        return this;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getFaxnummer() {
        return this.faxnummer;
    }

    public Rechtspersoon faxnummer(String faxnummer) {
        this.setFaxnummer(faxnummer);
        return this;
    }

    public void setFaxnummer(String faxnummer) {
        this.faxnummer = faxnummer;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Rechtspersoon identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getKvknummer() {
        return this.kvknummer;
    }

    public Rechtspersoon kvknummer(String kvknummer) {
        this.setKvknummer(kvknummer);
        return this;
    }

    public void setKvknummer(String kvknummer) {
        this.kvknummer = kvknummer;
    }

    public String getNaam() {
        return this.naam;
    }

    public Rechtspersoon naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getRechtsvorm() {
        return this.rechtsvorm;
    }

    public Rechtspersoon rechtsvorm(String rechtsvorm) {
        this.setRechtsvorm(rechtsvorm);
        return this;
    }

    public void setRechtsvorm(String rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public String getRekeningnummer() {
        return this.rekeningnummer;
    }

    public Rechtspersoon rekeningnummer(String rekeningnummer) {
        this.setRekeningnummer(rekeningnummer);
        return this;
    }

    public void setRekeningnummer(String rekeningnummer) {
        this.rekeningnummer = rekeningnummer;
    }

    public String getTelefoonnummer() {
        return this.telefoonnummer;
    }

    public Rechtspersoon telefoonnummer(String telefoonnummer) {
        this.setTelefoonnummer(telefoonnummer);
        return this;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public Set<Rapportagemoment> getProjectleiderRapportagemoments() {
        return this.projectleiderRapportagemoments;
    }

    public void setProjectleiderRapportagemoments(Set<Rapportagemoment> rapportagemoments) {
        if (this.projectleiderRapportagemoments != null) {
            this.projectleiderRapportagemoments.forEach(i -> i.setProjectleiderRechtspersoon(null));
        }
        if (rapportagemoments != null) {
            rapportagemoments.forEach(i -> i.setProjectleiderRechtspersoon(this));
        }
        this.projectleiderRapportagemoments = rapportagemoments;
    }

    public Rechtspersoon projectleiderRapportagemoments(Set<Rapportagemoment> rapportagemoments) {
        this.setProjectleiderRapportagemoments(rapportagemoments);
        return this;
    }

    public Rechtspersoon addProjectleiderRapportagemoment(Rapportagemoment rapportagemoment) {
        this.projectleiderRapportagemoments.add(rapportagemoment);
        rapportagemoment.setProjectleiderRechtspersoon(this);
        return this;
    }

    public Rechtspersoon removeProjectleiderRapportagemoment(Rapportagemoment rapportagemoment) {
        this.projectleiderRapportagemoments.remove(rapportagemoment);
        rapportagemoment.setProjectleiderRechtspersoon(null);
        return this;
    }

    public Set<Subsidie> getAanvragerSubsidies() {
        return this.aanvragerSubsidies;
    }

    public void setAanvragerSubsidies(Set<Subsidie> subsidies) {
        if (this.aanvragerSubsidies != null) {
            this.aanvragerSubsidies.forEach(i -> i.setAanvragerRechtspersoon(null));
        }
        if (subsidies != null) {
            subsidies.forEach(i -> i.setAanvragerRechtspersoon(this));
        }
        this.aanvragerSubsidies = subsidies;
    }

    public Rechtspersoon aanvragerSubsidies(Set<Subsidie> subsidies) {
        this.setAanvragerSubsidies(subsidies);
        return this;
    }

    public Rechtspersoon addAanvragerSubsidie(Subsidie subsidie) {
        this.aanvragerSubsidies.add(subsidie);
        subsidie.setAanvragerRechtspersoon(this);
        return this;
    }

    public Rechtspersoon removeAanvragerSubsidie(Subsidie subsidie) {
        this.aanvragerSubsidies.remove(subsidie);
        subsidie.setAanvragerRechtspersoon(null);
        return this;
    }

    public Set<Tenaamstelling> getHeeftTenaamstellings() {
        return this.heeftTenaamstellings;
    }

    public void setHeeftTenaamstellings(Set<Tenaamstelling> tenaamstellings) {
        if (this.heeftTenaamstellings != null) {
            this.heeftTenaamstellings.forEach(i -> i.setHeeftRechtspersoon(null));
        }
        if (tenaamstellings != null) {
            tenaamstellings.forEach(i -> i.setHeeftRechtspersoon(this));
        }
        this.heeftTenaamstellings = tenaamstellings;
    }

    public Rechtspersoon heeftTenaamstellings(Set<Tenaamstelling> tenaamstellings) {
        this.setHeeftTenaamstellings(tenaamstellings);
        return this;
    }

    public Rechtspersoon addHeeftTenaamstelling(Tenaamstelling tenaamstelling) {
        this.heeftTenaamstellings.add(tenaamstelling);
        tenaamstelling.setHeeftRechtspersoon(this);
        return this;
    }

    public Rechtspersoon removeHeeftTenaamstelling(Tenaamstelling tenaamstelling) {
        this.heeftTenaamstellings.remove(tenaamstelling);
        tenaamstelling.setHeeftRechtspersoon(null);
        return this;
    }

    public Set<Kadastralemutatie> getBetrokkenenKadastralemutaties() {
        return this.betrokkenenKadastralemutaties;
    }

    public void setBetrokkenenKadastralemutaties(Set<Kadastralemutatie> kadastralemutaties) {
        this.betrokkenenKadastralemutaties = kadastralemutaties;
    }

    public Rechtspersoon betrokkenenKadastralemutaties(Set<Kadastralemutatie> kadastralemutaties) {
        this.setBetrokkenenKadastralemutaties(kadastralemutaties);
        return this;
    }

    public Rechtspersoon addBetrokkenenKadastralemutatie(Kadastralemutatie kadastralemutatie) {
        this.betrokkenenKadastralemutaties.add(kadastralemutatie);
        return this;
    }

    public Rechtspersoon removeBetrokkenenKadastralemutatie(Kadastralemutatie kadastralemutatie) {
        this.betrokkenenKadastralemutaties.remove(kadastralemutatie);
        return this;
    }

    public Indiener getIsIndiener() {
        return this.isIndiener;
    }

    public void setIsIndiener(Indiener indiener) {
        if (this.isIndiener != null) {
            this.isIndiener.setIsRechtspersoon(null);
        }
        if (indiener != null) {
            indiener.setIsRechtspersoon(this);
        }
        this.isIndiener = indiener;
    }

    public Rechtspersoon isIndiener(Indiener indiener) {
        this.setIsIndiener(indiener);
        return this;
    }

    public Set<Parkeervergunning> getHouderParkeervergunnings() {
        return this.houderParkeervergunnings;
    }

    public void setHouderParkeervergunnings(Set<Parkeervergunning> parkeervergunnings) {
        if (this.houderParkeervergunnings != null) {
            this.houderParkeervergunnings.forEach(i -> i.setHouderRechtspersoon(null));
        }
        if (parkeervergunnings != null) {
            parkeervergunnings.forEach(i -> i.setHouderRechtspersoon(this));
        }
        this.houderParkeervergunnings = parkeervergunnings;
    }

    public Rechtspersoon houderParkeervergunnings(Set<Parkeervergunning> parkeervergunnings) {
        this.setHouderParkeervergunnings(parkeervergunnings);
        return this;
    }

    public Rechtspersoon addHouderParkeervergunning(Parkeervergunning parkeervergunning) {
        this.houderParkeervergunnings.add(parkeervergunning);
        parkeervergunning.setHouderRechtspersoon(this);
        return this;
    }

    public Rechtspersoon removeHouderParkeervergunning(Parkeervergunning parkeervergunning) {
        this.houderParkeervergunnings.remove(parkeervergunning);
        parkeervergunning.setHouderRechtspersoon(null);
        return this;
    }

    public Set<Subsidie> getVerstrekkerSubsidies() {
        return this.verstrekkerSubsidies;
    }

    public void setVerstrekkerSubsidies(Set<Subsidie> subsidies) {
        if (this.verstrekkerSubsidies != null) {
            this.verstrekkerSubsidies.forEach(i -> i.setVerstrekkerRechtspersoon(null));
        }
        if (subsidies != null) {
            subsidies.forEach(i -> i.setVerstrekkerRechtspersoon(this));
        }
        this.verstrekkerSubsidies = subsidies;
    }

    public Rechtspersoon verstrekkerSubsidies(Set<Subsidie> subsidies) {
        this.setVerstrekkerSubsidies(subsidies);
        return this;
    }

    public Rechtspersoon addVerstrekkerSubsidie(Subsidie subsidie) {
        this.verstrekkerSubsidies.add(subsidie);
        subsidie.setVerstrekkerRechtspersoon(this);
        return this;
    }

    public Rechtspersoon removeVerstrekkerSubsidie(Subsidie subsidie) {
        this.verstrekkerSubsidies.remove(subsidie);
        subsidie.setVerstrekkerRechtspersoon(null);
        return this;
    }

    public Set<Taak> getProjectleiderTaaks() {
        return this.projectleiderTaaks;
    }

    public void setProjectleiderTaaks(Set<Taak> taaks) {
        if (this.projectleiderTaaks != null) {
            this.projectleiderTaaks.forEach(i -> i.setProjectleiderRechtspersoon(null));
        }
        if (taaks != null) {
            taaks.forEach(i -> i.setProjectleiderRechtspersoon(this));
        }
        this.projectleiderTaaks = taaks;
    }

    public Rechtspersoon projectleiderTaaks(Set<Taak> taaks) {
        this.setProjectleiderTaaks(taaks);
        return this;
    }

    public Rechtspersoon addProjectleiderTaak(Taak taak) {
        this.projectleiderTaaks.add(taak);
        taak.setProjectleiderRechtspersoon(this);
        return this;
    }

    public Rechtspersoon removeProjectleiderTaak(Taak taak) {
        this.projectleiderTaaks.remove(taak);
        taak.setProjectleiderRechtspersoon(null);
        return this;
    }

    public Set<Vastgoedcontract> getHeeftVastgoedcontracts() {
        return this.heeftVastgoedcontracts;
    }

    public void setHeeftVastgoedcontracts(Set<Vastgoedcontract> vastgoedcontracts) {
        if (this.heeftVastgoedcontracts != null) {
            this.heeftVastgoedcontracts.forEach(i -> i.setHeeftRechtspersoon(null));
        }
        if (vastgoedcontracts != null) {
            vastgoedcontracts.forEach(i -> i.setHeeftRechtspersoon(this));
        }
        this.heeftVastgoedcontracts = vastgoedcontracts;
    }

    public Rechtspersoon heeftVastgoedcontracts(Set<Vastgoedcontract> vastgoedcontracts) {
        this.setHeeftVastgoedcontracts(vastgoedcontracts);
        return this;
    }

    public Rechtspersoon addHeeftVastgoedcontract(Vastgoedcontract vastgoedcontract) {
        this.heeftVastgoedcontracts.add(vastgoedcontract);
        vastgoedcontract.setHeeftRechtspersoon(this);
        return this;
    }

    public Rechtspersoon removeHeeftVastgoedcontract(Vastgoedcontract vastgoedcontract) {
        this.heeftVastgoedcontracts.remove(vastgoedcontract);
        vastgoedcontract.setHeeftRechtspersoon(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rechtspersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Rechtspersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rechtspersoon{" +
            "id=" + getId() +
            ", adresbinnenland='" + getAdresbinnenland() + "'" +
            ", adresbuitenland='" + getAdresbuitenland() + "'" +
            ", adrescorrespondentie='" + getAdrescorrespondentie() + "'" +
            ", emailadres='" + getEmailadres() + "'" +
            ", faxnummer='" + getFaxnummer() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", kvknummer='" + getKvknummer() + "'" +
            ", naam='" + getNaam() + "'" +
            ", rechtsvorm='" + getRechtsvorm() + "'" +
            ", rekeningnummer='" + getRekeningnummer() + "'" +
            ", telefoonnummer='" + getTelefoonnummer() + "'" +
            "}";
    }
}
