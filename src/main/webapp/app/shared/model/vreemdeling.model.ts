export interface IVreemdeling {
  id?: number;
  sociaalreferent?: string | null;
  vnummer?: string | null;
}

export const defaultValue: Readonly<IVreemdeling> = {};
