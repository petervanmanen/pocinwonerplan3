export interface IHandelsnamenvestiging {
  id?: number;
  handelsnaam?: string | null;
  verkortenaam?: string | null;
  volgorde?: string | null;
}

export const defaultValue: Readonly<IHandelsnamenvestiging> = {};
