export interface IFinancielesituatie {
  id?: number;
  datumvastgesteld?: string | null;
  schuld?: string | null;
}

export const defaultValue: Readonly<IFinancielesituatie> = {};
