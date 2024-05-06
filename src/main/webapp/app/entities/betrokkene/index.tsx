import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Betrokkene from './betrokkene';
import BetrokkeneDetail from './betrokkene-detail';
import BetrokkeneUpdate from './betrokkene-update';
import BetrokkeneDeleteDialog from './betrokkene-delete-dialog';

const BetrokkeneRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Betrokkene />} />
    <Route path="new" element={<BetrokkeneUpdate />} />
    <Route path=":id">
      <Route index element={<BetrokkeneDetail />} />
      <Route path="edit" element={<BetrokkeneUpdate />} />
      <Route path="delete" element={<BetrokkeneDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BetrokkeneRoutes;
