export interface IStrooirouteuitvoering {
  id?: number;
  geplandeinde?: string | null;
  geplandstart?: string | null;
  eroute?: string | null;
  werkelijkeinde?: string | null;
  werkelijkestart?: string | null;
}

export const defaultValue: Readonly<IStrooirouteuitvoering> = {};
