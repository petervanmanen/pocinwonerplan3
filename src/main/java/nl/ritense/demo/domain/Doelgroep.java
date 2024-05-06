package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Doelgroep.
 */
@Entity
@Table(name = "doelgroep")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Doelgroep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "branch")
    private String branch;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "segment")
    private String segment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bestaatuitDoelgroep2")
    @JsonIgnoreProperties(
        value = { "bestaatuitDoelgroeps", "bestaatuitDoelgroep2", "valtbinnendoelgroepClients", "valtbinnenMuseumrelaties" },
        allowSetters = true
    )
    private Set<Doelgroep> bestaatuitDoelgroeps = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "bestaatuitDoelgroeps", "bestaatuitDoelgroep2", "valtbinnendoelgroepClients", "valtbinnenMuseumrelaties" },
        allowSetters = true
    )
    private Doelgroep bestaatuitDoelgroep2;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnendoelgroepDoelgroep")
    @JsonIgnoreProperties(
        value = {
            "heeftParticipatiedossier",
            "heeftvoorzieningInkomensvoorziening",
            "heeftScores",
            "leverttegenprestatieTegenprestaties",
            "heeftparticipatietrajectTrajects",
            "heeftfraudegegevensFraudegegevens",
            "heeftregelingRegelings",
            "heefttrajectTrajects",
            "valtbinnendoelgroepDoelgroep",
            "heeftrelatieRelaties",
            "voorzieningbijstandspartijInkomensvoorzienings",
            "maaktonderdeeluitvanHuishoudens",
            "heefttaalniveauTaalniveaus",
            "emptyBeschikkings",
            "prestatievoorLeverings",
            "betreftDeclaratieregels",
            "ondersteuntclientClientbegeleiders",
        },
        allowSetters = true
    )
    private Set<Client> valtbinnendoelgroepClients = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "valtbinnenDoelgroeps")
    @JsonIgnoreProperties(value = { "voorProgrammas", "valtbinnenDoelgroeps", "versturenaanMailings" }, allowSetters = true)
    private Set<Museumrelatie> valtbinnenMuseumrelaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Doelgroep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranch() {
        return this.branch;
    }

    public Doelgroep branch(String branch) {
        this.setBranch(branch);
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getNaam() {
        return this.naam;
    }

    public Doelgroep naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Doelgroep omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getSegment() {
        return this.segment;
    }

    public Doelgroep segment(String segment) {
        this.setSegment(segment);
        return this;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public Set<Doelgroep> getBestaatuitDoelgroeps() {
        return this.bestaatuitDoelgroeps;
    }

    public void setBestaatuitDoelgroeps(Set<Doelgroep> doelgroeps) {
        if (this.bestaatuitDoelgroeps != null) {
            this.bestaatuitDoelgroeps.forEach(i -> i.setBestaatuitDoelgroep2(null));
        }
        if (doelgroeps != null) {
            doelgroeps.forEach(i -> i.setBestaatuitDoelgroep2(this));
        }
        this.bestaatuitDoelgroeps = doelgroeps;
    }

    public Doelgroep bestaatuitDoelgroeps(Set<Doelgroep> doelgroeps) {
        this.setBestaatuitDoelgroeps(doelgroeps);
        return this;
    }

    public Doelgroep addBestaatuitDoelgroep(Doelgroep doelgroep) {
        this.bestaatuitDoelgroeps.add(doelgroep);
        doelgroep.setBestaatuitDoelgroep2(this);
        return this;
    }

    public Doelgroep removeBestaatuitDoelgroep(Doelgroep doelgroep) {
        this.bestaatuitDoelgroeps.remove(doelgroep);
        doelgroep.setBestaatuitDoelgroep2(null);
        return this;
    }

    public Doelgroep getBestaatuitDoelgroep2() {
        return this.bestaatuitDoelgroep2;
    }

    public void setBestaatuitDoelgroep2(Doelgroep doelgroep) {
        this.bestaatuitDoelgroep2 = doelgroep;
    }

    public Doelgroep bestaatuitDoelgroep2(Doelgroep doelgroep) {
        this.setBestaatuitDoelgroep2(doelgroep);
        return this;
    }

    public Set<Client> getValtbinnendoelgroepClients() {
        return this.valtbinnendoelgroepClients;
    }

    public void setValtbinnendoelgroepClients(Set<Client> clients) {
        if (this.valtbinnendoelgroepClients != null) {
            this.valtbinnendoelgroepClients.forEach(i -> i.setValtbinnendoelgroepDoelgroep(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setValtbinnendoelgroepDoelgroep(this));
        }
        this.valtbinnendoelgroepClients = clients;
    }

    public Doelgroep valtbinnendoelgroepClients(Set<Client> clients) {
        this.setValtbinnendoelgroepClients(clients);
        return this;
    }

    public Doelgroep addValtbinnendoelgroepClient(Client client) {
        this.valtbinnendoelgroepClients.add(client);
        client.setValtbinnendoelgroepDoelgroep(this);
        return this;
    }

    public Doelgroep removeValtbinnendoelgroepClient(Client client) {
        this.valtbinnendoelgroepClients.remove(client);
        client.setValtbinnendoelgroepDoelgroep(null);
        return this;
    }

    public Set<Museumrelatie> getValtbinnenMuseumrelaties() {
        return this.valtbinnenMuseumrelaties;
    }

    public void setValtbinnenMuseumrelaties(Set<Museumrelatie> museumrelaties) {
        if (this.valtbinnenMuseumrelaties != null) {
            this.valtbinnenMuseumrelaties.forEach(i -> i.removeValtbinnenDoelgroep(this));
        }
        if (museumrelaties != null) {
            museumrelaties.forEach(i -> i.addValtbinnenDoelgroep(this));
        }
        this.valtbinnenMuseumrelaties = museumrelaties;
    }

    public Doelgroep valtbinnenMuseumrelaties(Set<Museumrelatie> museumrelaties) {
        this.setValtbinnenMuseumrelaties(museumrelaties);
        return this;
    }

    public Doelgroep addValtbinnenMuseumrelatie(Museumrelatie museumrelatie) {
        this.valtbinnenMuseumrelaties.add(museumrelatie);
        museumrelatie.getValtbinnenDoelgroeps().add(this);
        return this;
    }

    public Doelgroep removeValtbinnenMuseumrelatie(Museumrelatie museumrelatie) {
        this.valtbinnenMuseumrelaties.remove(museumrelatie);
        museumrelatie.getValtbinnenDoelgroeps().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doelgroep)) {
            return false;
        }
        return getId() != null && getId().equals(((Doelgroep) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doelgroep{" +
            "id=" + getId() +
            ", branch='" + getBranch() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", segment='" + getSegment() + "'" +
            "}";
    }
}
