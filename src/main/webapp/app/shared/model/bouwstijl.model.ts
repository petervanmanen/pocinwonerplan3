export interface IBouwstijl {
  id?: number;
  hoofdstijl?: string | null;
  substijl?: string | null;
  toelichting?: string | null;
  zuiverheid?: string | null;
}

export const defaultValue: Readonly<IBouwstijl> = {};
