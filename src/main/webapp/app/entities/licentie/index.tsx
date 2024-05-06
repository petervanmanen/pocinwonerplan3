import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Licentie from './licentie';
import LicentieDetail from './licentie-detail';
import LicentieUpdate from './licentie-update';
import LicentieDeleteDialog from './licentie-delete-dialog';

const LicentieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Licentie />} />
    <Route path="new" element={<LicentieUpdate />} />
    <Route path=":id">
      <Route index element={<LicentieDetail />} />
      <Route path="edit" element={<LicentieUpdate />} />
      <Route path="delete" element={<LicentieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LicentieRoutes;
