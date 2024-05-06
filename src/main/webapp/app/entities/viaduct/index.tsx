import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Viaduct from './viaduct';
import ViaductDetail from './viaduct-detail';
import ViaductUpdate from './viaduct-update';
import ViaductDeleteDialog from './viaduct-delete-dialog';

const ViaductRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Viaduct />} />
    <Route path="new" element={<ViaductUpdate />} />
    <Route path=":id">
      <Route index element={<ViaductDetail />} />
      <Route path="edit" element={<ViaductUpdate />} />
      <Route path="delete" element={<ViaductDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ViaductRoutes;
