import { IProductgroep } from 'app/shared/model/productgroep.model';

export interface IProductsoort {
  id?: number;
  code?: string | null;
  omschrijving?: string | null;
  tarief?: number | null;
  tariefperiode?: string | null;
  valtbinnenProductgroep?: IProductgroep | null;
}

export const defaultValue: Readonly<IProductsoort> = {};
