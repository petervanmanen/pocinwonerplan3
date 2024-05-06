export interface INormprofiel {
  id?: number;
  code?: string | null;
  omschrijving?: string | null;
  schaal?: string | null;
}

export const defaultValue: Readonly<INormprofiel> = {};
