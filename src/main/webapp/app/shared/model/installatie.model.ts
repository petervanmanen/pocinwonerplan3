export interface IInstallatie {
  id?: number;
  breedte?: string | null;
  eancode?: string | null;
  fabrikant?: string | null;
  hoogte?: string | null;
  inbelgegevens?: string | null;
  installateur?: string | null;
  jaaronderhouduitgevoerd?: string | null;
  lengte?: string | null;
  leverancier?: string | null;
  typecommunicatie?: string | null;
}

export const defaultValue: Readonly<IInstallatie> = {};
