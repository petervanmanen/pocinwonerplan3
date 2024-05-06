import { ICollegelid } from 'app/shared/model/collegelid.model';
import { IRaadslid } from 'app/shared/model/raadslid.model';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';

export interface IIndiener {
  id?: number;
  naam?: string | null;
  omschrijving?: string | null;
  isCollegelid?: ICollegelid | null;
  isRaadslid?: IRaadslid | null;
  isRechtspersoon?: IRechtspersoon | null;
  heeftRaadsstuks?: IRaadsstuk[] | null;
}

export const defaultValue: Readonly<IIndiener> = {};
