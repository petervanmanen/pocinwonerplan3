package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vlak.
 */
@Entity
@Table(name = "vlak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vlak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dieptetot")
    private String dieptetot;

    @Column(name = "dieptevan")
    private String dieptevan;

    @Column(name = "key")
    private String key;

    @Column(name = "keyput")
    private String keyput;

    @Size(max = 20)
    @Column(name = "projectcd", length = 20)
    private String projectcd;

    @Size(max = 20)
    @Column(name = "putnummer", length = 20)
    private String putnummer;

    @Size(max = 20)
    @Column(name = "vlaknummer", length = 20)
    private String vlaknummer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftVlak")
    @JsonIgnoreProperties(value = { "heeftVullings", "heeftVlak" }, allowSetters = true)
    private Set<Spoor> heeftSpoors = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftVlaks", "heeftlocatieLocaties", "heeftProject" }, allowSetters = true)
    private Put heeftPut;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vlak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDieptetot() {
        return this.dieptetot;
    }

    public Vlak dieptetot(String dieptetot) {
        this.setDieptetot(dieptetot);
        return this;
    }

    public void setDieptetot(String dieptetot) {
        this.dieptetot = dieptetot;
    }

    public String getDieptevan() {
        return this.dieptevan;
    }

    public Vlak dieptevan(String dieptevan) {
        this.setDieptevan(dieptevan);
        return this;
    }

    public void setDieptevan(String dieptevan) {
        this.dieptevan = dieptevan;
    }

    public String getKey() {
        return this.key;
    }

    public Vlak key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyput() {
        return this.keyput;
    }

    public Vlak keyput(String keyput) {
        this.setKeyput(keyput);
        return this;
    }

    public void setKeyput(String keyput) {
        this.keyput = keyput;
    }

    public String getProjectcd() {
        return this.projectcd;
    }

    public Vlak projectcd(String projectcd) {
        this.setProjectcd(projectcd);
        return this;
    }

    public void setProjectcd(String projectcd) {
        this.projectcd = projectcd;
    }

    public String getPutnummer() {
        return this.putnummer;
    }

    public Vlak putnummer(String putnummer) {
        this.setPutnummer(putnummer);
        return this;
    }

    public void setPutnummer(String putnummer) {
        this.putnummer = putnummer;
    }

    public String getVlaknummer() {
        return this.vlaknummer;
    }

    public Vlak vlaknummer(String vlaknummer) {
        this.setVlaknummer(vlaknummer);
        return this;
    }

    public void setVlaknummer(String vlaknummer) {
        this.vlaknummer = vlaknummer;
    }

    public Set<Spoor> getHeeftSpoors() {
        return this.heeftSpoors;
    }

    public void setHeeftSpoors(Set<Spoor> spoors) {
        if (this.heeftSpoors != null) {
            this.heeftSpoors.forEach(i -> i.setHeeftVlak(null));
        }
        if (spoors != null) {
            spoors.forEach(i -> i.setHeeftVlak(this));
        }
        this.heeftSpoors = spoors;
    }

    public Vlak heeftSpoors(Set<Spoor> spoors) {
        this.setHeeftSpoors(spoors);
        return this;
    }

    public Vlak addHeeftSpoor(Spoor spoor) {
        this.heeftSpoors.add(spoor);
        spoor.setHeeftVlak(this);
        return this;
    }

    public Vlak removeHeeftSpoor(Spoor spoor) {
        this.heeftSpoors.remove(spoor);
        spoor.setHeeftVlak(null);
        return this;
    }

    public Put getHeeftPut() {
        return this.heeftPut;
    }

    public void setHeeftPut(Put put) {
        this.heeftPut = put;
    }

    public Vlak heeftPut(Put put) {
        this.setHeeftPut(put);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vlak)) {
            return false;
        }
        return getId() != null && getId().equals(((Vlak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vlak{" +
            "id=" + getId() +
            ", dieptetot='" + getDieptetot() + "'" +
            ", dieptevan='" + getDieptevan() + "'" +
            ", key='" + getKey() + "'" +
            ", keyput='" + getKeyput() + "'" +
            ", projectcd='" + getProjectcd() + "'" +
            ", putnummer='" + getPutnummer() + "'" +
            ", vlaknummer='" + getVlaknummer() + "'" +
            "}";
    }
}
