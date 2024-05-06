package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Areaal.
 */
@Entity
@Table(name = "areaal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Areaal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "geometrie")
    private String geometrie;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_areaal__ligtin_buurt",
        joinColumns = @JoinColumn(name = "areaal_id"),
        inverseJoinColumns = @JoinColumn(name = "ligtin_buurt_id")
    )
    @JsonIgnoreProperties(
        value = { "komtovereenGebied", "ligtinNummeraanduidings", "zonderverblijfsobjectligtinPands", "ligtinAreaals" },
        allowSetters = true
    )
    private Set<Buurt> ligtinBuurts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_areaal__valtbinnen_wijk",
        joinColumns = @JoinColumn(name = "areaal_id"),
        inverseJoinColumns = @JoinColumn(name = "valtbinnen_wijk_id")
    )
    @JsonIgnoreProperties(value = { "bedientBinnenlocaties", "valtbinnenAreaals" }, allowSetters = true)
    private Set<Wijk> valtbinnenWijks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_areaal__binnen_schouwronde",
        joinColumns = @JoinColumn(name = "areaal_id"),
        inverseJoinColumns = @JoinColumn(name = "binnen_schouwronde_id")
    )
    @JsonIgnoreProperties(value = { "heeftMeldings", "voertuitMedewerker", "binnenAreaals" }, allowSetters = true)
    private Set<Schouwronde> binnenSchouwrondes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Areaal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Areaal geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public Set<Buurt> getLigtinBuurts() {
        return this.ligtinBuurts;
    }

    public void setLigtinBuurts(Set<Buurt> buurts) {
        this.ligtinBuurts = buurts;
    }

    public Areaal ligtinBuurts(Set<Buurt> buurts) {
        this.setLigtinBuurts(buurts);
        return this;
    }

    public Areaal addLigtinBuurt(Buurt buurt) {
        this.ligtinBuurts.add(buurt);
        return this;
    }

    public Areaal removeLigtinBuurt(Buurt buurt) {
        this.ligtinBuurts.remove(buurt);
        return this;
    }

    public Set<Wijk> getValtbinnenWijks() {
        return this.valtbinnenWijks;
    }

    public void setValtbinnenWijks(Set<Wijk> wijks) {
        this.valtbinnenWijks = wijks;
    }

    public Areaal valtbinnenWijks(Set<Wijk> wijks) {
        this.setValtbinnenWijks(wijks);
        return this;
    }

    public Areaal addValtbinnenWijk(Wijk wijk) {
        this.valtbinnenWijks.add(wijk);
        return this;
    }

    public Areaal removeValtbinnenWijk(Wijk wijk) {
        this.valtbinnenWijks.remove(wijk);
        return this;
    }

    public Set<Schouwronde> getBinnenSchouwrondes() {
        return this.binnenSchouwrondes;
    }

    public void setBinnenSchouwrondes(Set<Schouwronde> schouwrondes) {
        this.binnenSchouwrondes = schouwrondes;
    }

    public Areaal binnenSchouwrondes(Set<Schouwronde> schouwrondes) {
        this.setBinnenSchouwrondes(schouwrondes);
        return this;
    }

    public Areaal addBinnenSchouwronde(Schouwronde schouwronde) {
        this.binnenSchouwrondes.add(schouwronde);
        return this;
    }

    public Areaal removeBinnenSchouwronde(Schouwronde schouwronde) {
        this.binnenSchouwrondes.remove(schouwronde);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Areaal)) {
            return false;
        }
        return getId() != null && getId().equals(((Areaal) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Areaal{" +
            "id=" + getId() +
            ", geometrie='" + getGeometrie() + "'" +
            "}";
    }
}
