export interface IEpackage {
  id?: number;
  naam?: string | null;
  proces?: string | null;
  project?: string | null;
  status?: string | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IEpackage> = {};
