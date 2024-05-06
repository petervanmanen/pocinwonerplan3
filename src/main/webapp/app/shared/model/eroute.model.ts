export interface IEroute {
  id?: number;
  geometrie?: string | null;
  eroutecode?: string | null;
  eroutesoort?: string | null;
}

export const defaultValue: Readonly<IEroute> = {};
