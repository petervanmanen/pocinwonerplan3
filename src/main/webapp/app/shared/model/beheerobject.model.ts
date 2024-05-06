import dayjs from 'dayjs';
import { IMelding } from 'app/shared/model/melding.model';

export interface IBeheerobject {
  id?: number;
  aangemaaktdoor?: string | null;
  begingarantieperiode?: dayjs.Dayjs | null;
  beheergebied?: string | null;
  beheerobjectbeheervak?: string | null;
  beheerobjectgebruiksfunctie?: string | null;
  beheerobjectmemo?: string | null;
  beschermdefloraenfauna?: boolean | null;
  buurt?: string | null;
  conversieid?: string | null;
  datummutatie?: dayjs.Dayjs | null;
  datumoplevering?: dayjs.Dayjs | null;
  datumpublicatielv?: dayjs.Dayjs | null;
  datumverwijdering?: dayjs.Dayjs | null;
  eindegarantieperiode?: dayjs.Dayjs | null;
  gebiedstype?: string | null;
  gemeente?: string | null;
  geometrie?: string | null;
  gewijzigddoor?: string | null;
  grondsoort?: string | null;
  grondsoortplus?: string | null;
  identificatieimbor?: string | null;
  identificatieimgeo?: string | null;
  jaarvanaanleg?: string | null;
  eobjectbegintijd?: string | null;
  eobjecteindtijd?: string | null;
  onderhoudsplichtige?: string | null;
  openbareruimte?: string | null;
  postcode?: string | null;
  relatievehoogteligging?: string | null;
  stadsdeel?: string | null;
  status?: string | null;
  theoretischeindejaar?: string | null;
  tijdstipregistratie?: string | null;
  typebeheerder?: string | null;
  typebeheerderplus?: string | null;
  typeeigenaar?: string | null;
  typeeigenaarplus?: string | null;
  typeligging?: string | null;
  waterschap?: string | null;
  wijk?: string | null;
  woonplaats?: string | null;
  zettingsgevoeligheid?: string | null;
  zettingsgevoeligheidplus?: string | null;
  betreftMeldings?: IMelding[];
}

export const defaultValue: Readonly<IBeheerobject> = {
  beschermdefloraenfauna: false,
};
