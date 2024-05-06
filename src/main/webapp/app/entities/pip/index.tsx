import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Pip from './pip';
import PipDetail from './pip-detail';
import PipUpdate from './pip-update';
import PipDeleteDialog from './pip-delete-dialog';

const PipRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Pip />} />
    <Route path="new" element={<PipUpdate />} />
    <Route path=":id">
      <Route index element={<PipDetail />} />
      <Route path="edit" element={<PipUpdate />} />
      <Route path="delete" element={<PipDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PipRoutes;
