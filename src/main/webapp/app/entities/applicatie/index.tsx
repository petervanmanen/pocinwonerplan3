import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Applicatie from './applicatie';
import ApplicatieDetail from './applicatie-detail';
import ApplicatieUpdate from './applicatie-update';
import ApplicatieDeleteDialog from './applicatie-delete-dialog';

const ApplicatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Applicatie />} />
    <Route path="new" element={<ApplicatieUpdate />} />
    <Route path=":id">
      <Route index element={<ApplicatieDetail />} />
      <Route path="edit" element={<ApplicatieUpdate />} />
      <Route path="delete" element={<ApplicatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApplicatieRoutes;
