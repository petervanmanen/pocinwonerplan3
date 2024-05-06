import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Land from './land';
import LandDetail from './land-detail';
import LandUpdate from './land-update';
import LandDeleteDialog from './land-delete-dialog';

const LandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Land />} />
    <Route path="new" element={<LandUpdate />} />
    <Route path=":id">
      <Route index element={<LandDetail />} />
      <Route path="edit" element={<LandUpdate />} />
      <Route path="delete" element={<LandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LandRoutes;
