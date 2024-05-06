import { IVoorziening } from 'app/shared/model/voorziening.model';
import { IZaal } from 'app/shared/model/zaal.model';
import { IActiviteit } from 'app/shared/model/activiteit.model';

export interface IReservering {
  id?: number;
  aantal?: string | null;
  btw?: string | null;
  tijdtot?: string | null;
  tijdvanaf?: string | null;
  totaalprijs?: string | null;
  betreftVoorziening?: IVoorziening | null;
  betreftZaal?: IZaal | null;
  heeftActiviteit?: IActiviteit | null;
}

export const defaultValue: Readonly<IReservering> = {};
