import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Academischetitel from './academischetitel';
import AcademischetitelDetail from './academischetitel-detail';
import AcademischetitelUpdate from './academischetitel-update';
import AcademischetitelDeleteDialog from './academischetitel-delete-dialog';

const AcademischetitelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Academischetitel />} />
    <Route path="new" element={<AcademischetitelUpdate />} />
    <Route path=":id">
      <Route index element={<AcademischetitelDetail />} />
      <Route path="edit" element={<AcademischetitelUpdate />} />
      <Route path="delete" element={<AcademischetitelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AcademischetitelRoutes;
