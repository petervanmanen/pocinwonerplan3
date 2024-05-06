package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Gebied.
 */
@Entity
@Table(name = "gebied")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gebied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "gebied")
    private String gebied;

    @JsonIgnoreProperties(
        value = { "komtovereenGebied", "ligtinNummeraanduidings", "zonderverblijfsobjectligtinPands", "ligtinAreaals" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Buurt komtovereenBuurt;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "ligtinGebieds")
    @JsonIgnoreProperties(
        value = {
            "ligtinWoonplaats",
            "ligtinBuurt",
            "ligtinGebieds",
            "verwijstnaarAdresaanduiding",
            "emptyBriefadres",
            "heeftalslocatieadresVestigings",
        },
        allowSetters = true
    )
    private Set<Nummeraanduiding> ligtinNummeraanduidings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gebied id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGebied() {
        return this.gebied;
    }

    public Gebied gebied(String gebied) {
        this.setGebied(gebied);
        return this;
    }

    public void setGebied(String gebied) {
        this.gebied = gebied;
    }

    public Buurt getKomtovereenBuurt() {
        return this.komtovereenBuurt;
    }

    public void setKomtovereenBuurt(Buurt buurt) {
        this.komtovereenBuurt = buurt;
    }

    public Gebied komtovereenBuurt(Buurt buurt) {
        this.setKomtovereenBuurt(buurt);
        return this;
    }

    public Set<Nummeraanduiding> getLigtinNummeraanduidings() {
        return this.ligtinNummeraanduidings;
    }

    public void setLigtinNummeraanduidings(Set<Nummeraanduiding> nummeraanduidings) {
        if (this.ligtinNummeraanduidings != null) {
            this.ligtinNummeraanduidings.forEach(i -> i.removeLigtinGebied(this));
        }
        if (nummeraanduidings != null) {
            nummeraanduidings.forEach(i -> i.addLigtinGebied(this));
        }
        this.ligtinNummeraanduidings = nummeraanduidings;
    }

    public Gebied ligtinNummeraanduidings(Set<Nummeraanduiding> nummeraanduidings) {
        this.setLigtinNummeraanduidings(nummeraanduidings);
        return this;
    }

    public Gebied addLigtinNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.ligtinNummeraanduidings.add(nummeraanduiding);
        nummeraanduiding.getLigtinGebieds().add(this);
        return this;
    }

    public Gebied removeLigtinNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.ligtinNummeraanduidings.remove(nummeraanduiding);
        nummeraanduiding.getLigtinGebieds().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gebied)) {
            return false;
        }
        return getId() != null && getId().equals(((Gebied) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gebied{" +
            "id=" + getId() +
            ", gebied='" + getGebied() + "'" +
            "}";
    }
}
