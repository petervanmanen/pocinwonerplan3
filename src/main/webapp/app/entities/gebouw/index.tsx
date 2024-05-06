import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gebouw from './gebouw';
import GebouwDetail from './gebouw-detail';
import GebouwUpdate from './gebouw-update';
import GebouwDeleteDialog from './gebouw-delete-dialog';

const GebouwRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gebouw />} />
    <Route path="new" element={<GebouwUpdate />} />
    <Route path=":id">
      <Route index element={<GebouwDetail />} />
      <Route path="edit" element={<GebouwUpdate />} />
      <Route path="delete" element={<GebouwDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GebouwRoutes;
