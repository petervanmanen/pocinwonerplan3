export interface IFietsparkeervoorziening {
  id?: number;
  aantalparkeerplaatsen?: string | null;
  type?: string | null;
  typeplus?: string | null;
}

export const defaultValue: Readonly<IFietsparkeervoorziening> = {};
