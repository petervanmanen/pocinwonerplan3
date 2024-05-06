import dayjs from 'dayjs';

export interface IKadastraleonroerendezaak {
  id?: number;
  empty?: string | null;
  appartementsrechtvolgnummer?: string | null;
  begrenzing?: string | null;
  cultuurcodeonbebouwd?: string | null;
  datumbegingeldigheidkadastraleonroerendezaak?: dayjs.Dayjs | null;
  datumeindegeldigheidkadastraleonroerendezaak?: dayjs.Dayjs | null;
  identificatie?: string | null;
  kadastralegemeente?: string | null;
  kadastralegemeentecode?: string | null;
  koopjaar?: string | null;
  koopsom?: number | null;
  landinrichtingrentebedrag?: number | null;
  landinrichtingrenteeindejaar?: string | null;
  ligging?: string | null;
  locatieomschrijving?: string | null;
  oppervlakte?: string | null;
  oud?: string | null;
  perceelnummer?: string | null;
  sectie?: string | null;
  valutacode?: string | null;
}

export const defaultValue: Readonly<IKadastraleonroerendezaak> = {};
