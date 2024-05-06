export interface IKunstwerk {
  id?: number;
  aanleghoogte?: string | null;
  antigraffitivoorziening?: boolean | null;
  bereikbaarheid?: string | null;
  breedte?: string | null;
  constructietype?: string | null;
  gewicht?: string | null;
  hoogte?: string | null;
  installateur?: string | null;
  jaarconserveren?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  jaarrenovatie?: string | null;
  jaarvervanging?: string | null;
  kilometreringbegin?: string | null;
  kilometreringeinde?: string | null;
  kleur?: string | null;
  kunstwerkbereikbaarheidplus?: string | null;
  kunstwerkmateriaal?: string | null;
  kwaliteitsniveauactueel?: string | null;
  kwaliteitsniveaugewenst?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  looprichel?: boolean | null;
  minimumconditiescore?: string | null;
  monument?: boolean | null;
  monumentnummer?: string | null;
  eobjectnaam?: string | null;
  eobjectnummer?: string | null;
  onderhoudsregime?: string | null;
  oppervlakte?: string | null;
  orientatie?: string | null;
  technischelevensduur?: string | null;
  typefundering?: string | null;
  typemonument?: string | null;
  vervangingswaarde?: string | null;
  wegnummer?: string | null;
}

export const defaultValue: Readonly<IKunstwerk> = {
  antigraffitivoorziening: false,
  looprichel: false,
  monument: false,
};
