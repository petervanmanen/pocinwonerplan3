import { IContract } from 'app/shared/model/contract.model';
import { IInkooppakket } from 'app/shared/model/inkooppakket.model';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';

export interface IInkooporder {
  id?: number;
  artikelcode?: string | null;
  betalingmeerderejaren?: boolean | null;
  betreft?: string | null;
  datumeinde?: string | null;
  datumingediend?: string | null;
  datumstart?: string | null;
  goederencode?: string | null;
  omschrijving?: string | null;
  ordernummer?: string | null;
  saldo?: number | null;
  totaalnettobedrag?: number | null;
  wijzevanaanbesteden?: string | null;
  betreftContract?: IContract | null;
  oorspronkelijkInkooporder?: IInkooporder | null;
  heeftInkooppakket?: IInkooppakket | null;
  verplichtingaanLeverancier?: ILeverancier | null;
  wordtgeschrevenopHoofdrekenings?: IHoofdrekening[] | null;
  gerelateerdInkooporder2?: IInkooporder | null;
  heeftKostenplaats?: IKostenplaats[] | null;
}

export const defaultValue: Readonly<IInkooporder> = {
  betalingmeerderejaren: false,
};
