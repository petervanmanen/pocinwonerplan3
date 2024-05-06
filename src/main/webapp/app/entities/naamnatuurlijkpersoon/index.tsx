import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Naamnatuurlijkpersoon from './naamnatuurlijkpersoon';
import NaamnatuurlijkpersoonDetail from './naamnatuurlijkpersoon-detail';
import NaamnatuurlijkpersoonUpdate from './naamnatuurlijkpersoon-update';
import NaamnatuurlijkpersoonDeleteDialog from './naamnatuurlijkpersoon-delete-dialog';

const NaamnatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Naamnatuurlijkpersoon />} />
    <Route path="new" element={<NaamnatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<NaamnatuurlijkpersoonDetail />} />
      <Route path="edit" element={<NaamnatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<NaamnatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NaamnatuurlijkpersoonRoutes;
