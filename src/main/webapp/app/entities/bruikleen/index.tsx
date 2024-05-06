import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bruikleen from './bruikleen';
import BruikleenDetail from './bruikleen-detail';
import BruikleenUpdate from './bruikleen-update';
import BruikleenDeleteDialog from './bruikleen-delete-dialog';

const BruikleenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bruikleen />} />
    <Route path="new" element={<BruikleenUpdate />} />
    <Route path=":id">
      <Route index element={<BruikleenDetail />} />
      <Route path="edit" element={<BruikleenUpdate />} />
      <Route path="delete" element={<BruikleenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BruikleenRoutes;
