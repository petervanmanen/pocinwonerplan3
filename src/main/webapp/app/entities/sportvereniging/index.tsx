import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sportvereniging from './sportvereniging';
import SportverenigingDetail from './sportvereniging-detail';
import SportverenigingUpdate from './sportvereniging-update';
import SportverenigingDeleteDialog from './sportvereniging-delete-dialog';

const SportverenigingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sportvereniging />} />
    <Route path="new" element={<SportverenigingUpdate />} />
    <Route path=":id">
      <Route index element={<SportverenigingDetail />} />
      <Route path="edit" element={<SportverenigingUpdate />} />
      <Route path="delete" element={<SportverenigingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SportverenigingRoutes;
