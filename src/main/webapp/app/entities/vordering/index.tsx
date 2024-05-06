import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vordering from './vordering';
import VorderingDetail from './vordering-detail';
import VorderingUpdate from './vordering-update';
import VorderingDeleteDialog from './vordering-delete-dialog';

const VorderingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vordering />} />
    <Route path="new" element={<VorderingUpdate />} />
    <Route path=":id">
      <Route index element={<VorderingDetail />} />
      <Route path="edit" element={<VorderingUpdate />} />
      <Route path="delete" element={<VorderingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VorderingRoutes;
