export interface IDigitaalbestand {
  id?: number;
  blob?: string | null;
  mimetype?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
}

export const defaultValue: Readonly<IDigitaalbestand> = {};
