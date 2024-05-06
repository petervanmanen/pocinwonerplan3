export interface IMaterielehistorie {
  id?: number;
  datumbegingeldigheidgegevens?: string | null;
  datumeindegeldigheidgegevens?: string | null;
}

export const defaultValue: Readonly<IMaterielehistorie> = {};
