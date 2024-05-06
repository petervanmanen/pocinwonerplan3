export interface IBankrekening {
  id?: number;
  bank?: string | null;
  nummer?: string | null;
  tennaamstelling?: string | null;
}

export const defaultValue: Readonly<IBankrekening> = {};
