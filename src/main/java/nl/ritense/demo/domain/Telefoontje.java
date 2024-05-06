package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Telefoontje.
 */
@Entity
@Table(name = "telefoontje")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Telefoontje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "afhandeltijdnagesprek")
    private String afhandeltijdnagesprek;

    @Column(name = "deltaisdnconnectie")
    private String deltaisdnconnectie;

    @Column(name = "eindtijd")
    private String eindtijd;

    @Column(name = "starttijd")
    private String starttijd;

    @Column(name = "totaleonholdtijd")
    private String totaleonholdtijd;

    @Column(name = "totalespreektijd")
    private String totalespreektijd;

    @Column(name = "totalewachttijd")
    private String totalewachttijd;

    @Column(name = "totlatetijdsduur")
    private String totlatetijdsduur;

    @Size(max = 20)
    @Column(name = "trackid", length = 20)
    private String trackid;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mondtuitinTelefoontje")
    @JsonIgnoreProperties(
        value = {
            "heeftklantcontactenBetrokkene",
            "heeftbetrekkingopZaak",
            "isgevoerddoorMedewerker",
            "mondtuitinBalieafspraak",
            "heeftTelefoononderwerp",
            "mondtuitinTelefoontje",
        },
        allowSetters = true
    )
    private Set<Klantcontact> mondtuitinKlantcontacts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftTelefoontjes" }, allowSetters = true)
    private Telefoonstatus heeftTelefoonstatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftKlantcontacts", "heeftTelefoontjes" }, allowSetters = true)
    private Telefoononderwerp heeftTelefoononderwerp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Telefoontje id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfhandeltijdnagesprek() {
        return this.afhandeltijdnagesprek;
    }

    public Telefoontje afhandeltijdnagesprek(String afhandeltijdnagesprek) {
        this.setAfhandeltijdnagesprek(afhandeltijdnagesprek);
        return this;
    }

    public void setAfhandeltijdnagesprek(String afhandeltijdnagesprek) {
        this.afhandeltijdnagesprek = afhandeltijdnagesprek;
    }

    public String getDeltaisdnconnectie() {
        return this.deltaisdnconnectie;
    }

    public Telefoontje deltaisdnconnectie(String deltaisdnconnectie) {
        this.setDeltaisdnconnectie(deltaisdnconnectie);
        return this;
    }

    public void setDeltaisdnconnectie(String deltaisdnconnectie) {
        this.deltaisdnconnectie = deltaisdnconnectie;
    }

    public String getEindtijd() {
        return this.eindtijd;
    }

    public Telefoontje eindtijd(String eindtijd) {
        this.setEindtijd(eindtijd);
        return this;
    }

    public void setEindtijd(String eindtijd) {
        this.eindtijd = eindtijd;
    }

    public String getStarttijd() {
        return this.starttijd;
    }

    public Telefoontje starttijd(String starttijd) {
        this.setStarttijd(starttijd);
        return this;
    }

    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    public String getTotaleonholdtijd() {
        return this.totaleonholdtijd;
    }

    public Telefoontje totaleonholdtijd(String totaleonholdtijd) {
        this.setTotaleonholdtijd(totaleonholdtijd);
        return this;
    }

    public void setTotaleonholdtijd(String totaleonholdtijd) {
        this.totaleonholdtijd = totaleonholdtijd;
    }

    public String getTotalespreektijd() {
        return this.totalespreektijd;
    }

    public Telefoontje totalespreektijd(String totalespreektijd) {
        this.setTotalespreektijd(totalespreektijd);
        return this;
    }

    public void setTotalespreektijd(String totalespreektijd) {
        this.totalespreektijd = totalespreektijd;
    }

    public String getTotalewachttijd() {
        return this.totalewachttijd;
    }

    public Telefoontje totalewachttijd(String totalewachttijd) {
        this.setTotalewachttijd(totalewachttijd);
        return this;
    }

    public void setTotalewachttijd(String totalewachttijd) {
        this.totalewachttijd = totalewachttijd;
    }

    public String getTotlatetijdsduur() {
        return this.totlatetijdsduur;
    }

    public Telefoontje totlatetijdsduur(String totlatetijdsduur) {
        this.setTotlatetijdsduur(totlatetijdsduur);
        return this;
    }

    public void setTotlatetijdsduur(String totlatetijdsduur) {
        this.totlatetijdsduur = totlatetijdsduur;
    }

    public String getTrackid() {
        return this.trackid;
    }

    public Telefoontje trackid(String trackid) {
        this.setTrackid(trackid);
        return this;
    }

    public void setTrackid(String trackid) {
        this.trackid = trackid;
    }

    public Set<Klantcontact> getMondtuitinKlantcontacts() {
        return this.mondtuitinKlantcontacts;
    }

    public void setMondtuitinKlantcontacts(Set<Klantcontact> klantcontacts) {
        if (this.mondtuitinKlantcontacts != null) {
            this.mondtuitinKlantcontacts.forEach(i -> i.setMondtuitinTelefoontje(null));
        }
        if (klantcontacts != null) {
            klantcontacts.forEach(i -> i.setMondtuitinTelefoontje(this));
        }
        this.mondtuitinKlantcontacts = klantcontacts;
    }

    public Telefoontje mondtuitinKlantcontacts(Set<Klantcontact> klantcontacts) {
        this.setMondtuitinKlantcontacts(klantcontacts);
        return this;
    }

    public Telefoontje addMondtuitinKlantcontact(Klantcontact klantcontact) {
        this.mondtuitinKlantcontacts.add(klantcontact);
        klantcontact.setMondtuitinTelefoontje(this);
        return this;
    }

    public Telefoontje removeMondtuitinKlantcontact(Klantcontact klantcontact) {
        this.mondtuitinKlantcontacts.remove(klantcontact);
        klantcontact.setMondtuitinTelefoontje(null);
        return this;
    }

    public Telefoonstatus getHeeftTelefoonstatus() {
        return this.heeftTelefoonstatus;
    }

    public void setHeeftTelefoonstatus(Telefoonstatus telefoonstatus) {
        this.heeftTelefoonstatus = telefoonstatus;
    }

    public Telefoontje heeftTelefoonstatus(Telefoonstatus telefoonstatus) {
        this.setHeeftTelefoonstatus(telefoonstatus);
        return this;
    }

    public Telefoononderwerp getHeeftTelefoononderwerp() {
        return this.heeftTelefoononderwerp;
    }

    public void setHeeftTelefoononderwerp(Telefoononderwerp telefoononderwerp) {
        this.heeftTelefoononderwerp = telefoononderwerp;
    }

    public Telefoontje heeftTelefoononderwerp(Telefoononderwerp telefoononderwerp) {
        this.setHeeftTelefoononderwerp(telefoononderwerp);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Telefoontje)) {
            return false;
        }
        return getId() != null && getId().equals(((Telefoontje) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Telefoontje{" +
            "id=" + getId() +
            ", afhandeltijdnagesprek='" + getAfhandeltijdnagesprek() + "'" +
            ", deltaisdnconnectie='" + getDeltaisdnconnectie() + "'" +
            ", eindtijd='" + getEindtijd() + "'" +
            ", starttijd='" + getStarttijd() + "'" +
            ", totaleonholdtijd='" + getTotaleonholdtijd() + "'" +
            ", totalespreektijd='" + getTotalespreektijd() + "'" +
            ", totalewachttijd='" + getTotalewachttijd() + "'" +
            ", totlatetijdsduur='" + getTotlatetijdsduur() + "'" +
            ", trackid='" + getTrackid() + "'" +
            "}";
    }
}
