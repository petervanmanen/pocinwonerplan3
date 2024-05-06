export interface IKadastraalperceel {
  id?: number;
  aanduidingsoortgrootte?: string | null;
  begrenzingperceel?: string | null;
  grootteperceel?: string | null;
  indicatiedeelperceel?: string | null;
  omschrijvingdeelperceel?: string | null;
  plaatscoordinatenperceel?: string | null;
}

export const defaultValue: Readonly<IKadastraalperceel> = {};
