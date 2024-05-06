import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sportmateriaal from './sportmateriaal';
import SportmateriaalDetail from './sportmateriaal-detail';
import SportmateriaalUpdate from './sportmateriaal-update';
import SportmateriaalDeleteDialog from './sportmateriaal-delete-dialog';

const SportmateriaalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sportmateriaal />} />
    <Route path="new" element={<SportmateriaalUpdate />} />
    <Route path=":id">
      <Route index element={<SportmateriaalDetail />} />
      <Route path="edit" element={<SportmateriaalUpdate />} />
      <Route path="delete" element={<SportmateriaalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SportmateriaalRoutes;
