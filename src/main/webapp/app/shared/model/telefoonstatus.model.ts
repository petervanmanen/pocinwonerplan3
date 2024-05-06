export interface ITelefoonstatus {
  id?: number;
  contactconnectionstate?: string | null;
  status?: string | null;
}

export const defaultValue: Readonly<ITelefoonstatus> = {};
