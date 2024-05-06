export interface ILeerjaar {
  id?: number;
  jaareinde?: string | null;
  jaarstart?: string | null;
}

export const defaultValue: Readonly<ILeerjaar> = {};
