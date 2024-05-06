export interface IBouwwerk {
  id?: number;
  aanleghoogte?: string | null;
  bouwwerkmateriaal?: string | null;
  breedte?: string | null;
  fabrikant?: string | null;
  hoogte?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  oppervlakte?: string | null;
  typefundering?: string | null;
}

export const defaultValue: Readonly<IBouwwerk> = {};
