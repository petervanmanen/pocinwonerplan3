import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Raadscommissie from './raadscommissie';
import RaadscommissieDetail from './raadscommissie-detail';
import RaadscommissieUpdate from './raadscommissie-update';
import RaadscommissieDeleteDialog from './raadscommissie-delete-dialog';

const RaadscommissieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Raadscommissie />} />
    <Route path="new" element={<RaadscommissieUpdate />} />
    <Route path=":id">
      <Route index element={<RaadscommissieDetail />} />
      <Route path="edit" element={<RaadscommissieUpdate />} />
      <Route path="delete" element={<RaadscommissieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RaadscommissieRoutes;
