import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leveringsvorm from './leveringsvorm';
import LeveringsvormDetail from './leveringsvorm-detail';
import LeveringsvormUpdate from './leveringsvorm-update';
import LeveringsvormDeleteDialog from './leveringsvorm-delete-dialog';

const LeveringsvormRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leveringsvorm />} />
    <Route path="new" element={<LeveringsvormUpdate />} />
    <Route path=":id">
      <Route index element={<LeveringsvormDetail />} />
      <Route path="edit" element={<LeveringsvormUpdate />} />
      <Route path="delete" element={<LeveringsvormDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeveringsvormRoutes;
