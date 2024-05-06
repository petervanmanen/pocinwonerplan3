export interface IWaterinrichtingsobject {
  id?: number;
  aanleghoogte?: string | null;
  breedte?: string | null;
  jaarconserveren?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  kwaliteitsniveauactueel?: string | null;
  kwaliteitsniveaugewenst?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  materiaal?: string | null;
  oppervlakte?: string | null;
}

export const defaultValue: Readonly<IWaterinrichtingsobject> = {};
