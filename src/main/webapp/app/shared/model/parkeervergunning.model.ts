import dayjs from 'dayjs';
import { IParkeerrecht } from 'app/shared/model/parkeerrecht.model';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { IProductgroep } from 'app/shared/model/productgroep.model';
import { IProductsoort } from 'app/shared/model/productsoort.model';

export interface IParkeervergunning {
  id?: number;
  datumeindegeldigheid?: string | null;
  datumreservering?: dayjs.Dayjs | null;
  datumstart?: string | null;
  kenteken?: string | null;
  minutenafgeschreven?: string | null;
  minutengeldig?: string | null;
  minutenresterend?: string | null;
  nummer?: string | null;
  type?: string | null;
  resulteertParkeerrecht?: IParkeerrecht | null;
  houderRechtspersoon?: IRechtspersoon | null;
  soortProductgroep?: IProductgroep | null;
  soortProductsoort?: IProductsoort | null;
}

export const defaultValue: Readonly<IParkeervergunning> = {};
