import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sollicitatie from './sollicitatie';
import SollicitatieDetail from './sollicitatie-detail';
import SollicitatieUpdate from './sollicitatie-update';
import SollicitatieDeleteDialog from './sollicitatie-delete-dialog';

const SollicitatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sollicitatie />} />
    <Route path="new" element={<SollicitatieUpdate />} />
    <Route path=":id">
      <Route index element={<SollicitatieDetail />} />
      <Route path="edit" element={<SollicitatieUpdate />} />
      <Route path="delete" element={<SollicitatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SollicitatieRoutes;
