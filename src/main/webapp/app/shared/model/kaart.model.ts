export interface IKaart {
  id?: number;
  kaart?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IKaart> = {};
