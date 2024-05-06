export interface IVoertuig {
  id?: number;
  kenteken?: string | null;
  kleur?: string | null;
  land?: string | null;
  merk?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IVoertuig> = {};
