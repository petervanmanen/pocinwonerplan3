export interface IGeoobject {
  id?: number;
  datumbegingeldigheid?: string | null;
  datumeindegeldigheid?: string | null;
  geometriesoort?: string | null;
  identificatie?: string | null;
}

export const defaultValue: Readonly<IGeoobject> = {};
