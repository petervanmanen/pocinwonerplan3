export interface IOnderwerp {
  id?: number;
  hoofdonderwerpOnderwerp?: IOnderwerp | null;
}

export const defaultValue: Readonly<IOnderwerp> = {};
