import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Spoor from './spoor';
import SpoorDetail from './spoor-detail';
import SpoorUpdate from './spoor-update';
import SpoorDeleteDialog from './spoor-delete-dialog';

const SpoorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Spoor />} />
    <Route path="new" element={<SpoorUpdate />} />
    <Route path=":id">
      <Route index element={<SpoorDetail />} />
      <Route path="edit" element={<SpoorUpdate />} />
      <Route path="delete" element={<SpoorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SpoorRoutes;
