import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Grondslag from './grondslag';
import GrondslagDetail from './grondslag-detail';
import GrondslagUpdate from './grondslag-update';
import GrondslagDeleteDialog from './grondslag-delete-dialog';

const GrondslagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Grondslag />} />
    <Route path="new" element={<GrondslagUpdate />} />
    <Route path=":id">
      <Route index element={<GrondslagDetail />} />
      <Route path="edit" element={<GrondslagUpdate />} />
      <Route path="delete" element={<GrondslagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GrondslagRoutes;
