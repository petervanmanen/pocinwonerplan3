export interface IVerblijfbuitenland {
  id?: number;
  adresregelbuitenland1?: string | null;
  adresregelbuitenland2?: string | null;
  adresregelbuitenland3?: string | null;
  adresregelbuitenland4?: string | null;
  adresregelbuitenland5?: string | null;
  adresregelbuitenland6?: string | null;
  landofgebiedverblijfadres?: string | null;
}

export const defaultValue: Readonly<IVerblijfbuitenland> = {};
