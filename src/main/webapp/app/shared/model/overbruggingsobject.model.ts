export interface IOverbruggingsobject {
  id?: number;
  aanleghoogte?: string | null;
  antigraffitivoorziening?: boolean | null;
  bereikbaarheid?: string | null;
  breedte?: string | null;
  hoogte?: string | null;
  installateur?: string | null;
  jaarconserveren?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  jaarrenovatie?: string | null;
  jaarvervanging?: string | null;
  kleur?: string | null;
  kwaliteitsniveauactueel?: string | null;
  kwaliteitsniveaugewenst?: string | null;
  lengte?: string | null;
  looprichel?: boolean | null;
  minimumconditiescore?: string | null;
  onderhoudsregime?: string | null;
  oppervlakte?: string | null;
  overbruggingsobjectmateriaal?: string | null;
  overbruggingsobjectmodaliteit?: string | null;
  technischelevensduur?: string | null;
  typefundering?: string | null;
  vervangingswaarde?: string | null;
}

export const defaultValue: Readonly<IOverbruggingsobject> = {
  antigraffitivoorziening: false,
  looprichel: false,
};
