export interface IKolk {
  id?: number;
  bereikbaarheidkolk?: string | null;
  risicogebied?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IKolk> = {};
