export interface INatuurlijkpersoon {
  id?: number;
  empty?: string | null;
  aanduidingbijzondernederlanderschappersoon?: string | null;
  aanduidingnaamgebruik?: string | null;
  aanhefaanschrijving?: string | null;
  academischetitel?: string | null;
  achternaam?: string | null;
  adellijketitelofpredikaat?: string | null;
  anummer?: string | null;
  burgerservicenummer?: string | null;
  datumgeboorte?: string | null;
  datumoverlijden?: string | null;
  geboorteland?: string | null;
  geboorteplaats?: string | null;
  geslachtsaanduiding?: string | null;
  geslachtsnaam?: string | null;
  geslachtsnaamaanschrijving?: string | null;
  handlichting?: string | null;
  indicatieafschermingpersoonsgegevens?: boolean | null;
  indicatieoverleden?: boolean | null;
  landoverlijden?: string | null;
  nationaliteit?: string | null;
  overlijdensplaats?: string | null;
  voorlettersaanschrijving?: string | null;
  voornamen?: string | null;
  voornamenaanschrijving?: string | null;
  voorvoegselgeslachtsnaam?: string | null;
}

export const defaultValue: Readonly<INatuurlijkpersoon> = {
  indicatieafschermingpersoonsgegevens: false,
  indicatieoverleden: false,
};
