import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Specificatie from './specificatie';
import SpecificatieDetail from './specificatie-detail';
import SpecificatieUpdate from './specificatie-update';
import SpecificatieDeleteDialog from './specificatie-delete-dialog';

const SpecificatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Specificatie />} />
    <Route path="new" element={<SpecificatieUpdate />} />
    <Route path=":id">
      <Route index element={<SpecificatieDetail />} />
      <Route path="edit" element={<SpecificatieUpdate />} />
      <Route path="delete" element={<SpecificatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SpecificatieRoutes;
