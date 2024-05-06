import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Installatie from './installatie';
import InstallatieDetail from './installatie-detail';
import InstallatieUpdate from './installatie-update';
import InstallatieDeleteDialog from './installatie-delete-dialog';

const InstallatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Installatie />} />
    <Route path="new" element={<InstallatieUpdate />} />
    <Route path=":id">
      <Route index element={<InstallatieDetail />} />
      <Route path="edit" element={<InstallatieUpdate />} />
      <Route path="delete" element={<InstallatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InstallatieRoutes;
