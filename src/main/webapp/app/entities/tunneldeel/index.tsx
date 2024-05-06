import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tunneldeel from './tunneldeel';
import TunneldeelDetail from './tunneldeel-detail';
import TunneldeelUpdate from './tunneldeel-update';
import TunneldeelDeleteDialog from './tunneldeel-delete-dialog';

const TunneldeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tunneldeel />} />
    <Route path="new" element={<TunneldeelUpdate />} />
    <Route path=":id">
      <Route index element={<TunneldeelDetail />} />
      <Route path="edit" element={<TunneldeelUpdate />} />
      <Route path="delete" element={<TunneldeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TunneldeelRoutes;
