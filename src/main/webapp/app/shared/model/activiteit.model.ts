import { IRondleiding } from 'app/shared/model/rondleiding.model';
import { IActiviteitsoort } from 'app/shared/model/activiteitsoort.model';
import { ILocatie } from 'app/shared/model/locatie.model';
import { IProgramma } from 'app/shared/model/programma.model';
import { IVerzoek } from 'app/shared/model/verzoek.model';

export interface IActiviteit {
  id?: number;
  omschrijving?: string | null;
  gerelateerdeactiviteitActiviteit?: IActiviteit | null;
  bovenliggendeactiviteitActiviteit?: IActiviteit | null;
  heeftRondleiding?: IRondleiding | null;
  vansoortActiviteitsoort?: IActiviteitsoort | null;
  isverbondenmetLocaties?: ILocatie[] | null;
  bestaatuitActiviteit2?: IActiviteit | null;
  bestaatuitProgramma?: IProgramma | null;
  betreftVerzoeks?: IVerzoek[];
}

export const defaultValue: Readonly<IActiviteit> = {};
