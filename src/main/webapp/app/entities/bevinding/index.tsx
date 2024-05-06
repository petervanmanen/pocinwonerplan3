import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bevinding from './bevinding';
import BevindingDetail from './bevinding-detail';
import BevindingUpdate from './bevinding-update';
import BevindingDeleteDialog from './bevinding-delete-dialog';

const BevindingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bevinding />} />
    <Route path="new" element={<BevindingUpdate />} />
    <Route path=":id">
      <Route index element={<BevindingDetail />} />
      <Route path="edit" element={<BevindingUpdate />} />
      <Route path="delete" element={<BevindingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BevindingRoutes;
