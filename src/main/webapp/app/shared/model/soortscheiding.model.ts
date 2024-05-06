export interface ISoortscheiding {
  id?: number;
  indicatieplusbrpopulatie?: string | null;
  typescheiding?: string | null;
}

export const defaultValue: Readonly<ISoortscheiding> = {};
