import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Classificatie from './classificatie';
import ClassificatieDetail from './classificatie-detail';
import ClassificatieUpdate from './classificatie-update';
import ClassificatieDeleteDialog from './classificatie-delete-dialog';

const ClassificatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Classificatie />} />
    <Route path="new" element={<ClassificatieUpdate />} />
    <Route path=":id">
      <Route index element={<ClassificatieDetail />} />
      <Route path="edit" element={<ClassificatieUpdate />} />
      <Route path="delete" element={<ClassificatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClassificatieRoutes;
