export interface IFunctioneelgebied {
  id?: number;
  functioneelgebiedcode?: string | null;
  functioneelgebiednaam?: string | null;
  omtrek?: string | null;
  oppervlakte?: string | null;
}

export const defaultValue: Readonly<IFunctioneelgebied> = {};
