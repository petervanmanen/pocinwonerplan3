import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nietnatuurlijkpersoon from './nietnatuurlijkpersoon';
import NietnatuurlijkpersoonDetail from './nietnatuurlijkpersoon-detail';
import NietnatuurlijkpersoonUpdate from './nietnatuurlijkpersoon-update';
import NietnatuurlijkpersoonDeleteDialog from './nietnatuurlijkpersoon-delete-dialog';

const NietnatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nietnatuurlijkpersoon />} />
    <Route path="new" element={<NietnatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<NietnatuurlijkpersoonDetail />} />
      <Route path="edit" element={<NietnatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<NietnatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NietnatuurlijkpersoonRoutes;
