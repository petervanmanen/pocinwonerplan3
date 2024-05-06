export interface IRekeningnummer {
  id?: number;
  bic?: string | null;
  iban?: string | null;
}

export const defaultValue: Readonly<IRekeningnummer> = {};
