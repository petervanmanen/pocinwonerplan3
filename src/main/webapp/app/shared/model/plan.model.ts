import dayjs from 'dayjs';
import { IProgramma } from 'app/shared/model/programma.model';
import { IProjectleider } from 'app/shared/model/projectleider.model';
import { IProjectontwikkelaar } from 'app/shared/model/projectontwikkelaar.model';

export interface IPlan {
  id?: number;
  zeventigprocentverkocht?: boolean | null;
  aardgasloos?: boolean | null;
  bestemminggoedgekeurd?: boolean | null;
  eersteoplevering?: dayjs.Dayjs | null;
  eigendomgemeente?: boolean | null;
  gebiedstransformatie?: boolean | null;
  intentie?: boolean | null;
  laatsteoplevering?: dayjs.Dayjs | null;
  naam?: string | null;
  nummer?: string | null;
  onherroepelijk?: boolean | null;
  percelen?: string | null;
  startbouw?: dayjs.Dayjs | null;
  startverkoop?: dayjs.Dayjs | null;
  binnenprogrammaProgramma?: IProgramma | null;
  isprojectleidervanProjectleider?: IProjectleider | null;
  heeftProjectontwikkelaars?: IProjectontwikkelaar[] | null;
}

export const defaultValue: Readonly<IPlan> = {
  zeventigprocentverkocht: false,
  aardgasloos: false,
  bestemminggoedgekeurd: false,
  eigendomgemeente: false,
  gebiedstransformatie: false,
  intentie: false,
  onherroepelijk: false,
};
