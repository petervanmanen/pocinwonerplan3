import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Versie from './versie';
import VersieDetail from './versie-detail';
import VersieUpdate from './versie-update';
import VersieDeleteDialog from './versie-delete-dialog';

const VersieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Versie />} />
    <Route path="new" element={<VersieUpdate />} />
    <Route path=":id">
      <Route index element={<VersieDetail />} />
      <Route path="edit" element={<VersieUpdate />} />
      <Route path="delete" element={<VersieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VersieRoutes;
