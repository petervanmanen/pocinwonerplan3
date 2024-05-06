import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gunning from './gunning';
import GunningDetail from './gunning-detail';
import GunningUpdate from './gunning-update';
import GunningDeleteDialog from './gunning-delete-dialog';

const GunningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gunning />} />
    <Route path="new" element={<GunningUpdate />} />
    <Route path=":id">
      <Route index element={<GunningDetail />} />
      <Route path="edit" element={<GunningUpdate />} />
      <Route path="delete" element={<GunningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GunningRoutes;
