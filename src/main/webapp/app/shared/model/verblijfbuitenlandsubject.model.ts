export interface IVerblijfbuitenlandsubject {
  id?: number;
  adresbuitenland1?: string | null;
  adresbuitenland2?: string | null;
  adresbuitenland3?: string | null;
  landverblijfadres?: string | null;
}

export const defaultValue: Readonly<IVerblijfbuitenlandsubject> = {};
