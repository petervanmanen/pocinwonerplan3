export interface INederlandsenationaliteitingeschrevenpersoon {
  id?: number;
  aanduidingbijzondernederlanderschap?: string | null;
  nationaliteit?: string | null;
  redenverkrijgingnederlandsenationaliteit?: string | null;
  redenverliesnederlandsenationaliteit?: string | null;
}

export const defaultValue: Readonly<INederlandsenationaliteitingeschrevenpersoon> = {};
