package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Database.
 */
@Entity
@Table(name = "database")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Database implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "architectuur")
    private String architectuur;

    @Column(name = "database")
    private String database;

    @Column(name = "databaseversie")
    private String databaseversie;

    @Column(name = "dbms")
    private String dbms;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "otap")
    private String otap;

    @Column(name = "vlan")
    private String vlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftleverancierLeverancier", "servervandatabaseDatabases" }, allowSetters = true)
    private Server servervandatabaseServer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Database id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchitectuur() {
        return this.architectuur;
    }

    public Database architectuur(String architectuur) {
        this.setArchitectuur(architectuur);
        return this;
    }

    public void setArchitectuur(String architectuur) {
        this.architectuur = architectuur;
    }

    public String getDatabase() {
        return this.database;
    }

    public Database database(String database) {
        this.setDatabase(database);
        return this;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabaseversie() {
        return this.databaseversie;
    }

    public Database databaseversie(String databaseversie) {
        this.setDatabaseversie(databaseversie);
        return this;
    }

    public void setDatabaseversie(String databaseversie) {
        this.databaseversie = databaseversie;
    }

    public String getDbms() {
        return this.dbms;
    }

    public Database dbms(String dbms) {
        this.setDbms(dbms);
        return this;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Database omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOtap() {
        return this.otap;
    }

    public Database otap(String otap) {
        this.setOtap(otap);
        return this;
    }

    public void setOtap(String otap) {
        this.otap = otap;
    }

    public String getVlan() {
        return this.vlan;
    }

    public Database vlan(String vlan) {
        this.setVlan(vlan);
        return this;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    public Server getServervandatabaseServer() {
        return this.servervandatabaseServer;
    }

    public void setServervandatabaseServer(Server server) {
        this.servervandatabaseServer = server;
    }

    public Database servervandatabaseServer(Server server) {
        this.setServervandatabaseServer(server);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Database)) {
            return false;
        }
        return getId() != null && getId().equals(((Database) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Database{" +
            "id=" + getId() +
            ", architectuur='" + getArchitectuur() + "'" +
            ", database='" + getDatabase() + "'" +
            ", databaseversie='" + getDatabaseversie() + "'" +
            ", dbms='" + getDbms() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", otap='" + getOtap() + "'" +
            ", vlan='" + getVlan() + "'" +
            "}";
    }
}
