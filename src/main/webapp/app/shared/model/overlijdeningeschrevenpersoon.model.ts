export interface IOverlijdeningeschrevenpersoon {
  id?: number;
  datumoverlijden?: string | null;
  landoverlijden?: string | null;
  overlijdensplaats?: string | null;
}

export const defaultValue: Readonly<IOverlijdeningeschrevenpersoon> = {};
