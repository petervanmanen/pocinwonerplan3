import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Storting from './storting';
import StortingDetail from './storting-detail';
import StortingUpdate from './storting-update';
import StortingDeleteDialog from './storting-delete-dialog';

const StortingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Storting />} />
    <Route path="new" element={<StortingUpdate />} />
    <Route path=":id">
      <Route index element={<StortingDetail />} />
      <Route path="edit" element={<StortingUpdate />} />
      <Route path="delete" element={<StortingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StortingRoutes;
