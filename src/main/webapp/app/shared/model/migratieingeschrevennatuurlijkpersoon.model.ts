export interface IMigratieingeschrevennatuurlijkpersoon {
  id?: number;
  aangevermigratie?: string | null;
  redenwijzigingmigratie?: string | null;
  soortmigratie?: string | null;
}

export const defaultValue: Readonly<IMigratieingeschrevennatuurlijkpersoon> = {};
