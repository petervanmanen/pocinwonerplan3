export interface IFietsregistratie {
  id?: number;
  gelabeld?: string | null;
  verwijderd?: string | null;
}

export const defaultValue: Readonly<IFietsregistratie> = {};
