import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gebied from './gebied';
import GebiedDetail from './gebied-detail';
import GebiedUpdate from './gebied-update';
import GebiedDeleteDialog from './gebied-delete-dialog';

const GebiedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gebied />} />
    <Route path="new" element={<GebiedUpdate />} />
    <Route path=":id">
      <Route index element={<GebiedDetail />} />
      <Route path="edit" element={<GebiedUpdate />} />
      <Route path="delete" element={<GebiedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GebiedRoutes;
