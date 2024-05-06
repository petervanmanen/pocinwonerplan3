import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Keermuur from './keermuur';
import KeermuurDetail from './keermuur-detail';
import KeermuurUpdate from './keermuur-update';
import KeermuurDeleteDialog from './keermuur-delete-dialog';

const KeermuurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Keermuur />} />
    <Route path="new" element={<KeermuurUpdate />} />
    <Route path=":id">
      <Route index element={<KeermuurDetail />} />
      <Route path="edit" element={<KeermuurUpdate />} />
      <Route path="delete" element={<KeermuurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KeermuurRoutes;
