export interface IAansluitput {
  id?: number;
  aansluitpunt?: string | null;
  risicogebied?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IAansluitput> = {};
