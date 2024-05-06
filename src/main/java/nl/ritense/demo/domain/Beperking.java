package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Beperking.
 */
@Entity
@Table(name = "beperking")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beperking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "commentaar")
    private String commentaar;

    @Column(name = "duur")
    private String duur;

    @Column(name = "wet")
    private String wet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emptyBeperking")
    @JsonIgnoreProperties(value = { "iseenBeperkingscoresoort", "emptyBeperking" }, allowSetters = true)
    private Set<Beperkingscore> emptyBeperkingscores = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "iseenBeperkings" }, allowSetters = true)
    private Beperkingscategorie iseenBeperkingscategorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "toewijzingToewijzings",
            "emptyClient",
            "geeftafClientbegeleider",
            "isgebaseerdopBeperkings",
            "geleverdeprestatieLeverings",
            "isvoorDeclaratieregels",
        },
        allowSetters = true
    )
    private Beschikking isgebaseerdopBeschikking;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beperking id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public Beperking categorie(String categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getCommentaar() {
        return this.commentaar;
    }

    public Beperking commentaar(String commentaar) {
        this.setCommentaar(commentaar);
        return this;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public String getDuur() {
        return this.duur;
    }

    public Beperking duur(String duur) {
        this.setDuur(duur);
        return this;
    }

    public void setDuur(String duur) {
        this.duur = duur;
    }

    public String getWet() {
        return this.wet;
    }

    public Beperking wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public Set<Beperkingscore> getEmptyBeperkingscores() {
        return this.emptyBeperkingscores;
    }

    public void setEmptyBeperkingscores(Set<Beperkingscore> beperkingscores) {
        if (this.emptyBeperkingscores != null) {
            this.emptyBeperkingscores.forEach(i -> i.setEmptyBeperking(null));
        }
        if (beperkingscores != null) {
            beperkingscores.forEach(i -> i.setEmptyBeperking(this));
        }
        this.emptyBeperkingscores = beperkingscores;
    }

    public Beperking emptyBeperkingscores(Set<Beperkingscore> beperkingscores) {
        this.setEmptyBeperkingscores(beperkingscores);
        return this;
    }

    public Beperking addEmptyBeperkingscore(Beperkingscore beperkingscore) {
        this.emptyBeperkingscores.add(beperkingscore);
        beperkingscore.setEmptyBeperking(this);
        return this;
    }

    public Beperking removeEmptyBeperkingscore(Beperkingscore beperkingscore) {
        this.emptyBeperkingscores.remove(beperkingscore);
        beperkingscore.setEmptyBeperking(null);
        return this;
    }

    public Beperkingscategorie getIseenBeperkingscategorie() {
        return this.iseenBeperkingscategorie;
    }

    public void setIseenBeperkingscategorie(Beperkingscategorie beperkingscategorie) {
        this.iseenBeperkingscategorie = beperkingscategorie;
    }

    public Beperking iseenBeperkingscategorie(Beperkingscategorie beperkingscategorie) {
        this.setIseenBeperkingscategorie(beperkingscategorie);
        return this;
    }

    public Beschikking getIsgebaseerdopBeschikking() {
        return this.isgebaseerdopBeschikking;
    }

    public void setIsgebaseerdopBeschikking(Beschikking beschikking) {
        this.isgebaseerdopBeschikking = beschikking;
    }

    public Beperking isgebaseerdopBeschikking(Beschikking beschikking) {
        this.setIsgebaseerdopBeschikking(beschikking);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beperking)) {
            return false;
        }
        return getId() != null && getId().equals(((Beperking) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beperking{" +
            "id=" + getId() +
            ", categorie='" + getCategorie() + "'" +
            ", commentaar='" + getCommentaar() + "'" +
            ", duur='" + getDuur() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
