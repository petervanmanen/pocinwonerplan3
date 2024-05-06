export interface IAmbacht {
  id?: number;
  ambachtsoort?: string | null;
  jaarambachttot?: string | null;
  jaarambachtvanaf?: string | null;
}

export const defaultValue: Readonly<IAmbacht> = {};
