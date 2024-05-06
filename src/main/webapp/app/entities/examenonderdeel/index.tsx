import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Examenonderdeel from './examenonderdeel';
import ExamenonderdeelDetail from './examenonderdeel-detail';
import ExamenonderdeelUpdate from './examenonderdeel-update';
import ExamenonderdeelDeleteDialog from './examenonderdeel-delete-dialog';

const ExamenonderdeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Examenonderdeel />} />
    <Route path="new" element={<ExamenonderdeelUpdate />} />
    <Route path=":id">
      <Route index element={<ExamenonderdeelDetail />} />
      <Route path="edit" element={<ExamenonderdeelUpdate />} />
      <Route path="delete" element={<ExamenonderdeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ExamenonderdeelRoutes;
