export interface IKlimplant {
  id?: number;
  hoogte?: string | null;
  knipfrequentie?: string | null;
  knipoppervlakte?: string | null;
  ondersteuningsvorm?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IKlimplant> = {};
