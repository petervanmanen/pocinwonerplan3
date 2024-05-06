package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sportvereniging.
 */
@Entity
@Table(name = "sportvereniging")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sportvereniging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalnormteams")
    private String aantalnormteams;

    @Column(name = "adres")
    private String adres;

    @Column(name = "binnensport")
    private Boolean binnensport;

    @Column(name = "buitensport")
    private Boolean buitensport;

    @Column(name = "email")
    private String email;

    @Column(name = "ledenaantal")
    private String ledenaantal;

    @Column(name = "naam")
    private String naam;

    @Column(name = "typesport")
    private String typesport;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_sportvereniging__oefentuit_sport",
        joinColumns = @JoinColumn(name = "sportvereniging_id"),
        inverseJoinColumns = @JoinColumn(name = "oefentuit_sport_id")
    )
    @JsonIgnoreProperties(value = { "oefentuitSportverenigings" }, allowSetters = true)
    private Set<Sport> oefentuitSports = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_sportvereniging__gebruikt_sportlocatie",
        joinColumns = @JoinColumn(name = "sportvereniging_id"),
        inverseJoinColumns = @JoinColumn(name = "gebruikt_sportlocatie_id")
    )
    @JsonIgnoreProperties(value = { "gebruiktSchools", "gebruiktSportverenigings" }, allowSetters = true)
    private Set<Sportlocatie> gebruiktSportlocaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sportvereniging id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalnormteams() {
        return this.aantalnormteams;
    }

    public Sportvereniging aantalnormteams(String aantalnormteams) {
        this.setAantalnormteams(aantalnormteams);
        return this;
    }

    public void setAantalnormteams(String aantalnormteams) {
        this.aantalnormteams = aantalnormteams;
    }

    public String getAdres() {
        return this.adres;
    }

    public Sportvereniging adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Boolean getBinnensport() {
        return this.binnensport;
    }

    public Sportvereniging binnensport(Boolean binnensport) {
        this.setBinnensport(binnensport);
        return this;
    }

    public void setBinnensport(Boolean binnensport) {
        this.binnensport = binnensport;
    }

    public Boolean getBuitensport() {
        return this.buitensport;
    }

    public Sportvereniging buitensport(Boolean buitensport) {
        this.setBuitensport(buitensport);
        return this;
    }

    public void setBuitensport(Boolean buitensport) {
        this.buitensport = buitensport;
    }

    public String getEmail() {
        return this.email;
    }

    public Sportvereniging email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLedenaantal() {
        return this.ledenaantal;
    }

    public Sportvereniging ledenaantal(String ledenaantal) {
        this.setLedenaantal(ledenaantal);
        return this;
    }

    public void setLedenaantal(String ledenaantal) {
        this.ledenaantal = ledenaantal;
    }

    public String getNaam() {
        return this.naam;
    }

    public Sportvereniging naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getTypesport() {
        return this.typesport;
    }

    public Sportvereniging typesport(String typesport) {
        this.setTypesport(typesport);
        return this;
    }

    public void setTypesport(String typesport) {
        this.typesport = typesport;
    }

    public Set<Sport> getOefentuitSports() {
        return this.oefentuitSports;
    }

    public void setOefentuitSports(Set<Sport> sports) {
        this.oefentuitSports = sports;
    }

    public Sportvereniging oefentuitSports(Set<Sport> sports) {
        this.setOefentuitSports(sports);
        return this;
    }

    public Sportvereniging addOefentuitSport(Sport sport) {
        this.oefentuitSports.add(sport);
        return this;
    }

    public Sportvereniging removeOefentuitSport(Sport sport) {
        this.oefentuitSports.remove(sport);
        return this;
    }

    public Set<Sportlocatie> getGebruiktSportlocaties() {
        return this.gebruiktSportlocaties;
    }

    public void setGebruiktSportlocaties(Set<Sportlocatie> sportlocaties) {
        this.gebruiktSportlocaties = sportlocaties;
    }

    public Sportvereniging gebruiktSportlocaties(Set<Sportlocatie> sportlocaties) {
        this.setGebruiktSportlocaties(sportlocaties);
        return this;
    }

    public Sportvereniging addGebruiktSportlocatie(Sportlocatie sportlocatie) {
        this.gebruiktSportlocaties.add(sportlocatie);
        return this;
    }

    public Sportvereniging removeGebruiktSportlocatie(Sportlocatie sportlocatie) {
        this.gebruiktSportlocaties.remove(sportlocatie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sportvereniging)) {
            return false;
        }
        return getId() != null && getId().equals(((Sportvereniging) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sportvereniging{" +
            "id=" + getId() +
            ", aantalnormteams='" + getAantalnormteams() + "'" +
            ", adres='" + getAdres() + "'" +
            ", binnensport='" + getBinnensport() + "'" +
            ", buitensport='" + getBuitensport() + "'" +
            ", email='" + getEmail() + "'" +
            ", ledenaantal='" + getLedenaantal() + "'" +
            ", naam='" + getNaam() + "'" +
            ", typesport='" + getTypesport() + "'" +
            "}";
    }
}
