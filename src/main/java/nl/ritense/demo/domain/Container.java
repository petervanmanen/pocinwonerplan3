package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Container.
 */
@Entity
@Table(name = "container")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Container implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "containercode")
    private String containercode;

    @Column(name = "sensorid")
    private String sensorid;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftContainer")
    @JsonIgnoreProperties(value = { "heeftContainer" }, allowSetters = true)
    private Set<Vulgraadmeting> heeftVulgraadmetings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "geschiktvoorContainers", "betreftMeldings", "inzamelpuntvanMilieustraats", "fractieStortings" },
        allowSetters = true
    )
    private Fractie geschiktvoorFractie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortContainers", "betreftMeldings", "geschiktvoorVuilniswagens" }, allowSetters = true)
    private Containertype soortContainertype;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Locatie heeftLocatie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gelostContainer")
    @JsonIgnoreProperties(value = { "gelostContainer", "gestoptopLocatie", "heeftRit" }, allowSetters = true)
    private Set<Ophaalmoment> gelostOphaalmoments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Container id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContainercode() {
        return this.containercode;
    }

    public Container containercode(String containercode) {
        this.setContainercode(containercode);
        return this;
    }

    public void setContainercode(String containercode) {
        this.containercode = containercode;
    }

    public String getSensorid() {
        return this.sensorid;
    }

    public Container sensorid(String sensorid) {
        this.setSensorid(sensorid);
        return this;
    }

    public void setSensorid(String sensorid) {
        this.sensorid = sensorid;
    }

    public Set<Vulgraadmeting> getHeeftVulgraadmetings() {
        return this.heeftVulgraadmetings;
    }

    public void setHeeftVulgraadmetings(Set<Vulgraadmeting> vulgraadmetings) {
        if (this.heeftVulgraadmetings != null) {
            this.heeftVulgraadmetings.forEach(i -> i.setHeeftContainer(null));
        }
        if (vulgraadmetings != null) {
            vulgraadmetings.forEach(i -> i.setHeeftContainer(this));
        }
        this.heeftVulgraadmetings = vulgraadmetings;
    }

    public Container heeftVulgraadmetings(Set<Vulgraadmeting> vulgraadmetings) {
        this.setHeeftVulgraadmetings(vulgraadmetings);
        return this;
    }

    public Container addHeeftVulgraadmeting(Vulgraadmeting vulgraadmeting) {
        this.heeftVulgraadmetings.add(vulgraadmeting);
        vulgraadmeting.setHeeftContainer(this);
        return this;
    }

    public Container removeHeeftVulgraadmeting(Vulgraadmeting vulgraadmeting) {
        this.heeftVulgraadmetings.remove(vulgraadmeting);
        vulgraadmeting.setHeeftContainer(null);
        return this;
    }

    public Fractie getGeschiktvoorFractie() {
        return this.geschiktvoorFractie;
    }

    public void setGeschiktvoorFractie(Fractie fractie) {
        this.geschiktvoorFractie = fractie;
    }

    public Container geschiktvoorFractie(Fractie fractie) {
        this.setGeschiktvoorFractie(fractie);
        return this;
    }

    public Containertype getSoortContainertype() {
        return this.soortContainertype;
    }

    public void setSoortContainertype(Containertype containertype) {
        this.soortContainertype = containertype;
    }

    public Container soortContainertype(Containertype containertype) {
        this.setSoortContainertype(containertype);
        return this;
    }

    public Locatie getHeeftLocatie() {
        return this.heeftLocatie;
    }

    public void setHeeftLocatie(Locatie locatie) {
        this.heeftLocatie = locatie;
    }

    public Container heeftLocatie(Locatie locatie) {
        this.setHeeftLocatie(locatie);
        return this;
    }

    public Set<Ophaalmoment> getGelostOphaalmoments() {
        return this.gelostOphaalmoments;
    }

    public void setGelostOphaalmoments(Set<Ophaalmoment> ophaalmoments) {
        if (this.gelostOphaalmoments != null) {
            this.gelostOphaalmoments.forEach(i -> i.setGelostContainer(null));
        }
        if (ophaalmoments != null) {
            ophaalmoments.forEach(i -> i.setGelostContainer(this));
        }
        this.gelostOphaalmoments = ophaalmoments;
    }

    public Container gelostOphaalmoments(Set<Ophaalmoment> ophaalmoments) {
        this.setGelostOphaalmoments(ophaalmoments);
        return this;
    }

    public Container addGelostOphaalmoment(Ophaalmoment ophaalmoment) {
        this.gelostOphaalmoments.add(ophaalmoment);
        ophaalmoment.setGelostContainer(this);
        return this;
    }

    public Container removeGelostOphaalmoment(Ophaalmoment ophaalmoment) {
        this.gelostOphaalmoments.remove(ophaalmoment);
        ophaalmoment.setGelostContainer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Container)) {
            return false;
        }
        return getId() != null && getId().equals(((Container) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Container{" +
            "id=" + getId() +
            ", containercode='" + getContainercode() + "'" +
            ", sensorid='" + getSensorid() + "'" +
            "}";
    }
}
