export interface IStraatsectie {
  id?: number;
  code?: string | null;
  omschrijving?: string | null;
  zonecode?: string | null;
}

export const defaultValue: Readonly<IStraatsectie> = {};
