package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Server.
 */
@Entity
@Table(name = "server")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "actief")
    private Boolean actief;

    @Column(name = "ipadres")
    private String ipadres;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "organisatie")
    private String organisatie;

    @Column(name = "serienummer")
    private String serienummer;

    @Size(max = 20)
    @Column(name = "serverid", length = 20)
    private String serverid;

    @Column(name = "servertype")
    private String servertype;

    @Column(name = "vlan")
    private String vlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftContracts",
            "leverdeprestatieLeverings",
            "voertwerkuitconformWerkbons",
            "contractantContracts",
            "heeftInschrijvings",
            "biedtaanKandidaats",
            "heeftKwalificaties",
            "gekwalificeerdCategories",
            "leverancierProducts",
            "ingedienddoorDeclaraties",
            "levertvoorzieningToewijzings",
            "heeftTariefs",
            "uitvoerderMeldings",
            "heeftleverancierApplicaties",
            "heeftleverancierServers",
            "crediteurFactuurs",
            "verplichtingaanInkooporders",
            "gerichtaanUitnodigings",
            "geleverdviaMedewerkers",
            "gerichtaanOfferteaanvraags",
            "ingedienddoorOffertes",
        },
        allowSetters = true
    )
    private Leverancier heeftleverancierLeverancier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servervandatabaseServer")
    @JsonIgnoreProperties(value = { "servervandatabaseServer" }, allowSetters = true)
    private Set<Database> servervandatabaseDatabases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Server id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActief() {
        return this.actief;
    }

    public Server actief(Boolean actief) {
        this.setActief(actief);
        return this;
    }

    public void setActief(Boolean actief) {
        this.actief = actief;
    }

    public String getIpadres() {
        return this.ipadres;
    }

    public Server ipadres(String ipadres) {
        this.setIpadres(ipadres);
        return this;
    }

    public void setIpadres(String ipadres) {
        this.ipadres = ipadres;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Server locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getOrganisatie() {
        return this.organisatie;
    }

    public Server organisatie(String organisatie) {
        this.setOrganisatie(organisatie);
        return this;
    }

    public void setOrganisatie(String organisatie) {
        this.organisatie = organisatie;
    }

    public String getSerienummer() {
        return this.serienummer;
    }

    public Server serienummer(String serienummer) {
        this.setSerienummer(serienummer);
        return this;
    }

    public void setSerienummer(String serienummer) {
        this.serienummer = serienummer;
    }

    public String getServerid() {
        return this.serverid;
    }

    public Server serverid(String serverid) {
        this.setServerid(serverid);
        return this;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    public String getServertype() {
        return this.servertype;
    }

    public Server servertype(String servertype) {
        this.setServertype(servertype);
        return this;
    }

    public void setServertype(String servertype) {
        this.servertype = servertype;
    }

    public String getVlan() {
        return this.vlan;
    }

    public Server vlan(String vlan) {
        this.setVlan(vlan);
        return this;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    public Leverancier getHeeftleverancierLeverancier() {
        return this.heeftleverancierLeverancier;
    }

    public void setHeeftleverancierLeverancier(Leverancier leverancier) {
        this.heeftleverancierLeverancier = leverancier;
    }

    public Server heeftleverancierLeverancier(Leverancier leverancier) {
        this.setHeeftleverancierLeverancier(leverancier);
        return this;
    }

    public Set<Database> getServervandatabaseDatabases() {
        return this.servervandatabaseDatabases;
    }

    public void setServervandatabaseDatabases(Set<Database> databases) {
        if (this.servervandatabaseDatabases != null) {
            this.servervandatabaseDatabases.forEach(i -> i.setServervandatabaseServer(null));
        }
        if (databases != null) {
            databases.forEach(i -> i.setServervandatabaseServer(this));
        }
        this.servervandatabaseDatabases = databases;
    }

    public Server servervandatabaseDatabases(Set<Database> databases) {
        this.setServervandatabaseDatabases(databases);
        return this;
    }

    public Server addServervandatabaseDatabase(Database database) {
        this.servervandatabaseDatabases.add(database);
        database.setServervandatabaseServer(this);
        return this;
    }

    public Server removeServervandatabaseDatabase(Database database) {
        this.servervandatabaseDatabases.remove(database);
        database.setServervandatabaseServer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Server)) {
            return false;
        }
        return getId() != null && getId().equals(((Server) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Server{" +
            "id=" + getId() +
            ", actief='" + getActief() + "'" +
            ", ipadres='" + getIpadres() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", organisatie='" + getOrganisatie() + "'" +
            ", serienummer='" + getSerienummer() + "'" +
            ", serverid='" + getServerid() + "'" +
            ", servertype='" + getServertype() + "'" +
            ", vlan='" + getVlan() + "'" +
            "}";
    }
}
