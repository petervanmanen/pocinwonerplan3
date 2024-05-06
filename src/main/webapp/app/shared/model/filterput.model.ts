export interface IFilterput {
  id?: number;
  drain?: string | null;
  risicogebied?: string | null;
}

export const defaultValue: Readonly<IFilterput> = {};
