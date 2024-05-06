import { IBankafschriftregel } from 'app/shared/model/bankafschriftregel.model';
import { IBankrekening } from 'app/shared/model/bankrekening.model';
import { IZaak } from 'app/shared/model/zaak.model';

export interface IBetaling {
  id?: number;
  bedrag?: number | null;
  datumtijd?: string | null;
  omschrijving?: string | null;
  valuta?: string | null;
  komtvooropBankafschriftregel?: IBankafschriftregel | null;
  vanBankrekening?: IBankrekening | null;
  naarBankrekening?: IBankrekening | null;
  heeftbetalingZaak?: IZaak | null;
}

export const defaultValue: Readonly<IBetaling> = {};
