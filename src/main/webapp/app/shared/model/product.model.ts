import dayjs from 'dayjs';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { IOmzetgroep } from 'app/shared/model/omzetgroep.model';
import { IProductgroep } from 'app/shared/model/productgroep.model';
import { IDoelstelling } from 'app/shared/model/doelstelling.model';
import { IOpdrachtgever } from 'app/shared/model/opdrachtgever.model';
import { IOpdrachtnemer } from 'app/shared/model/opdrachtnemer.model';

export interface IProduct {
  id?: number;
  codemuseumjaarkaart?: string | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  entreekaart?: string | null;
  omschrijving?: string | null;
  prijs?: number | null;
  leverancierLeverancier?: ILeverancier | null;
  heeftKostenplaats?: IKostenplaats | null;
  valtbinnenOmzetgroeps?: IOmzetgroep[] | null;
  valtbinnenProductgroeps?: IProductgroep[] | null;
  heeftDoelstelling?: IDoelstelling | null;
  isopdrachtgeverOpdrachtgever?: IOpdrachtgever | null;
  isopdrachtnemerOpdrachtnemer?: IOpdrachtnemer | null;
}

export const defaultValue: Readonly<IProduct> = {};
