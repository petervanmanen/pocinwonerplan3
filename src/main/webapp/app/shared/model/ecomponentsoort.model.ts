export interface IEcomponentsoort {
  id?: number;
  ecomponent?: string | null;
  ecomponentcode?: string | null;
  kolom?: string | null;
  kolomcode?: string | null;
  regeling?: string | null;
  regelingcode?: string | null;
}

export const defaultValue: Readonly<IEcomponentsoort> = {};
