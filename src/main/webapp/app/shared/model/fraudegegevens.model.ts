import { IFraudesoort } from 'app/shared/model/fraudesoort.model';
import { IClient } from 'app/shared/model/client.model';

export interface IFraudegegevens {
  id?: number;
  bedragfraude?: string | null;
  datumeindefraude?: string | null;
  datumgeconstateerd?: string | null;
  datumstartfraude?: string | null;
  verrekening?: string | null;
  vorderingen?: string | null;
  isvansoortFraudesoort?: IFraudesoort | null;
  heeftfraudegegevensClient?: IClient | null;
}

export const defaultValue: Readonly<IFraudegegevens> = {};
