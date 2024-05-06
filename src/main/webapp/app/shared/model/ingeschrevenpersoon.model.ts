import dayjs from 'dayjs';

export interface IIngeschrevenpersoon {
  id?: number;
  adresherkomst?: string | null;
  anummer?: string | null;
  beschrijvinglocatie?: string | null;
  buitenlandsreisdocument?: string | null;
  burgerlijkestaat?: string | null;
  datumbegingeldigheidverblijfplaats?: string | null;
  datumeindegeldigheidverblijfsplaats?: dayjs.Dayjs | null;
  datuminschrijvinggemeente?: string | null;
  datumopschortingbijhouding?: string | null;
  datumvertrekuitnederland?: string | null;
  datumvestigingnederland?: string | null;
  gemeentevaninschrijving?: string | null;
  gezinsrelatie?: string | null;
  indicatiegeheim?: string | null;
  ingezetene?: string | null;
  landwaarnaarvertrokken?: string | null;
  landwaarvandaaningeschreven?: string | null;
  ouder1?: string | null;
  ouder2?: string | null;
  partnerid?: string | null;
  redeneindebewoning?: string | null;
  redenopschortingbijhouding?: string | null;
  signaleringreisdocument?: string | null;
  verblijfstitel?: string | null;
}

export const defaultValue: Readonly<IIngeschrevenpersoon> = {};
