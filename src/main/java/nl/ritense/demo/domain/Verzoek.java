package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Verzoek.
 */
@Entity
@Table(name = "verzoek")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verzoek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "akkoordverklaring")
    private Boolean akkoordverklaring;

    @Column(name = "ambtshalve")
    private Boolean ambtshalve;

    @Column(name = "datumindiening")
    private LocalDate datumindiening;

    @Column(name = "doel")
    private String doel;

    @Column(name = "naam")
    private String naam;

    @Column(name = "referentieaanvrager")
    private String referentieaanvrager;

    @Column(name = "toelichtinglateraantelevereninformatie")
    private String toelichtinglateraantelevereninformatie;

    @Column(name = "toelichtingnietaantelevereninformatie")
    private String toelichtingnietaantelevereninformatie;

    @Column(name = "toelichtingverzoek")
    private String toelichtingverzoek;

    @Column(name = "type")
    private String type;

    @Column(name = "verzoeknummer")
    private String verzoeknummer;

    @Column(name = "volgnummer")
    private String volgnummer;

    @JsonIgnoreProperties(
        value = {
            "heeftproductProducttype",
            "heeftKlantbeoordeling",
            "heeftHeffing",
            "heeftbetalingBetalings",
            "heeftStatuses",
            "betreftProject",
            "isvanZaaktype",
            "kentDocuments",
            "afhandelendmedewerkerMedewerkers",
            "leidttotVerzoek",
            "heeftSubsidie",
            "betreftAanbesteding",
            "heeftbetrekkingopBalieafspraaks",
            "isuitkomstvanBesluits",
            "heeftbetrekkingopKlantcontacts",
            "heeftGrondslags",
            "uitgevoerdbinnenBedrijfsproces",
            "oefentuitBetrokkenes",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Zaak leidttotZaak;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bevatVerzoek")
    @JsonIgnoreProperties(value = { "gedefinieerddoorProjectactiviteit", "bevatVerzoek" }, allowSetters = true)
    private Set<Specificatie> bevatSpecificaties = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "leidttotZaak",
            "bevatSpecificaties",
            "betrefteerderverzoekVerzoek",
            "betreftProjectactiviteits",
            "betreftProjectlocaties",
            "betreftActiviteits",
            "betreftLocaties",
            "heeftalsverantwoordelijkeInitiatiefnemer",
            "betrefteerderverzoekVerzoek2s",
        },
        allowSetters = true
    )
    private Verzoek betrefteerderverzoekVerzoek;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_verzoek__betreft_projectactiviteit",
        joinColumns = @JoinColumn(name = "verzoek_id"),
        inverseJoinColumns = @JoinColumn(name = "betreft_projectactiviteit_id")
    )
    @JsonIgnoreProperties(value = { "heeftProject", "gedefinieerddoorSpecificaties", "betreftVerzoeks" }, allowSetters = true)
    private Set<Projectactiviteit> betreftProjectactiviteits = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_verzoek__betreft_projectlocatie",
        joinColumns = @JoinColumn(name = "verzoek_id"),
        inverseJoinColumns = @JoinColumn(name = "betreft_projectlocatie_id")
    )
    @JsonIgnoreProperties(value = { "betreftLocatie", "heeftProject", "betreftVerzoeks" }, allowSetters = true)
    private Set<Projectlocatie> betreftProjectlocaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_verzoek__betreft_activiteit",
        joinColumns = @JoinColumn(name = "verzoek_id"),
        inverseJoinColumns = @JoinColumn(name = "betreft_activiteit_id")
    )
    @JsonIgnoreProperties(
        value = {
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    private Set<Activiteit> betreftActiviteits = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_verzoek__betreft_locatie",
        joinColumns = @JoinColumn(name = "verzoek_id"),
        inverseJoinColumns = @JoinColumn(name = "betreft_locatie_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftContainers",
            "betreftMeldings",
            "gestoptopOphaalmoments",
            "betreftProjectlocaties",
            "heeftlocatiePuts",
            "wordtbegrensddoorProjects",
            "betreftVerzoeks",
            "werkingsgebiedRegelteksts",
            "isverbondenmetActiviteits",
            "verwijstnaarGebiedsaanwijzings",
            "geldtvoorNormwaardes",
        },
        allowSetters = true
    )
    private Set<Locatie> betreftLocaties = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "heeftalsverantwoordelijkeVerzoeks" }, allowSetters = true)
    private Initiatiefnemer heeftalsverantwoordelijkeInitiatiefnemer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betrefteerderverzoekVerzoek")
    @JsonIgnoreProperties(
        value = {
            "leidttotZaak",
            "bevatSpecificaties",
            "betrefteerderverzoekVerzoek",
            "betreftProjectactiviteits",
            "betreftProjectlocaties",
            "betreftActiviteits",
            "betreftLocaties",
            "heeftalsverantwoordelijkeInitiatiefnemer",
            "betrefteerderverzoekVerzoek2s",
        },
        allowSetters = true
    )
    private Set<Verzoek> betrefteerderverzoekVerzoek2s = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verzoek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAkkoordverklaring() {
        return this.akkoordverklaring;
    }

    public Verzoek akkoordverklaring(Boolean akkoordverklaring) {
        this.setAkkoordverklaring(akkoordverklaring);
        return this;
    }

    public void setAkkoordverklaring(Boolean akkoordverklaring) {
        this.akkoordverklaring = akkoordverklaring;
    }

    public Boolean getAmbtshalve() {
        return this.ambtshalve;
    }

    public Verzoek ambtshalve(Boolean ambtshalve) {
        this.setAmbtshalve(ambtshalve);
        return this;
    }

    public void setAmbtshalve(Boolean ambtshalve) {
        this.ambtshalve = ambtshalve;
    }

    public LocalDate getDatumindiening() {
        return this.datumindiening;
    }

    public Verzoek datumindiening(LocalDate datumindiening) {
        this.setDatumindiening(datumindiening);
        return this;
    }

    public void setDatumindiening(LocalDate datumindiening) {
        this.datumindiening = datumindiening;
    }

    public String getDoel() {
        return this.doel;
    }

    public Verzoek doel(String doel) {
        this.setDoel(doel);
        return this;
    }

    public void setDoel(String doel) {
        this.doel = doel;
    }

    public String getNaam() {
        return this.naam;
    }

    public Verzoek naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getReferentieaanvrager() {
        return this.referentieaanvrager;
    }

    public Verzoek referentieaanvrager(String referentieaanvrager) {
        this.setReferentieaanvrager(referentieaanvrager);
        return this;
    }

    public void setReferentieaanvrager(String referentieaanvrager) {
        this.referentieaanvrager = referentieaanvrager;
    }

    public String getToelichtinglateraantelevereninformatie() {
        return this.toelichtinglateraantelevereninformatie;
    }

    public Verzoek toelichtinglateraantelevereninformatie(String toelichtinglateraantelevereninformatie) {
        this.setToelichtinglateraantelevereninformatie(toelichtinglateraantelevereninformatie);
        return this;
    }

    public void setToelichtinglateraantelevereninformatie(String toelichtinglateraantelevereninformatie) {
        this.toelichtinglateraantelevereninformatie = toelichtinglateraantelevereninformatie;
    }

    public String getToelichtingnietaantelevereninformatie() {
        return this.toelichtingnietaantelevereninformatie;
    }

    public Verzoek toelichtingnietaantelevereninformatie(String toelichtingnietaantelevereninformatie) {
        this.setToelichtingnietaantelevereninformatie(toelichtingnietaantelevereninformatie);
        return this;
    }

    public void setToelichtingnietaantelevereninformatie(String toelichtingnietaantelevereninformatie) {
        this.toelichtingnietaantelevereninformatie = toelichtingnietaantelevereninformatie;
    }

    public String getToelichtingverzoek() {
        return this.toelichtingverzoek;
    }

    public Verzoek toelichtingverzoek(String toelichtingverzoek) {
        this.setToelichtingverzoek(toelichtingverzoek);
        return this;
    }

    public void setToelichtingverzoek(String toelichtingverzoek) {
        this.toelichtingverzoek = toelichtingverzoek;
    }

    public String getType() {
        return this.type;
    }

    public Verzoek type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVerzoeknummer() {
        return this.verzoeknummer;
    }

    public Verzoek verzoeknummer(String verzoeknummer) {
        this.setVerzoeknummer(verzoeknummer);
        return this;
    }

    public void setVerzoeknummer(String verzoeknummer) {
        this.verzoeknummer = verzoeknummer;
    }

    public String getVolgnummer() {
        return this.volgnummer;
    }

    public Verzoek volgnummer(String volgnummer) {
        this.setVolgnummer(volgnummer);
        return this;
    }

    public void setVolgnummer(String volgnummer) {
        this.volgnummer = volgnummer;
    }

    public Zaak getLeidttotZaak() {
        return this.leidttotZaak;
    }

    public void setLeidttotZaak(Zaak zaak) {
        this.leidttotZaak = zaak;
    }

    public Verzoek leidttotZaak(Zaak zaak) {
        this.setLeidttotZaak(zaak);
        return this;
    }

    public Set<Specificatie> getBevatSpecificaties() {
        return this.bevatSpecificaties;
    }

    public void setBevatSpecificaties(Set<Specificatie> specificaties) {
        if (this.bevatSpecificaties != null) {
            this.bevatSpecificaties.forEach(i -> i.setBevatVerzoek(null));
        }
        if (specificaties != null) {
            specificaties.forEach(i -> i.setBevatVerzoek(this));
        }
        this.bevatSpecificaties = specificaties;
    }

    public Verzoek bevatSpecificaties(Set<Specificatie> specificaties) {
        this.setBevatSpecificaties(specificaties);
        return this;
    }

    public Verzoek addBevatSpecificatie(Specificatie specificatie) {
        this.bevatSpecificaties.add(specificatie);
        specificatie.setBevatVerzoek(this);
        return this;
    }

    public Verzoek removeBevatSpecificatie(Specificatie specificatie) {
        this.bevatSpecificaties.remove(specificatie);
        specificatie.setBevatVerzoek(null);
        return this;
    }

    public Verzoek getBetrefteerderverzoekVerzoek() {
        return this.betrefteerderverzoekVerzoek;
    }

    public void setBetrefteerderverzoekVerzoek(Verzoek verzoek) {
        this.betrefteerderverzoekVerzoek = verzoek;
    }

    public Verzoek betrefteerderverzoekVerzoek(Verzoek verzoek) {
        this.setBetrefteerderverzoekVerzoek(verzoek);
        return this;
    }

    public Set<Projectactiviteit> getBetreftProjectactiviteits() {
        return this.betreftProjectactiviteits;
    }

    public void setBetreftProjectactiviteits(Set<Projectactiviteit> projectactiviteits) {
        this.betreftProjectactiviteits = projectactiviteits;
    }

    public Verzoek betreftProjectactiviteits(Set<Projectactiviteit> projectactiviteits) {
        this.setBetreftProjectactiviteits(projectactiviteits);
        return this;
    }

    public Verzoek addBetreftProjectactiviteit(Projectactiviteit projectactiviteit) {
        this.betreftProjectactiviteits.add(projectactiviteit);
        return this;
    }

    public Verzoek removeBetreftProjectactiviteit(Projectactiviteit projectactiviteit) {
        this.betreftProjectactiviteits.remove(projectactiviteit);
        return this;
    }

    public Set<Projectlocatie> getBetreftProjectlocaties() {
        return this.betreftProjectlocaties;
    }

    public void setBetreftProjectlocaties(Set<Projectlocatie> projectlocaties) {
        this.betreftProjectlocaties = projectlocaties;
    }

    public Verzoek betreftProjectlocaties(Set<Projectlocatie> projectlocaties) {
        this.setBetreftProjectlocaties(projectlocaties);
        return this;
    }

    public Verzoek addBetreftProjectlocatie(Projectlocatie projectlocatie) {
        this.betreftProjectlocaties.add(projectlocatie);
        return this;
    }

    public Verzoek removeBetreftProjectlocatie(Projectlocatie projectlocatie) {
        this.betreftProjectlocaties.remove(projectlocatie);
        return this;
    }

    public Set<Activiteit> getBetreftActiviteits() {
        return this.betreftActiviteits;
    }

    public void setBetreftActiviteits(Set<Activiteit> activiteits) {
        this.betreftActiviteits = activiteits;
    }

    public Verzoek betreftActiviteits(Set<Activiteit> activiteits) {
        this.setBetreftActiviteits(activiteits);
        return this;
    }

    public Verzoek addBetreftActiviteit(Activiteit activiteit) {
        this.betreftActiviteits.add(activiteit);
        return this;
    }

    public Verzoek removeBetreftActiviteit(Activiteit activiteit) {
        this.betreftActiviteits.remove(activiteit);
        return this;
    }

    public Set<Locatie> getBetreftLocaties() {
        return this.betreftLocaties;
    }

    public void setBetreftLocaties(Set<Locatie> locaties) {
        this.betreftLocaties = locaties;
    }

    public Verzoek betreftLocaties(Set<Locatie> locaties) {
        this.setBetreftLocaties(locaties);
        return this;
    }

    public Verzoek addBetreftLocatie(Locatie locatie) {
        this.betreftLocaties.add(locatie);
        return this;
    }

    public Verzoek removeBetreftLocatie(Locatie locatie) {
        this.betreftLocaties.remove(locatie);
        return this;
    }

    public Initiatiefnemer getHeeftalsverantwoordelijkeInitiatiefnemer() {
        return this.heeftalsverantwoordelijkeInitiatiefnemer;
    }

    public void setHeeftalsverantwoordelijkeInitiatiefnemer(Initiatiefnemer initiatiefnemer) {
        this.heeftalsverantwoordelijkeInitiatiefnemer = initiatiefnemer;
    }

    public Verzoek heeftalsverantwoordelijkeInitiatiefnemer(Initiatiefnemer initiatiefnemer) {
        this.setHeeftalsverantwoordelijkeInitiatiefnemer(initiatiefnemer);
        return this;
    }

    public Set<Verzoek> getBetrefteerderverzoekVerzoek2s() {
        return this.betrefteerderverzoekVerzoek2s;
    }

    public void setBetrefteerderverzoekVerzoek2s(Set<Verzoek> verzoeks) {
        if (this.betrefteerderverzoekVerzoek2s != null) {
            this.betrefteerderverzoekVerzoek2s.forEach(i -> i.setBetrefteerderverzoekVerzoek(null));
        }
        if (verzoeks != null) {
            verzoeks.forEach(i -> i.setBetrefteerderverzoekVerzoek(this));
        }
        this.betrefteerderverzoekVerzoek2s = verzoeks;
    }

    public Verzoek betrefteerderverzoekVerzoek2s(Set<Verzoek> verzoeks) {
        this.setBetrefteerderverzoekVerzoek2s(verzoeks);
        return this;
    }

    public Verzoek addBetrefteerderverzoekVerzoek2(Verzoek verzoek) {
        this.betrefteerderverzoekVerzoek2s.add(verzoek);
        verzoek.setBetrefteerderverzoekVerzoek(this);
        return this;
    }

    public Verzoek removeBetrefteerderverzoekVerzoek2(Verzoek verzoek) {
        this.betrefteerderverzoekVerzoek2s.remove(verzoek);
        verzoek.setBetrefteerderverzoekVerzoek(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verzoek)) {
            return false;
        }
        return getId() != null && getId().equals(((Verzoek) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verzoek{" +
            "id=" + getId() +
            ", akkoordverklaring='" + getAkkoordverklaring() + "'" +
            ", ambtshalve='" + getAmbtshalve() + "'" +
            ", datumindiening='" + getDatumindiening() + "'" +
            ", doel='" + getDoel() + "'" +
            ", naam='" + getNaam() + "'" +
            ", referentieaanvrager='" + getReferentieaanvrager() + "'" +
            ", toelichtinglateraantelevereninformatie='" + getToelichtinglateraantelevereninformatie() + "'" +
            ", toelichtingnietaantelevereninformatie='" + getToelichtingnietaantelevereninformatie() + "'" +
            ", toelichtingverzoek='" + getToelichtingverzoek() + "'" +
            ", type='" + getType() + "'" +
            ", verzoeknummer='" + getVerzoeknummer() + "'" +
            ", volgnummer='" + getVolgnummer() + "'" +
            "}";
    }
}
