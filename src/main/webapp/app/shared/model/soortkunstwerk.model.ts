export interface ISoortkunstwerk {
  id?: number;
  indicatieplusbrpopulatie?: string | null;
  typekunstwerk?: string | null;
}

export const defaultValue: Readonly<ISoortkunstwerk> = {};
