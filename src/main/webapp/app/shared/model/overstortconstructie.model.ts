export interface IOverstortconstructie {
  id?: number;
  bassin?: string | null;
  drempelbreedte?: string | null;
  drempelniveau?: string | null;
  klep?: boolean | null;
  type?: string | null;
  vormdrempel?: string | null;
  waking?: string | null;
}

export const defaultValue: Readonly<IOverstortconstructie> = {
  klep: false,
};
