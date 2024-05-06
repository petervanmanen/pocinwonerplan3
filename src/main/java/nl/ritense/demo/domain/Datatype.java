package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Datatype.
 */
@Entity
@Table(name = "datatype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Datatype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "datumopname")
    private LocalDate datumopname;

    @Column(name = "definitie")
    private String definitie;

    @Column(name = "domein")
    private String domein;

    @Column(name = "eaguid")
    private String eaguid;

    @Column(name = "herkomst")
    private String herkomst;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @Column(name = "kardinaliteit")
    private String kardinaliteit;

    @Column(name = "lengte")
    private String lengte;

    @Column(name = "naam")
    private String naam;

    @Column(name = "patroon")
    private String patroon;

    @Column(name = "toelichting")
    private String toelichting;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftDatatype")
    @JsonIgnoreProperties(value = { "heeftDatatype" }, allowSetters = true)
    private Set<Attribuutsoort> heeftAttribuutsoorts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public LocalDate getDatumopname() {
        return this.datumopname;
    }

    public Datatype datumopname(LocalDate datumopname) {
        this.setDatumopname(datumopname);
        return this;
    }

    public void setDatumopname(LocalDate datumopname) {
        this.datumopname = datumopname;
    }

    public String getDefinitie() {
        return this.definitie;
    }

    public Datatype definitie(String definitie) {
        this.setDefinitie(definitie);
        return this;
    }

    public void setDefinitie(String definitie) {
        this.definitie = definitie;
    }

    public String getDomein() {
        return this.domein;
    }

    public Datatype domein(String domein) {
        this.setDomein(domein);
        return this;
    }

    public void setDomein(String domein) {
        this.domein = domein;
    }

    public String getEaguid() {
        return this.eaguid;
    }

    public Datatype eaguid(String eaguid) {
        this.setEaguid(eaguid);
        return this;
    }

    public void setEaguid(String eaguid) {
        this.eaguid = eaguid;
    }

    public String getHerkomst() {
        return this.herkomst;
    }

    public Datatype herkomst(String herkomst) {
        this.setHerkomst(herkomst);
        return this;
    }

    public void setHerkomst(String herkomst) {
        this.herkomst = herkomst;
    }

    public String getId() {
        return this.id;
    }

    public Datatype id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKardinaliteit() {
        return this.kardinaliteit;
    }

    public Datatype kardinaliteit(String kardinaliteit) {
        this.setKardinaliteit(kardinaliteit);
        return this;
    }

    public void setKardinaliteit(String kardinaliteit) {
        this.kardinaliteit = kardinaliteit;
    }

    public String getLengte() {
        return this.lengte;
    }

    public Datatype lengte(String lengte) {
        this.setLengte(lengte);
        return this;
    }

    public void setLengte(String lengte) {
        this.lengte = lengte;
    }

    public String getNaam() {
        return this.naam;
    }

    public Datatype naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getPatroon() {
        return this.patroon;
    }

    public Datatype patroon(String patroon) {
        this.setPatroon(patroon);
        return this;
    }

    public void setPatroon(String patroon) {
        this.patroon = patroon;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Datatype toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public Set<Attribuutsoort> getHeeftAttribuutsoorts() {
        return this.heeftAttribuutsoorts;
    }

    public void setHeeftAttribuutsoorts(Set<Attribuutsoort> attribuutsoorts) {
        if (this.heeftAttribuutsoorts != null) {
            this.heeftAttribuutsoorts.forEach(i -> i.setHeeftDatatype(null));
        }
        if (attribuutsoorts != null) {
            attribuutsoorts.forEach(i -> i.setHeeftDatatype(this));
        }
        this.heeftAttribuutsoorts = attribuutsoorts;
    }

    public Datatype heeftAttribuutsoorts(Set<Attribuutsoort> attribuutsoorts) {
        this.setHeeftAttribuutsoorts(attribuutsoorts);
        return this;
    }

    public Datatype addHeeftAttribuutsoort(Attribuutsoort attribuutsoort) {
        this.heeftAttribuutsoorts.add(attribuutsoort);
        attribuutsoort.setHeeftDatatype(this);
        return this;
    }

    public Datatype removeHeeftAttribuutsoort(Attribuutsoort attribuutsoort) {
        this.heeftAttribuutsoorts.remove(attribuutsoort);
        attribuutsoort.setHeeftDatatype(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Datatype)) {
            return false;
        }
        return getId() != null && getId().equals(((Datatype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Datatype{" +
            "id=" + getId() +
            ", datumopname='" + getDatumopname() + "'" +
            ", definitie='" + getDefinitie() + "'" +
            ", domein='" + getDomein() + "'" +
            ", eaguid='" + getEaguid() + "'" +
            ", herkomst='" + getHerkomst() + "'" +
            ", kardinaliteit='" + getKardinaliteit() + "'" +
            ", lengte='" + getLengte() + "'" +
            ", naam='" + getNaam() + "'" +
            ", patroon='" + getPatroon() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
