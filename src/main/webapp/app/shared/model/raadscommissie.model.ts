import { IRaadslid } from 'app/shared/model/raadslid.model';

export interface IRaadscommissie {
  id?: number;
  naam?: string | null;
  islidvanRaadslids?: IRaadslid[] | null;
}

export const defaultValue: Readonly<IRaadscommissie> = {};
