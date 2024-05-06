import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Dienst from './dienst';
import DienstDetail from './dienst-detail';
import DienstUpdate from './dienst-update';
import DienstDeleteDialog from './dienst-delete-dialog';

const DienstRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Dienst />} />
    <Route path="new" element={<DienstUpdate />} />
    <Route path=":id">
      <Route index element={<DienstDetail />} />
      <Route path="edit" element={<DienstUpdate />} />
      <Route path="delete" element={<DienstDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DienstRoutes;
