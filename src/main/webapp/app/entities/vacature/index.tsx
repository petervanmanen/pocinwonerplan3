import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vacature from './vacature';
import VacatureDetail from './vacature-detail';
import VacatureUpdate from './vacature-update';
import VacatureDeleteDialog from './vacature-delete-dialog';

const VacatureRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vacature />} />
    <Route path="new" element={<VacatureUpdate />} />
    <Route path=":id">
      <Route index element={<VacatureDetail />} />
      <Route path="edit" element={<VacatureUpdate />} />
      <Route path="delete" element={<VacatureDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VacatureRoutes;
