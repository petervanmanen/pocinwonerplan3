export interface IVerkeersdrempel {
  id?: number;
  ontwerpsnelheid?: string | null;
  type?: string | null;
  typeplus?: string | null;
}

export const defaultValue: Readonly<IVerkeersdrempel> = {};
