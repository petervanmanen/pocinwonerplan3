import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Levering from './levering';
import LeveringDetail from './levering-detail';
import LeveringUpdate from './levering-update';
import LeveringDeleteDialog from './levering-delete-dialog';

const LeveringRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Levering />} />
    <Route path="new" element={<LeveringUpdate />} />
    <Route path=":id">
      <Route index element={<LeveringDetail />} />
      <Route path="edit" element={<LeveringUpdate />} />
      <Route path="delete" element={<LeveringDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeveringRoutes;
