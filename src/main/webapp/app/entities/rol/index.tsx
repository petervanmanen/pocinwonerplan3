import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rol from './rol';
import RolDetail from './rol-detail';
import RolUpdate from './rol-update';
import RolDeleteDialog from './rol-delete-dialog';

const RolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rol />} />
    <Route path="new" element={<RolUpdate />} />
    <Route path=":id">
      <Route index element={<RolDetail />} />
      <Route path="edit" element={<RolUpdate />} />
      <Route path="delete" element={<RolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RolRoutes;
