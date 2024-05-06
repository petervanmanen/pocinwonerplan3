import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Examen from './examen';
import ExamenDetail from './examen-detail';
import ExamenUpdate from './examen-update';
import ExamenDeleteDialog from './examen-delete-dialog';

const ExamenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Examen />} />
    <Route path="new" element={<ExamenUpdate />} />
    <Route path=":id">
      <Route index element={<ExamenDetail />} />
      <Route path="edit" element={<ExamenUpdate />} />
      <Route path="delete" element={<ExamenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ExamenRoutes;
