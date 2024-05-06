import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Migratieingeschrevennatuurlijkpersoon from './migratieingeschrevennatuurlijkpersoon';
import MigratieingeschrevennatuurlijkpersoonDetail from './migratieingeschrevennatuurlijkpersoon-detail';
import MigratieingeschrevennatuurlijkpersoonUpdate from './migratieingeschrevennatuurlijkpersoon-update';
import MigratieingeschrevennatuurlijkpersoonDeleteDialog from './migratieingeschrevennatuurlijkpersoon-delete-dialog';

const MigratieingeschrevennatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Migratieingeschrevennatuurlijkpersoon />} />
    <Route path="new" element={<MigratieingeschrevennatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<MigratieingeschrevennatuurlijkpersoonDetail />} />
      <Route path="edit" element={<MigratieingeschrevennatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<MigratieingeschrevennatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MigratieingeschrevennatuurlijkpersoonRoutes;
