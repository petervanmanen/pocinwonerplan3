import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Depot from './depot';
import DepotDetail from './depot-detail';
import DepotUpdate from './depot-update';
import DepotDeleteDialog from './depot-delete-dialog';

const DepotRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Depot />} />
    <Route path="new" element={<DepotUpdate />} />
    <Route path=":id">
      <Route index element={<DepotDetail />} />
      <Route path="edit" element={<DepotUpdate />} />
      <Route path="delete" element={<DepotDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DepotRoutes;
