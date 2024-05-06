import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sportterrein from './sportterrein';
import SportterreinDetail from './sportterrein-detail';
import SportterreinUpdate from './sportterrein-update';
import SportterreinDeleteDialog from './sportterrein-delete-dialog';

const SportterreinRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sportterrein />} />
    <Route path="new" element={<SportterreinUpdate />} />
    <Route path=":id">
      <Route index element={<SportterreinDetail />} />
      <Route path="edit" element={<SportterreinUpdate />} />
      <Route path="delete" element={<SportterreinDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SportterreinRoutes;
