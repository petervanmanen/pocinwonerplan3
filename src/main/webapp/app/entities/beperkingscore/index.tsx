import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beperkingscore from './beperkingscore';
import BeperkingscoreDetail from './beperkingscore-detail';
import BeperkingscoreUpdate from './beperkingscore-update';
import BeperkingscoreDeleteDialog from './beperkingscore-delete-dialog';

const BeperkingscoreRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beperkingscore />} />
    <Route path="new" element={<BeperkingscoreUpdate />} />
    <Route path=":id">
      <Route index element={<BeperkingscoreDetail />} />
      <Route path="edit" element={<BeperkingscoreUpdate />} />
      <Route path="delete" element={<BeperkingscoreDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeperkingscoreRoutes;
