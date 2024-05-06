import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Soortspoor from './soortspoor';
import SoortspoorDetail from './soortspoor-detail';
import SoortspoorUpdate from './soortspoor-update';
import SoortspoorDeleteDialog from './soortspoor-delete-dialog';

const SoortspoorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Soortspoor />} />
    <Route path="new" element={<SoortspoorUpdate />} />
    <Route path=":id">
      <Route index element={<SoortspoorDetail />} />
      <Route path="edit" element={<SoortspoorUpdate />} />
      <Route path="delete" element={<SoortspoorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoortspoorRoutes;
