export interface IVloginfo {
  id?: number;
  detectieverkeer?: string | null;
  eindegroen?: boolean | null;
  snelheid?: string | null;
  startgroen?: boolean | null;
  tijdstip?: string | null;
  verkeerwilgroen?: boolean | null;
  wachttijd?: string | null;
}

export const defaultValue: Readonly<IVloginfo> = {
  eindegroen: false,
  startgroen: false,
  verkeerwilgroen: false,
};
