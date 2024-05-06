import { IParticipatiedossier } from 'app/shared/model/participatiedossier.model';
import { IInkomensvoorziening } from 'app/shared/model/inkomensvoorziening.model';
import { IDoelgroep } from 'app/shared/model/doelgroep.model';
import { IRelatie } from 'app/shared/model/relatie.model';
import { IHuishouden } from 'app/shared/model/huishouden.model';
import { ITaalniveau } from 'app/shared/model/taalniveau.model';
import { IClientbegeleider } from 'app/shared/model/clientbegeleider.model';

export interface IClient {
  id?: number;
  code?: string | null;
  gezagsdragergekend?: boolean | null;
  juridischestatus?: string | null;
  wettelijkevertegenwoordiging?: string | null;
  heeftParticipatiedossier?: IParticipatiedossier;
  heeftvoorzieningInkomensvoorziening?: IInkomensvoorziening;
  valtbinnendoelgroepDoelgroep?: IDoelgroep | null;
  heeftrelatieRelaties?: IRelatie[] | null;
  voorzieningbijstandspartijInkomensvoorzienings?: IInkomensvoorziening[];
  maaktonderdeeluitvanHuishoudens?: IHuishouden[] | null;
  heefttaalniveauTaalniveaus?: ITaalniveau[] | null;
  ondersteuntclientClientbegeleiders?: IClientbegeleider[] | null;
}

export const defaultValue: Readonly<IClient> = {
  gezagsdragergekend: false,
};
