import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overstortconstructie from './overstortconstructie';
import OverstortconstructieDetail from './overstortconstructie-detail';
import OverstortconstructieUpdate from './overstortconstructie-update';
import OverstortconstructieDeleteDialog from './overstortconstructie-delete-dialog';

const OverstortconstructieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overstortconstructie />} />
    <Route path="new" element={<OverstortconstructieUpdate />} />
    <Route path=":id">
      <Route index element={<OverstortconstructieDetail />} />
      <Route path="edit" element={<OverstortconstructieUpdate />} />
      <Route path="delete" element={<OverstortconstructieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OverstortconstructieRoutes;
