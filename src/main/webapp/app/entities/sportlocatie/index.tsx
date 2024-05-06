import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sportlocatie from './sportlocatie';
import SportlocatieDetail from './sportlocatie-detail';
import SportlocatieUpdate from './sportlocatie-update';
import SportlocatieDeleteDialog from './sportlocatie-delete-dialog';

const SportlocatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sportlocatie />} />
    <Route path="new" element={<SportlocatieUpdate />} />
    <Route path=":id">
      <Route index element={<SportlocatieDetail />} />
      <Route path="edit" element={<SportlocatieUpdate />} />
      <Route path="delete" element={<SportlocatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SportlocatieRoutes;
