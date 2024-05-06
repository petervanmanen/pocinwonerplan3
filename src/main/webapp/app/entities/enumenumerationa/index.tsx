import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Enumenumerationa from './enumenumerationa';
import EnumenumerationaDetail from './enumenumerationa-detail';
import EnumenumerationaUpdate from './enumenumerationa-update';
import EnumenumerationaDeleteDialog from './enumenumerationa-delete-dialog';

const EnumenumerationaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Enumenumerationa />} />
    <Route path="new" element={<EnumenumerationaUpdate />} />
    <Route path=":id">
      <Route index element={<EnumenumerationaDetail />} />
      <Route path="edit" element={<EnumenumerationaUpdate />} />
      <Route path="delete" element={<EnumenumerationaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EnumenumerationaRoutes;
