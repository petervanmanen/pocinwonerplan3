import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aantekening from './aantekening';
import AantekeningDetail from './aantekening-detail';
import AantekeningUpdate from './aantekening-update';
import AantekeningDeleteDialog from './aantekening-delete-dialog';

const AantekeningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aantekening />} />
    <Route path="new" element={<AantekeningUpdate />} />
    <Route path=":id">
      <Route index element={<AantekeningDetail />} />
      <Route path="edit" element={<AantekeningUpdate />} />
      <Route path="delete" element={<AantekeningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AantekeningRoutes;
