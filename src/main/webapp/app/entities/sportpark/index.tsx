import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sportpark from './sportpark';
import SportparkDetail from './sportpark-detail';
import SportparkUpdate from './sportpark-update';
import SportparkDeleteDialog from './sportpark-delete-dialog';

const SportparkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sportpark />} />
    <Route path="new" element={<SportparkUpdate />} />
    <Route path=":id">
      <Route index element={<SportparkDetail />} />
      <Route path="edit" element={<SportparkUpdate />} />
      <Route path="delete" element={<SportparkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SportparkRoutes;
