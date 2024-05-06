import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gegeven from './gegeven';
import GegevenDetail from './gegeven-detail';
import GegevenUpdate from './gegeven-update';
import GegevenDeleteDialog from './gegeven-delete-dialog';

const GegevenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gegeven />} />
    <Route path="new" element={<GegevenUpdate />} />
    <Route path=":id">
      <Route index element={<GegevenDetail />} />
      <Route path="edit" element={<GegevenUpdate />} />
      <Route path="delete" element={<GegevenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GegevenRoutes;
