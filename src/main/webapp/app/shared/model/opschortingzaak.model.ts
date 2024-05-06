export interface IOpschortingzaak {
  id?: number;
  indicatieopschorting?: string | null;
  redenopschorting?: string | null;
}

export const defaultValue: Readonly<IOpschortingzaak> = {};
