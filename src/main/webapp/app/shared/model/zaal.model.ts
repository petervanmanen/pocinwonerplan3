export interface IZaal {
  id?: number;
  capaciteit?: string | null;
  naam?: string | null;
  nummer?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IZaal> = {};
