import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Activa from './activa';
import ActivaDetail from './activa-detail';
import ActivaUpdate from './activa-update';
import ActivaDeleteDialog from './activa-delete-dialog';

const ActivaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Activa />} />
    <Route path="new" element={<ActivaUpdate />} />
    <Route path=":id">
      <Route index element={<ActivaDetail />} />
      <Route path="edit" element={<ActivaUpdate />} />
      <Route path="delete" element={<ActivaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ActivaRoutes;
