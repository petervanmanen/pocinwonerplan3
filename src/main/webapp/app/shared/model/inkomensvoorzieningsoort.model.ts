export interface IInkomensvoorzieningsoort {
  id?: number;
  code?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  regeling?: string | null;
  regelingcode?: string | null;
  vergoeding?: string | null;
  vergoedingscode?: string | null;
  wet?: string | null;
}

export const defaultValue: Readonly<IInkomensvoorzieningsoort> = {};
