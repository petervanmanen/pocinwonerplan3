export interface IKeermuur {
  id?: number;
  belastingklassenieuw?: string | null;
  belastingklasseoud?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IKeermuur> = {};
